package com.vacuity.myapplication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gary Straub on 7/24/2017.
 */

public class MeetUpLab {
    private  List<MeetUp> mMeetUps;
    private static MeetUpLab sMeetUpLab;


    public MeetUpLab get(){
        if(sMeetUpLab == null){
            sMeetUpLab = new MeetUpLab();
        }

        return sMeetUpLab;
    }
    private MeetUpLab(){
        mMeetUps = new ArrayList<>();
    }

    public List<MeetUp> getList(){
        return mMeetUps;
    }
    public MeetUp getMeetUp(UUID id){
        for(MeetUp tMU : mMeetUps){
            if(tMU.getmID().equals(id)){
                return tMU;
            }
        }
        return null;
    }
    public void setLIST(List<MeetUp> newList){
    mMeetUps = newList;
    }






}
