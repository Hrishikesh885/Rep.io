package com.google.mediapipe.examples.poselandmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainMenu extends AppCompatActivity {
    TextView OurText;
    public int excerise_selection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        OurText= findViewById(R.id.excersie_selec_var) ;
    }

    /** Called when the user touches the button */
    public void PusUP(View view)
    {
//         Do something in response to button click
        excerise_selection = 1;
        OurText.setText(""+excerise_selection );
        ViewSelector();
    }
    public void Squat(View view)
    {
        // Do something in response to button click
        excerise_selection = 2;
        OurText.setText(""+excerise_selection );
        ViewSelector();
    }
    public void DeadLift(View view)
    {
        // Do something in response to button click
        excerise_selection = 3;
        OurText.setText(""+excerise_selection );
        ViewSelector();
    }
    public void Log(View view)
    {
        // Do something in response to button click
        excerise_selection = 4;
        OurText.setText(""+excerise_selection );
        ViewSelector();
    }
    public void ViewSelector(){
        Intent intent = new Intent(this, ViewSelection.class);
        startActivity(intent);
    }

}

