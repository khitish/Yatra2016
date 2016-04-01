package com.mobisys.android.FirstJsonExUrl;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class PlanTrip extends Fragment {

    Controller controller = null;
    private static ListView listView = null;
    private static Activity activity =null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_plan_trip, container, false);
        int position = getArguments().getInt("position");
        String[] rivers = getResources().getStringArray(R.array.rivers);

        getActivity().getActionBar().setTitle(rivers[position]);
        listView = (ListView) v.findViewById(R.id.listView);
        activity = this.getActivity();
        setup();

        return v;
    }

    private void setup(){
        try{
            controller = new Controller();
            controller.setActivity(this.getActivity());
            controller.sendPOIRequest("G");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadList(ArrayList<PlaceToVisit> list){
        ListAdapter listAdapter = new ListAdapter(activity, list);
        listView.setAdapter(listAdapter);
    }





}
