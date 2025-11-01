package com.example.clase05;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.ascensor.R;


public class BtnCreador {
    private final Context context;
    private final GridLayout gridlayout;


    public BtnCreador(Context context, GridLayout gridLayout) {
        this.context = context;
        this.gridlayout = gridLayout;

    }

    public void aniadirBtn(int num, View.OnClickListener listener) {
        clear();
        for (int i = 1; i <= num; i++) {
            Button btn = new Button(context);
            btn.setId(View.generateViewId());
            String nombre = "Piso-" + i;
            btn.setText(nombre);
                btn.setTextColor(ContextCompat.getColor(context,R.color.white));
            btn.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            btn.setOnClickListener(listener);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            gridlayout.addView(btn, params);


        }
        Toast.makeText(context, "Buttons added succesfully!!", Toast.LENGTH_SHORT).show();


    }

    public void clear() {
        gridlayout.removeAllViews();

    }

}
