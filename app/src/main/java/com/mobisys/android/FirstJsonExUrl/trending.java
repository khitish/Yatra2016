package com.mobisys.android.FirstJsonExUrl;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class trending extends Fragment {

    AppLocationService appLocationService = null;
    Controller controller = null;
    private Fragment fragment = null;
    private static Activity activity =null;
    private static ListView listView = null;

    public trending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trending, container, false);
        fragment = this;
        listView = (ListView) v.findViewById(R.id.listView);
        activity = this.getActivity();
        appLocationService = new AppLocationService(
                this.getActivity());
        setupLocation();
        return v;
    }


    private void setupLocation() {
        Location gpsLocation = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            double latitude = gpsLocation.getLatitude();
            double longitude = gpsLocation.getLongitude();
            String result = "Latitude: " + gpsLocation.getLatitude() +
                    " Longitude: " + gpsLocation.getLongitude();
            controller = new Controller();
            controller.setActivity(this.getActivity());
            controller.sendNearestAirportReq(latitude, longitude);
            Log.d(Constants.APP_NAME, result);
//            LocationAddress locationAddress = new LocationAddress();
//            locationAddress.getAddressFromLocation(latitude, longitude,
//                    this.getActivity(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }
    }

    public static void loadList(ArrayList<TrendingPlace> list){
        TrendListAdapter listAdapter = new TrendListAdapter(activity, list);
        listView.setAdapter(listAdapter);
    }




    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this.getActivity());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        fragment.getActivity().startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    Log.d(Constants.APP_NAME, locationAddress);
                    break;
                default:
                    locationAddress = null;
            }
        }
    }


}
