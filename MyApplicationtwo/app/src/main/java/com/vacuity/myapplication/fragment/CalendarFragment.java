package com.vacuity.myapplication.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.models.model.NYTLYFhistory;
import com.vacuity.myapplication.models.model.nytlyfLab;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gary Straub on 11/30/2017.
 */

public class CalendarFragment extends Fragment {
    CalendarView mCalendar;
    private ArrayList<NYTLYFhistory> mHistory;
    TextView mDateDisplay;
    ListView mListView;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getActionBar().hide();

    }

    private void setHistory(){
        nytlyfLab tLab = nytlyfLab.get(getContext());
        mHistory = tLab.getHistory();
    }
    private ArrayList<String> getRelevant(Date tm){
        setHistory();
        ArrayList<String> myResults = new ArrayList<>();

        Calendar tmp = toCalendar(tm);
        tmp.set(Calendar.MILLISECOND, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.HOUR, 0);


        Calendar tmp2 = tmp;
//        tmp2.add(Calendar.DAY_OF_MONTH, 1);



        for(int i =0; i< mHistory.size(); i++){

//            Log.e("List", mHistory.get(i).getmDate().toString());
//            Log.e("List", tmp2.toString());
            Calendar hist = toCalendar(mHistory.get(i).getmDate());
            hist.set(Calendar.MILLISECOND, 0);
            hist.set(Calendar.SECOND, 0);
            hist.set(Calendar.MINUTE, 0);
            hist.set(Calendar.HOUR, 0);

            if(tmp.get(Calendar.DAY_OF_YEAR) == hist.get(Calendar.DAY_OF_YEAR)){
                myResults.add(mHistory.get(i).getmName());
                Log.e("List", "getting results");
            }
        }
        return myResults;
    }
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_view, container, false);

        mListView = (ListView) v.findViewById(R.id.list_view);

        mDateDisplay = (TextView) v.findViewById(R.id.calendar_date);




        mCalendar = (CalendarView) v.findViewById(R.id.calendarView);
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month+1) + "/" + + dayOfMonth + "/" + year;
                mDateDisplay.setText(date);
                Date tmp = new Date(year-1900, month, dayOfMonth);
                ArrayList<String> tname = getRelevant(tmp);
                String [] test = {"Hello1", "Hello2"};
                String [] testTwo = tname.toArray(new String[tname.size()]);

                ListAdapter myListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, testTwo);
                mListView.setAdapter(myListAdapter);

            }
        });



        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }
}
