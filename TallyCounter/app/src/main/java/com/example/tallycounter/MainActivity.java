package com.example.tallycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView counttextView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        count = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button countButton = findViewById(R.id.countButton);
        Button resetButton = findViewById(R.id.resetButton);
        counttextView = findViewById(R.id.countTextView);

        countButton.setOnClickListener(onClickCountButton);
        resetButton.setOnClickListener(onClickResetButton);
    }


    private View.OnClickListener onClickCountButton = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            count++;
            counttextView.setText(String.valueOf(count));

        }
    };

    private View.OnClickListener onClickResetButton = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            count = 0;
            counttextView.setText(String.valueOf(count));

        }
    };
}
