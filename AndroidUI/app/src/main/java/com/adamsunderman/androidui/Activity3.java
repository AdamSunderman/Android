package com.adamsunderman.androidui;

import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity3);

        final TextView theText = (TextView) findViewById(R.id.act3_textView);
        theText.setText(String.format("%d",counter));

        TextView incButton = (TextView) findViewById(R.id.act3_inc_button);
        incButton.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View V){
                counter = counter + 1;
                theText.setText(String.format("%d",counter));
            }
        });

        TextView decButton = (TextView) findViewById(R.id.act3_dec_button);
        decButton.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View V){
                counter = counter - 1;
                theText.setText(String.format("%d",counter));
            }
        });
    }
}

