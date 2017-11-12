package com.vacuity.myapplication.models;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Created by Ranga on 2/24/2017.
 */

public class Center
{
    @JsonGetter("latitude")
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    double latitude;

    @JsonGetter("longitude")
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    double longitude;
}
