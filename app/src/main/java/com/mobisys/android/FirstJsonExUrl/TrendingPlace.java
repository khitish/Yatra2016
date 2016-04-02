package com.mobisys.android.FirstJsonExUrl;

/**
 * Created by khitishbiswal on 4/2/16.
 */
public class TrendingPlace {
    private String name;
    private int flights;
    private int travellers;

    public String getName() {
        return name;
    }

    public int getFlights(){
        return flights;
    }

    public int getTravellers(){
        return travellers;
    }


    public void setParams(String name, int flights, int travellers){
        this.name = name;
        this.flights = flights;
        this.travellers = travellers;
    }
}
