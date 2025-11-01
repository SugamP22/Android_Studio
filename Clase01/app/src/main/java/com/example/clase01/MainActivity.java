package com.example.clase01;

import   android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static boolean redBackground=false;
    private static boolean isNoSelected=false;
    private static boolean isYesSelected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    public void onClickYes(View view) {
        TextView txt= findViewById(R.id.TitleMainFrame);
        if(!isYesSelected){

        txt.setText("Yes Bro u the best");
        isYesSelected=true;
        }else{

            txt.setText("¿Are you the best?");
            isYesSelected=false;
        }

    }

    @SuppressLint("SetTextI18n")
    public void onClickNO(View view) {
        TextView txt=findViewById(R.id.TitleMainFrame);
       if(!isNoSelected){
           txt.setText("No bro sorry you not the best");
           isNoSelected=true;
       }else{
           txt.setText("¿Are you the best?");
           isNoSelected=false;
       }
    }

    public void OnClickThrid(View view) {
        RelativeLayout rootLayout = findViewById(R.id.main);
       if(!redBackground){
           rootLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.twitcBlue));
           redBackground=true;
       }else{
           rootLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
           redBackground = false;
       }
    }


}