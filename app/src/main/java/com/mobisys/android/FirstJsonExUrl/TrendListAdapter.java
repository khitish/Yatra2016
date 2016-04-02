package com.mobisys.android.FirstJsonExUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by khitishbiswal on 4/2/16.
 */
public class TrendListAdapter extends ArrayAdapter<TrendingPlace> {

    private final Context context;
    private ArrayList<TrendingPlace> places;

    public TrendListAdapter(Context context, ArrayList<TrendingPlace> values) {
        super(context,-1, values);
        this.context = context;
        this.places = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.trending_place, parent, false);

        TextView nameView = (TextView) rowView.findViewById(R.id.trend_name);
        String name = places.get(position).getName();
        nameView.setText(name);

        TextView travellersView = (TextView) rowView.findViewById(R.id.trend_travellers);
        int travellers = places.get(position).getTravellers();
        travellersView .setText(travellers + " visitors");

        TextView flightsView = (TextView) rowView.findViewById(R.id.trend_flights);
        int flights = places.get(position).getFlights();
        flightsView.setText(flights + " flights");

        return rowView;
    }

}
