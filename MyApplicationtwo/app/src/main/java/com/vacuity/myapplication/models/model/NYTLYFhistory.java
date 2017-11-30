package com.vacuity.myapplication.models.model;

import com.vacuity.myapplication.models.Business;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Gary Straub on 11/26/2017.
 */

public class NYTLYFhistory {
    private UUID mID;
    private double yelpID;
    private Date mDate;
    private boolean mRating;
    private String mName;
    private Business mBusiness;

    public NYTLYFhistory(String name){
        setmID();
        setmName(name);

    }

    public UUID getmID() {
        return mID;
    }

    public void setmID() {
        this.mID = UUID.randomUUID();
    }

    public double getYelpID() {
        return yelpID;
    }

    public void setYelpID(double yelpID) {
        this.yelpID = yelpID;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismRating() {
        return mRating;
    }

    public void setmRating(boolean mRating) {
        this.mRating = mRating;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Business getmBusiness() {
        return mBusiness;
    }

    public void setmBusiness(Business mBusiness) {
        this.mBusiness = mBusiness;
    }
}
