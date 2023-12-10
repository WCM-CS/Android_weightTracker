package com.zybooks.weightlogv2.model.MainModels;

public class DailyWeightsModel {
    private String weight;
    private String date;

    public DailyWeightsModel(String weight, String date) {
        this.weight = weight;
        this.date = date;
    }

    public String getWeight(){
        return this.weight;
    }

    public String getDate(){
        return this.date;
    }


}
