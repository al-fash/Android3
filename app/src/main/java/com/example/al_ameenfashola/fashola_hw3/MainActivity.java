package com.example.al_ameenfashola.fashola_hw3;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListViewFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ListViewFragment extends Fragment {

        public static final String ARG_OPTION = "option";

        static MyBaseAdapter baseAdapter;


        public static ListViewFragment newInstance(int option) {
            ListViewFragment fragment = new ListViewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_OPTION, option);
            fragment.setArguments(args);

            return fragment;
        }


/*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.listview, container, false);

//            int option = getArguments().getInt(ARG_OPTION);
            rootView = inflater.inflate(R.layout.listview, container, false);
            android.widget.ListView listView = (android.widget.ListView) rootView.findViewById(R.id.listview);

            MovieData movieData = new MovieData();


            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), movieData.getMoviesList(),
                    R.layout.list_row,
                    new String[]{"image", "name", "description"},
                    new int[]{R.id.icon, R.id.title, R.id.description});
            listView.setAdapter(simpleAdapter);

            return rootView;




        }
        */
        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.listview, container, false);
            android.widget.ListView listView = (android.widget.ListView) rootView.findViewById(R.id.listview);
            MovieData movieData = new MovieData();


             baseAdapter = new MyBaseAdapter(getActivity(), movieData.getMoviesList());

            listView.setAdapter(baseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final HashMap<String, ?> itemMap = (HashMap<String, ?>)parent.getItemAtPosition(position);
                    final String item = (String)itemMap.get("name");
                    Toast.makeText(getActivity(), "Movie: " + item, Toast.LENGTH_SHORT).show();
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);

                    final HashMap<String, Boolean> itemMap_bool = (HashMap<String,Boolean>) itemMap;
                    itemMap_bool.put("checkbox", !checkBox.isChecked());
                    checkBox.setChecked(!checkBox.isChecked());
                }

            });

            Button delete = (Button) rootView.findViewById(R.id.delete) ;
            delete.setOnClickListener (new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               ArrayList<HashMap<String, ?>> movies = new ArrayList<HashMap<String, ?>>();
                                               for (int i = 0; i<baseAdapter.getCount(); i++){
                                                   Map<String, ?> temp = (Map<String, ?>) baseAdapter.getItem(i);
                                                   if((Boolean)temp.get("checkbox")){
                                                       movies.add((HashMap<String, ?>) temp);
                                                   }

                                               }
                                               baseAdapter.getmovielist().removeAll(movies);
                                               baseAdapter.notifyDataSetChanged();
                                           }
                                       }) ;

            Button selectAll = (Button) rootView.findViewById(R.id.selectall) ;
            selectAll.setOnClickListener (new View.OnClickListener() {
                @Override
             public void onClick (View view) {
               for (int i = 0; i < baseAdapter.getCount(); i++) {
                  HashMap<String, Boolean> item =
                           (HashMap<String, Boolean>) baseAdapter.getItem(i);
                        item.put("checkbox", true);
                    }
                    baseAdapter.notifyDataSetChanged();
                }
            }) ;

            Button clearAll = (Button) rootView.findViewById(R.id.clearall) ;
            clearAll.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick (View view) {
                 for (int i = 0; i < baseAdapter.getCount(); i++) {
                 HashMap<String, Boolean> item =
                 (HashMap<String, Boolean>) baseAdapter.getItem(i);
                 item.put("checkbox", false);
                 }
                 baseAdapter.notifyDataSetChanged();
                                            }
                }) ;

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    baseAdapter.duplicateItem(position);
                    baseAdapter.notifyDataSetChanged();


                    return false;
                }


            });








            return rootView;
        }

        View CreateView_listview_using_BaseAdapter(LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.listview, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.listview);

            MovieData movieData = new MovieData();

            MyBaseAdapter myBaseAdapter = new MyBaseAdapter(getActivity(), movieData.getMoviesList()) ;

            listView.setAdapter(myBaseAdapter);

            return rootView ;
        }

        View CreateView_listview_with_event (LayoutInflater inflater, ViewGroup container) {
            View rootView = inflater.inflate(R.layout.listview, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final HashMap<String, String> itemMap =
                            (HashMap<String, String>) parent.getItemAtPosition(position);
                    final String item = itemMap.get("name");
                    Toast.makeText(getActivity(), "selected " + item, Toast.LENGTH_SHORT)
                            .show();

                }
            });
            /*
            listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick (AdapterView<?> parent,final View view, int position, long id)

                {
                    final HashMap<String, String> itemMap =
                            (HashMap<String, String>) parent.getItemAtPosition(position);
                    final String item = itemMap.get("name");
                    Toast.makeText(getActivity(), "selected " + item, Toast.LENGTH_SHORT)
                            .show();
                }

            }) ;
            */
            return rootView;
        }

        /*
        public Object getItemAtPosition (int position) {
            T adapter = getAdapter() ;
            return (adapter ==null || position <0) ? null : adapter.getItem(position) ;
        }
        */
  //  public class BaseAdapterActivity extends Activity implements AdapterView.OnItemClickListener {


      //  }
    }
}
