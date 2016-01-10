package adroit.quiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class quizMain extends AppCompatActivity {

    public static Context context;

    boolean nameSwitcher;
    boolean ratingSwitcher;

    private GameAdapter adapter1;
    private ArrayList<SingleRow> list = new ArrayList<SingleRow>();

    private EditText inputSearch;
    private ListView gameList;

    static JSONObject jsonResponse = new JSONObject();

    public static void setJson(JSONObject json){
        jsonResponse = json;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        context = getApplicationContext();
        initialize();
        makeList();
        //hur göra här?
        //sortRatingList();



        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //När användaren ändrar texten
                adapter1.getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    //Denna metoden gör så att dessa objekt finns tillgängliga i hela klassen
    private void initialize() {
        gameList = (ListView) findViewById(R.id.gameList);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
    }

    //Sorterar listan efter namn eller rating med hjälp av en Comparator-klass
    public void sortNameList (View view) {

        if (nameSwitcher == false) {
            Collections.sort(list, new SortingComparatorNameAsc());
            adapter1 = new GameAdapter(quizMain.this, list);
            gameList.setAdapter(adapter1);
            nameSwitcher = true;
        } else {
            Collections.sort(list, new SortingComparatorNameDsc());
            adapter1 = new GameAdapter(quizMain.this, list);
            gameList.setAdapter(adapter1);
            nameSwitcher = false;
        }
    }

    public void sortRatingList (View view) {

        if (ratingSwitcher == false) {
            Collections.sort(list, new SortingComparatorRatingAsc());
            adapter1 = new GameAdapter(quizMain.this, list);
            gameList.setAdapter(adapter1);
            ratingSwitcher = true;
        } else {
            Collections.sort(list, new SortingComparatorRatingDsc());
            adapter1 = new GameAdapter(quizMain.this, list);
            gameList.setAdapter(adapter1);
            ratingSwitcher = false;
        }
    }

    //Denna metoden läser in relevanta värden från databasen och lägger in dessa värden i en SingleRow-lista
    public void makeList(){

        ArrayList<String> games = new ArrayList<String>();
        ArrayList<String> ratings = new ArrayList<String>();
        ArrayList<String> creators = new ArrayList<String>();
        ArrayList<String> IDs = new ArrayList<String>();

        try {
            JSONArray quiz = jsonResponse.getJSONArray("Quiz");
            JSONArray member = jsonResponse.getJSONArray("Members");
            for (int i = 0; i < quiz.length(); i++) {
                JSONObject qInfo = quiz.getJSONObject(i);
                String name = qInfo.getString("Name");
                String rating = qInfo.getString("Rating");
                //I databasen så har värdet för Rating obegränsat med decimaler, men i appen så
                //avrundas det till en decimal
                double ratingDouble = Double.parseDouble(rating);
                NumberFormat formatter = new DecimalFormat("#0.0");
                String ratingDone = formatter.format(ratingDouble);
                String userID = qInfo.getString("UserID");
                games.add(name);
                ratings.add(ratingDone);
                IDs.add(userID);
                for (int x = 0; x < member.length(); x++){

                    JSONObject mInfo = member.getJSONObject(x);
                    String userID2 = mInfo.getString("UserID");
                    if (userID2.equals(userID)){
                        String userName = mInfo.getString("UserName");
                        creators.add(userName);
                    }
                }
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("Här har spelen laddats", ";");
        int listSize = ratings.size();
        for (int i = 0; i < listSize; i++) {
            Log.d("1", ratings.get(i));
        }

        //Här görs arraylist om till en array för att kunna skapa en SingleRow-lista
        //Finns säkert en bättre lösning, men det prioriterades bort eftersom att detta funkar
        String[] title = new String[games.size()];
        title = games.toArray(title);

        String[] rating = new String[ratings.size()];
        rating = ratings.toArray(rating);

        String[] creator = new String[creators.size()];
        creator = creators.toArray(creator);

        String[] ID = new String[IDs.size()];
        ID = IDs.toArray(ID);

        for(int i=0; i<games.size(); i++){
            list.add(new SingleRow(title[i], rating[i], creator[i], ID[i]));
        }
        //Här initieras SingleRow-listan i GameAdapter-klassen
        adapter1 = new GameAdapter(quizMain.this, list);
        gameList.setAdapter(adapter1);
    }


    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this, hub.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

}

    //Sorterings-klasserna
    class SortingComparatorNameAsc implements Comparator<SingleRow> {

        @Override
        public int compare(SingleRow s1, SingleRow s2) {
            return s1.getTitle().compareTo(s2.getTitle());
        }
    }

    class SortingComparatorNameDsc implements Comparator<SingleRow> {

        @Override
        public int compare(SingleRow s1, SingleRow s2) {
            return -s1.getTitle().compareTo(s2.getTitle());
        }
    }

    class SortingComparatorRatingAsc implements Comparator<SingleRow> {

        @Override
        public int compare(SingleRow s1, SingleRow s2) {
            return s1.getRating().compareTo(s2.getRating());
        }
    }

    class SortingComparatorRatingDsc implements Comparator<SingleRow> {

        @Override
        public int compare(SingleRow s1, SingleRow s2) {
            return -s1.getRating().compareTo(s2.getRating());
        }
    }


    //SingleRow-klassen med respektive get och set-metoder
    //ID visas inte i appen, men funkar som ett sökord för databasen eftersom att ID är Primary-key för varje lista
    class SingleRow{
        String title;
        String rating;
        String creator;
        String ID;
        SingleRow(String title, String rating, String creator, String ID){
            super();
            this.title=title;
            this.rating=rating;
            this.creator=creator;
            this.ID=ID;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getRating() {
            return rating;
        }
        public void setRating(String rating) {
            this.rating = rating;
        }
        public String getCreator() {
            return creator;
        }
        public void setCreator(String creator) {
            this.creator = creator;
        }
        public String getID() {
            return ID;
        }
        public void setID(String ID) {
            this.ID = ID;
        }
    }


    //Custom adapter för listan
    class GameAdapter extends BaseAdapter implements Filterable{

        static JSONObject jsonResponse = new JSONObject();
        public static void setJson(JSONObject json){
            jsonResponse = json;
        }

        //http://stackoverflow.com/questions/14118309/how-to-use-search-functionality-in-custom-list-view-in-android
        //Källan för den här skiten

        private ArrayList<SingleRow> OriginalValues; // Originala värden
        private ArrayList<SingleRow> DisplayedValues;    // Värden som visas när man söker
        LayoutInflater inflater;

        public GameAdapter(Context context, ArrayList<SingleRow> list) {
            this.OriginalValues = list;
            this.DisplayedValues = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return DisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class ViewHolder {
            RelativeLayout Container;
            TextView listTitle,listCreator, listRating;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.single_row, null);
                holder.Container = (RelativeLayout) convertView.findViewById(R.id.Container);
                holder.listTitle = (TextView) convertView.findViewById(R.id.listTitle);
                holder.listRating = (TextView) convertView.findViewById(R.id.listRating);
                holder.listCreator = (TextView) convertView.findViewById(R.id.listCreator);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.listTitle.setText(DisplayedValues.get(position).title);
            holder.listRating.setText(DisplayedValues.get(position).rating + "/5");
            holder.listCreator.setText("Created By " + DisplayedValues.get(position).creator);

            holder.Container.setOnClickListener(new View.OnClickListener() {

                //När man trycker på ett objekt i listan så anropas denna metoden.
                //Den tar värdet för ID, och söker sedan på detta i databasen och hämtar de relevanta
                //värdena. Dessa sparas i en bundle som skickas vidare till nästa aktivitet; quizInfo.
                //Slutligen så skickas användaren vidare till nästa aktivitet
                public void onClick(View v) {
                    
                    String s = DisplayedValues.get(position).ID;
                    Log.d("Här skall id stå", "som du tryckte på" + s);

                    String name = "";
                    String desc = "";
                    String rating = "";
                    String rated = "";
                    String played = "";

                    try {
                        JSONArray quiz = jsonResponse.getJSONArray("Quiz");
                        for (int i = 0; i < quiz.length(); i++) {
                            JSONObject info = quiz.getJSONObject(i);
                            String check = info.getString("QID");
                            if (check.equals(s)) {
                                name = info.getString("Name");
                                desc = info.getString("Description");
                                rating = info.getString("Rating");
                                rated = info.getString("Rated");
                                played = info.getString("Played");
                            } else {
                            }
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Bundle b = new Bundle();
                    b.putString("QuizTitle", name);
                    b.putString("QuizDesc", desc);
                    b.putString("QuizRating", rating);
                    b.putString("QuizRated", rated);
                    b.putString("QuizPlayed", played);
                    b.putString("QuizID", s);

                    Intent myIntent = new Intent(quizMain.context, quizInfo.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtras(b);
                    quizMain.context.startActivity(myIntent);


                }
            });

            return convertView;
        }

        //Denna metoden filtrerar värdena i OriginalValues-listan baserat på det användaren har skrivit i sökfältet
        // och sparar dem nya värdena i DisplayedValues som visas på skärmen om sökfältet inte är tomt
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    DisplayedValues = (ArrayList<SingleRow>) results.values; // har dem filtrerade värdena
                    notifyDataSetChanged();  // notifierar datan med dem nya filtrerade värdena
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Håller resultaten för en filtrering av värdena
                    ArrayList<SingleRow> FilteredArrList = new ArrayList<SingleRow>();

                    if (OriginalValues == null) {
                        OriginalValues = new ArrayList<SingleRow>(DisplayedValues); // Sparar den orginala datan i OriginalValues
                    }

                    // Om CharSequence som tas emot från sökfältet är null så returneras OriginalValues
                    // annars genomförs filtreringen och returnerar FilteredArrList

                    if (constraint == null || constraint.length() == 0) {

                        // sätter det orginala resultatet till return
                        results.count = OriginalValues.size();
                        results.values = OriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < OriginalValues.size(); i++) {
                            String t = OriginalValues.get(i).title;
                            String c = OriginalValues.get(i).creator;
                            //Det går att söka på både spelets namn och dess skapare
                            if (t.toLowerCase().startsWith(constraint.toString())||c.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new SingleRow(OriginalValues.get(i).title, OriginalValues.get(i).rating, OriginalValues.get(i).creator, OriginalValues.get(i).ID));
                            }
                        }
                        // sätter det filtrerade resultatet till return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }