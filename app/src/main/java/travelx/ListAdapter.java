package travelx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobisys.android.FirstJsonExUrl.R;

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

    public PlaceToVisit get(int pos){
        if(pos < this.places.size()){
            return this.places.get(pos);
        }else{
            return null;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        return rowView;
    }

}
