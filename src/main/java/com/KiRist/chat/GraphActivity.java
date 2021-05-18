package com.KiRist.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.*;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.LineChart;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class GraphActivity extends AppCompatActivity {

    private LineChart line_view;
    ArrayList<Entry> entry_chart = new ArrayList<>();
    ArrayList<Entry> entry2 = new ArrayList<>();
    int index = 0;

    float[] temp = new float[3];
    float[] humi = new float[3];
    LineData chartData = new LineData();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        String DATA_NAME = intent.getStringExtra("dataName");

        line_view = (LineChart) findViewById(R.id.line_chart);//layout의 id


//        entry_chart.add(new Entry(1, 3));
//        entry_chart.add(new Entry(2, 7));
//        entry_chart.add(new Entry(3, 1));
//        entry_chart.add(new Entry(4, 5));
//        entry_chart.add(new Entry(5, 10));
    /* 만약 (2, 3) add하고 (2, 5)한다고해서
    기존 (2, 3)이 사라지는게 아니라 x가 2인곳에 y가 3, 5의 점이 찍힘 */

        databaseReference.child("chat").child(DATA_NAME).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataDTO Data = dataSnapshot.getValue(DataDTO.class);  // chatData를 가져오고
                Log.e("LOG", "" +Data.getHumi() + " " + Data.getTemp());

                index++;
                temp[index-1] = Data.getTemp();
                humi[index-1] = Data.getHumi();
                entry_chart.add(new Entry(index,humi[index-1]));
                Log.e("LOG", "" +index + " " + humi[index-1] + " " + entry_chart.get(index-1));
                entry2.add(new Entry(index, temp[index-1]));
                Log.e("LOG", "" +index + " " + temp[index-1] + " " + entry2.get(index-1));

                if(index == 3){
                    drawData();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    private void drawData() {
        LineDataSet lineDataSet = new LineDataSet(entry_chart, "습");
        chartData.addDataSet(lineDataSet);

        LineDataSet set2 = new LineDataSet(entry2, "온");
        chartData.addDataSet(set2);

        line_view.setData(chartData);

        Log.e("LOG", "" +temp[0] + " " + humi[0] + " 그래프 업뎃");

        line_view.invalidate();
    }
}
