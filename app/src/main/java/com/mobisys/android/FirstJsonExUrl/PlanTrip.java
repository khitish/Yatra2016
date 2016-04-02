package com.mobisys.android.FirstJsonExUrl;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PlanTrip extends Fragment {

    Controller controller = null;
    private static ListView listView = null;
    private static Activity activity =null;
    private static ArrayList<PlaceToVisit> places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_plan_trip, container, false);

        listView = (ListView) v.findViewById(R.id.listView);
        activity = this.getActivity();
        setup();

        return v;
    }

    private void setup(){
        try{
            places = new ArrayList<PlaceToVisit>();
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

    public static void addPlaceToPlan(PlaceToVisit pv){
        Log.d(Constants.APP_NAME, "place added :: "+pv.getName());
        places.add(pv);
    }

    public static void removePlace(PlaceToVisit pv){
        if(places.size() == 0){
            return;
        }
        Log.d(Constants.APP_NAME, "place removed :: "+pv.getName());
        places.remove(pv);
    }

    public static boolean isChecked(PlaceToVisit pv){
        return places.contains(pv);
    }

    private void save(){

        JSONObject objToSave = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < places.size(); i++) {
                PlaceToVisit pv = places.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", pv.getName());
                jsonObject.put("imgUrl", pv.getImgUrl());
                jsonArray.put(i, jsonObject);
            }
            objToSave.put("pv", jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
