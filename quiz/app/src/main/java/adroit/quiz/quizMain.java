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
                // When user changed the Text
                //quizMain.this.adapter1.getFilter().filter(cs);
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



        /*gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = String.valueOf(parent.getItemAtPosition(0));
                String str = "";
                String rating = "";
                String rated = "";
                String played = "";
                String key = "";

                try {
                    JSONArray quiz = jsonResponse.getJSONArray("Quiz");
                    for (int i = 0; i < quiz.length(); i++) {
                        JSONObject info = quiz.getJSONObject(i);
                        String check = info.getString("Name");
                        if (check.equals(s)) {
                            //JSONObject desc = quiz.getJSONObject(i);
                            str = info.getString("Description");
                            rating = info.getString("Rating");
                            rated = info.getString("Rated");
                            played = info.getString("Played");
                            key = info.getString("QID");
                        } else {
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                Bundle b = new Bundle();
                b.putString("QuizTitle", s);
                b.putString("QuizDesc", str);
                b.putString("QuizRating", rating);
                b.putString("QuizRated", rated);
                b.putString("QuizPlayed", played);
                b.putString("QuizID", key);

                Intent myIntent = new Intent(view.getContext(), quizInfo.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
            }
        });*/

    }




    private void initialize() {
        gameList = (ListView) findViewById(R.id.gameList);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
    }

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
                String userID = qInfo.getString("UserID");
                games.add(name);
                ratings.add(rating);
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


    class GameAdapter extends BaseAdapter implements Filterable{

        static JSONObject jsonResponse = new JSONObject();
        public static void setJson(JSONObject json){
            jsonResponse = json;
        }

        /*ArrayList<SingleRow> list;
        Context context;
        GameAdapter(Context c, ArrayList<SingleRow> list){
            context = c;
            this.list = new ArrayList<SingleRow>();*/

        //http://stackoverflow.com/questions/14118309/how-to-use-search-functionality-in-custom-list-view-in-android
        //Källan för den här skiten

        private ArrayList<SingleRow> mOriginalValues; // Original Values
        private ArrayList<SingleRow> mDisplayedValues;    // Values to be displayed
        LayoutInflater inflater;

        public GameAdapter(Context context, ArrayList<SingleRow> list) {
            this.mOriginalValues = list;
            this.mDisplayedValues = list;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
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
            RelativeLayout llContainer;
            TextView listTitle,listCreator, listRating;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.single_row, null);
                holder.llContainer = (RelativeLayout) convertView.findViewById(R.id.llContainer);
                holder.listTitle = (TextView) convertView.findViewById(R.id.listTitle);
                holder.listRating = (TextView) convertView.findViewById(R.id.listRating);
                holder.listCreator = (TextView) convertView.findViewById(R.id.listCreator);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.listTitle.setText(mDisplayedValues.get(position).title);
            holder.listRating.setText(mDisplayedValues.get(position).rating + "/5");
            holder.listCreator.setText("Created By " + mDisplayedValues.get(position).creator);

            holder.llContainer.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    
                    String s = mDisplayedValues.get(position).ID;
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
                                //JSONObject desc = quiz.getJSONObject(i);
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
                    myIntent.putExtras(b);
                    quizMain.context.startActivity(myIntent);
                }
            });

            return convertView;
        }

        /*
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row, parent, false);

            TextView title = (TextView) row.findViewById(R.id.listTitle);
            TextView rating = (TextView) row.findViewById(R.id.listRating);
            TextView creator = (TextView) row.findViewById(R.id.listCreator);

            SingleRow temp = list.get(position);

            Log.d("111111111", "2" + temp.rating);

            title.setText(temp.title);
            rating.setText(temp.rating + "/5");
            creator.setText("Created By " + temp.creator);

            return row;
        }*/

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {

                    mDisplayedValues = (ArrayList<SingleRow>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<SingleRow> FilteredArrList = new ArrayList<SingleRow>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<SingleRow>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String t = mOriginalValues.get(i).title;
                            String c = mOriginalValues.get(i).creator;
                            if (t.toLowerCase().startsWith(constraint.toString())||c.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new SingleRow(mOriginalValues.get(i).title, mOriginalValues.get(i).rating, mOriginalValues.get(i).creator, mOriginalValues.get(i).ID));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }