package com.google.mediapipe.examples.poselandmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Context;

public class ViewSelection extends AppCompatActivity {


    TextView OurText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selection);
        OurText= findViewById(R.id.View_selection_var) ;
    }

    /** Called when the user touches the button */
    public void FrontView(View view)
    {
        MainActivity.setViewSelection(1);
        OurText.setText(""+MainActivity.getViewSelection());
        ViewSelector();
    }
    public void SideView(View view)
    {
        MainActivity.setViewSelection(2);
        OurText.setText(""+MainActivity.getViewSelection());
        ViewSelector();
    }
    public void ViewSelector(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void Home(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    public void Home(View view)
    {
        Home();
    }

}