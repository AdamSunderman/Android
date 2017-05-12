package com.adamsunderman.androidui;

import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
    private ArrayList<Integer> listFill = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity2);

        listFill.add(0);
        final ListView theList = (ListView) findViewById(R.id.listView);
        theList.setAdapter(new ArrayAdapter<Integer>(Activity2.this, R.layout.placeholder, listFill));

        TextView incButton = (TextView) findViewById(R.id.activity2_button);
        incButton.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View V){
                listFill.add(listFill.size());
                ((ArrayAdapter)theList.getAdapter()).notifyDataSetChanged();
            }
        });
    }
}

