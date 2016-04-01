package com.mobisys.android.FirstJsonExUrl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listView = null;
    private Context context = null;
    private Activity activity = null;
    Controller controller = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        listView = (ListView) findViewById(R.id.listView);
        setup();
    }

    private void setup(){
        try{
            controller = new Controller();
            controller.setActivity(activity);
            controller.sendPOIRequest("B");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void loadList(ArrayList<PlaceToVisit> list){
        ListAdapter listAdapter = new ListAdapter(context, list);
        listView.setAdapter(listAdapter);
    }

}
