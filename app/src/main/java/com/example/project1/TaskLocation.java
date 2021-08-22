package com.example.project1;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class TaskLocation implements Serializable {
    public double longitude;
    public double latitude;
    public TaskLocation(){

    }
    public TaskLocation(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
