package com.google.mediapipe.examples.poselandmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

public class ViewSelection extends AppCompatActivity {


    TextView OurText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainActivity.getexcerise_selection()==1)
            setContentView(R.layout.activity_view_selection);
        else if (MainActivity.getexcerise_selection()==2)
            setContentView(R.layout.squat_view);

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
        Toast.makeText(this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
//        MainActivity.setViewSelection(2);
//        OurText.setText(""+MainActivity.getViewSelection());
//        ViewSelector();
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