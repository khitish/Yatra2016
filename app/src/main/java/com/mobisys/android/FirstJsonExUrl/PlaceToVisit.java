package com.mobisys.android.FirstJsonExUrl;

/**
 * Created by khitishbiswal on 4/1/16.
 */
public class PlaceToVisit {

    private String name;
    private String imgUrl;
    private int budget;
    private int rank;

    public static PlaceToVisit getDummyObj() {
        PlaceToVisit pv = new PlaceToVisit();
        pv.name = "Place Name";
        pv.imgUrl = "";
        pv.budget = 1000;
        pv.rank = 4;
        return pv;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setParams(String name, String imgUrl, int budget, int rank){
        this.name = name;
        this.imgUrl = imgUrl;
        this.budget = budget;
        this.rank = rank;
    }
}
