package com.example.inflatingconstraintlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private ConstraintLayout taskLayout1, taskLayout2, taskLayout3, taskLayout4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        inflater = getLayoutInflater();

        ViewGroup parentView = findViewById(R.id.taskLayout);
        final ConstraintLayout innerArea = (ConstraintLayout)
                inflater.inflate(R.layout.busy_layout5, parentView, false);
        parentView.addView(innerArea);

        taskLayout1 = (ConstraintLayout)
                inflater.inflate(R.layout.busy_layout2, null);

        taskLayout2 = (ConstraintLayout)
                inflater.inflate(R.layout.busy_layout4, null);
        taskLayout3 = (ConstraintLayout)
                inflater.inflate(R.layout.busy_layout5, null);
        taskLayout4 = (ConstraintLayout)
                inflater.inflate(R.layout.busy_layout6, null);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateConstraintLayout(R.layout.busy_layout2);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateConstraintLayout(R.layout.busy_layout4);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateConstraintLayout(R.layout.busy_layout5);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateConstraintLayout(R.id.taskLayoutSpecial);
            }
        });
    }

    private void inflateConstraintLayout(int layoutToInflate){
        ViewGroup parentView = findViewById(R.id.taskLayout);
        parentView.removeAllViews();
        final ConstraintLayout innerArea = (ConstraintLayout)
                inflater.inflate(layoutToInflate, parentView, false);
        innerArea.setBackgroundColor(Color.BLUE);
        parentView.addView(innerArea);
    }
}

