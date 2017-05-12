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
import android.widget.TextView;
import java.util.ArrayList;

public class Activity1 extends AppCompatActivity {
    private ArrayList<Integer> gridFill = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity1);

        gridFill.add(0);
        final GridView theGrid = (GridView) findViewById(R.id.gridView);
        theGrid.setAdapter(new ArrayAdapter<Integer>(Activity1.this, R.layout.placeholder, gridFill));

        TextView incButton = (TextView) findViewById(R.id.activity1_button);
        incButton.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View V){
                gridFill.add(gridFill.size());
                ((ArrayAdapter)theGrid.getAdapter()).notifyDataSetChanged();
            }
        });
    }
}
