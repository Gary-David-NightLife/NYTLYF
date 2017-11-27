package com.vacuity.myapplication.models.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gary Straub on 11/26/2017.
 */

public class nytlyfLab {
    private static ArrayList<NYTLYFhistory> NYTLYFhistory;
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
            someName = someName.concat(Integer.toString(i));
            NYTLYFhistory nytlyFhistory = new NYTLYFhistory(someName);
            Date tDate = new Date(2017, 10, 4+i);
            nytlyFhistory.setmDate(tDate);
            NYTLYFhistory.add(nytlyFhistory);
        }
    }


}
