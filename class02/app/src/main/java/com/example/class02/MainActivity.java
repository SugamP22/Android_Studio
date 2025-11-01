package com.example.class02;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private boolean isYlo=false;
private  boolean isrd=false;
private  boolean isblu=false;
    @Override
    public void onClick(View v) {
        int vid=v.getId();
    if(vid==R.id.twitch_blue)runBtnblue();
    else if(vid==R.id.red_button)runBtnRed();
    else if(vid==R.id.yellow_button)runBtnYlo();
    else System.out.println("Error: valor inválido!!");
        }

    private void runBtnYlo() {
        RelativeLayout base=findViewById(R.id.main);
        if(!isYlo){
            base.setBackgroundColor(getResources().getColor(R.color.yellow));
            isYlo=true;
        }else{
            base.setBackgroundColor(getResources().getColor(R.color.white));
            isYlo=false;
        }
    }

    private void runBtnRed() {
        RelativeLayout base=findViewById(R.id.main);
        if(!isrd){
            base.setBackgroundColor(getResources().getColor(R.color.red));
            isrd=true;
        }else{
            base.setBackgroundColor(getResources().getColor(R.color.white));
            isrd=false;
        }
    }

    private void runBtnblue() {
        RelativeLayout base=findViewById(R.id.main);
        if(!isblu){
            base.setBackgroundColor(getResources().getColor(R.color.twitchBlue));
            isblu=true;
        }else{
            base.setBackgroundColor(getResources().getColor(R.color.white));
            isblu=false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Button btnBlue=findViewById(R.id.twitch_blue);
            btnBlue.setOnClickListener(this);
            Button btnred=findViewById(R.id.red_button);
            btnred.setOnClickListener(this);
            Button btnYelo=findViewById(R.id.yellow_button);
            btnYelo.setOnClickListener(this);
            return insets;





        });
    }
}



