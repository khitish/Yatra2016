package com.mobisys.android.FirstJsonExUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by khitishbiswal on 10/23/15.
 */
public class ListAdapter extends ArrayAdapter<PlaceToVisit> {

    private final Context context;
    private ArrayList<PlaceToVisit> places;

    public ListAdapter(Context context, ArrayList<PlaceToVisit> values) {
        super(context,-1, values);
        this.context = context;
        this.places = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.place_to_visit, parent, false);

        TextView nameView = (TextView) rowView.findViewById(R.id.poi_name);
        String name = places.get(position).getName();
        nameView.setText(name);

        TextView budgetView = (TextView) rowView.findViewById(R.id.poi_budget);
        int budget = places.get(position).getBudget();
        budgetView.setText(budget + "");

        TextView rankView = (TextView) rowView.findViewById(R.id.poi_rank);
        int rank = places.get(position).getRank();
        rankView.setText(rank + "");

        WebView poiWebView = (WebView) rowView.findViewById(R.id.poi_img);
        String imgUrl = places.get(position).getImgUrl();
        poiWebView.loadUrl(imgUrl);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.poi_id);
        boolean checked = PlanTrip.isChecked(places.get(position));
        checkBox.setChecked(checked);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cv = (CheckBox)v;
                if(cv.isChecked()){
                    PlanTrip.addPlaceToPlan(places.get(position));
                }else{
                    PlanTrip.removePlace(places.get(position));
                }
            }
        });
        return rowView;
    }

}
