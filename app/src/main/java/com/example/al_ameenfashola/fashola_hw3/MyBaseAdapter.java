package com.example.al_ameenfashola.fashola_hw3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by al-ameenfashola on 2/4/15.
 */
public class MyBaseAdapter extends BaseAdapter {


    private final Context context;
    private final List<Map<String, ?>> movieList;
    private final int option  ;

    public MyBaseAdapter(Context context, List<Map<String,?>> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.option= 1 ;
    }

    public class ViewHolder {

        ImageView image ;
        TextView name ;
        TextView description ;

    }



    @Override
    public boolean isEnabled(int position){
        if (position%3==0) {
            return false ;
        }
    return true ;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }


    public void removeItem(int position) {movieList.remove(position) ;}





    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder= null;

        //    rowView= getView_basic_with_customization( position, view, parent) ;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);


        ImageView image = (ImageView) rowView.findViewById(R.id.icon);
        TextView name = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);

        Map<String, ?> entry = movieList.get(position);
        image.setImageResource((Integer) entry.get("image"));
        name.setText((String) entry.get("name"));
        description.setText((String) entry.get("description"));
        checkBox.setChecked((Boolean)entry.get("checkbox"));



        if (position%2==0) {
            System.out.println("test even");
            name.setTextColor(Color.RED);
            rowView.setBackgroundColor(Color.BLACK);
            description.setTextColor(Color.WHITE);
        }
        else {
            System.out.println("test odd");
            name.setTextColor(Color.GREEN) ;
            rowView.setBackgroundColor(Color.BLACK);
            description.setTextColor(Color.WHITE) ;
        }

        return rowView;


    }




    View getView_basic_with_customization(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);


        ImageView image = (ImageView) rowView.findViewById(R.id.icon);
        TextView name = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);


        Map<String, ?> entry = movieList.get(position);
        image.setImageResource((Integer) entry.get("image"));
        name.setText((String) entry.get("name"));
        description.setText((String) entry.get("description"));

        name.setTextColor (Color.RED) ;
        if (position%2==0) description.setTextColor (Color.BLUE) ;
        else description.setTextColor (Color.GREEN) ;

        return rowView;
    }

    public void duplicateItem(int position ) {

        HashMap<String, ?> movie=(HashMap<String, ?>) movieList.get(position) ;
        movieList.add(position, (HashMap<String, ?>) movie.clone());
    }
    public List<Map<String, ?>> getmovielist(){
        return movieList;
    }
}


