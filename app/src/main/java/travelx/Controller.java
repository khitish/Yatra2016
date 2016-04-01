package travelx;

import android.app.Activity;
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
    public Controller(){

    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public String getFullCityName(String starting){
        return "Bangkok";
    }

    public void sendPOIRequest(String city){
        String url = Constants.BASE_POI_URL;
        url += "apikey="+Constants.APP_KEY;
        String availableCity = getFullCityName(city);
        if(availableCity == ""){
            return;
        }
        url += "&city_name="+availableCity;
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

        @Override
        protected void onPostExecute(String result) {
            Log.d(Constants.APP_NAME, "Calling Post " + result);
            processResult(result);
        }
    }


    private void processResult(String resp){
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
                ((MainActivity)activity).loadList(list);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
