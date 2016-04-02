package com.mobisys.android.FirstJsonExUrl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by khitishbiswal on 4/1/16.
 */

public class Controller {
    private String resp = "";
    private Activity activity = null;
    private String reqType;
    public Controller(){

    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setReqType(String type){
        reqType = type;
    }

    public String getFullCityName(String starting){
        String city = "";
        for(int i=0;i<Constants.CITIES.length;i++){
            String currentCity = Constants.CITIES[i];
            String lowerCity = currentCity.toLowerCase();
            String lowerStarting = starting.toLowerCase();
            if(lowerCity.startsWith(lowerStarting)){
                return currentCity;
            }
        }
        return city;
    }

    public void sendPOIRequest(String city){
        setReqType(Constants.BASE_POI_URL);
        String url = Constants.BASE_POI_URL;
        url += "apikey="+ Constants.APP_KEY;
        String availableCity = getFullCityName(city);
        if(availableCity == ""){
            return;
        }
        url += "&city_name="+availableCity;
        sendRequest(url, "GET");
    }

    public void sendNearestAirportReq(double lat, double longi){
        setReqType(Constants.BASE_AIR_URL);
        String url = Constants.BASE_AIR_URL;
        url += "apikey="+ Constants.APP_KEY;
        url += "&latitude="+lat;
        url += "&longitude="+longi;
        sendRequest(url, "GET");
    }

    public void sendTrending(String cityCode){
        setReqType(Constants.BASE_TREND_URL);
        String url = Constants.BASE_TREND_URL;
        url += "apikey="+ Constants.APP_KEY;
        url += "&period=2015-09";
        url += "&origin="+cityCode;
        sendRequest(url, "GET");
    }

    public void  sendRequest(String url, String type){
        new SendHttpRequestTask().execute(url,type);
    }

    private  class SendHttpRequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String url = params[0];
                String type = params[1];
                HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
                con.setRequestMethod(type);
                Log.d(Constants.APP_NAME, "Requesting " + url);
                con.connect();
                int status = con.getResponseCode();
                switch (status) {
                    case HttpURLConnection.HTTP_GONE:
                        Log.d(Constants.APP_NAME, "Request gone");
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        Log.d(Constants.APP_NAME, "Request unauthorised");
                        break;
                    case HttpURLConnection.HTTP_FORBIDDEN:
                        Log.d(Constants.APP_NAME, "Request forbidden");
                        break;
                    case HttpURLConnection.HTTP_OK:
                        Log.d(Constants.APP_NAME, "Request okay");
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                            Log.d(Constants.APP_NAME, response + "");
                        }
                        in.close();
                        Log.d(Constants.APP_NAME, response + "");
                        resp = response.toString();
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        Log.d(Constants.APP_NAME, "Request Not found");
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resp;
        }
        public ProgressDialog prog;
        @Override
        protected void onPostExecute(String result) {

            if (prog != null) {
                prog.dismiss();
                prog = null;
            }
            Log.d(Constants.APP_NAME, "Calling Post Execute" + result);
            if(reqType == Constants.BASE_POI_URL) {
                processPOIResult(result);
            }else if(reqType == Constants.BASE_AIR_URL){
                processAirReq(result);
            }else if(reqType == Constants.BASE_TREND_URL){
                processTrendingReq(result);
            }
        }
    }

    private void processTrendingReq(String resp){
        try{
            JSONObject res = new JSONObject(resp);
            JSONArray trendArray = res.getJSONArray("results");
            ArrayList<TrendingPlace> list = new ArrayList<TrendingPlace>();
            for(int i=0;i<trendArray.length();i++){
                JSONObject trendObj = trendArray.getJSONObject(i);
                String name = trendObj.getString("destination");
                int travellers = trendObj.getInt("travelers");
                int flights = trendObj.getInt("flights");
                TrendingPlace tp = new TrendingPlace();
                tp.setParams(name, flights, travellers);
                list.add(tp);
            }
            if(activity instanceof MainActivity){
                trending.loadList(list);
            }

        }catch (Exception e){

        }
    }

    private void processAirReq(String resp){
        try{
            JSONArray airArray = new JSONArray(resp);
            if(airArray.length() > 0){
                JSONObject jsonObject = airArray.getJSONObject(0);
                String city = jsonObject.getString("city");
                sendTrending(city);
                Log.d(Constants.APP_NAME, city);
            }

        }catch (Exception e){

        }
    }


    private void processPOIResult(String resp){
        try {
            JSONObject res = new JSONObject(resp);
            JSONArray poiArray = res.getJSONArray("points_of_interest");
            ArrayList<PlaceToVisit> list = new ArrayList<PlaceToVisit>();
            for(int i=0;i<poiArray.length();i++){
                JSONObject poiObj = poiArray.getJSONObject(i);
                String imgUrl = poiObj.getString("main_image");
                String title = poiObj.getString("title");
                JSONObject grades = poiObj.getJSONObject("grades");
                int rank = grades.getInt("city_grade");
                Log.d(Constants.APP_NAME, title);
                int budget = 0;
                PlaceToVisit pv = new PlaceToVisit();
                pv.setParams(title, imgUrl, budget, rank);
                list.add(pv);
            }
            if(activity instanceof MainActivity){
                PlanTrip.loadList(list);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
