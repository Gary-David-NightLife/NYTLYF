package com.vacuity.myapplication.models.model;

import android.content.Context;

import com.vacuity.myapplication.models.Business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gary Straub on 11/26/2017.
 */

public class nytlyfLab {
    private ArrayList<NYTLYFhistory> NYTLYFhistory;
    private static nytlyfLab sNytlyfLab;

    public static nytlyfLab get(Context context){
        if(sNytlyfLab == null){
            sNytlyfLab = new nytlyfLab(context);
        }

        return sNytlyfLab;
    }
    private nytlyfLab(Context context){
        NYTLYFhistory = new ArrayList<>();
        generateFake(20);
    }

    private void generateFake(int s){
        String someName = "Some Name ";
        for(int i =0; i<s; i++){
            String t = someName + Integer.toString(i);
            NYTLYFhistory nytlyFhistory = new NYTLYFhistory(t);
            Date tDate = new Date(117, 10, 4+i);
            nytlyFhistory.setmDate(tDate);
            NYTLYFhistory.add(nytlyFhistory);
        }
    }
    public ArrayList<NYTLYFhistory> getHistory(){
        return this.NYTLYFhistory;
    }
    public void add(Business bus){
        NYTLYFhistory nytlyFhistory = new NYTLYFhistory(bus.getName());
        Date time = Calendar.getInstance().getTime();
        nytlyFhistory.setmDate(time);
        NYTLYFhistory.add(nytlyFhistory);
    }


}
