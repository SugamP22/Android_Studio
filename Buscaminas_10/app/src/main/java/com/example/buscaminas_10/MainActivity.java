package com.example.buscaminas_10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputBtn;
    Button btnEnter;
    GridLayout gridlayout;
    Button btnBorar;
    TextView fallos;
    TextView aciertos;
    CreadorButton crearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
    }

    // Example method to initialize components
    private void initializeUI() {
        inputBtn = findViewById(R.id.inputNumBtn);
        btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        gridlayout = findViewById(R.id.grid);
        btnBorar = findViewById(R.id.btnBorrar);
        btnBorar.setOnClickListener(this);
        fallos = findViewById(R.id.fallos);
        aciertos = findViewById(R.id.aciertos);
        crearBtn = new CreadorButton(this, gridlayout);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnEnter) {
            gridlayout.removeAllViews();
            try {
                int num = Integer.parseInt(inputBtn.getText().toString().trim());
                inputBtn.setText("");
                if (num < 4 || num > 30) {
                    Toast.makeText(this, "Error: Inválido número", Toast.LENGTH_SHORT).show();

                } else {

                    crearBtn.crearBtn(num, this, fallos, aciertos);
                }

                return;
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Error: Inválido número", Toast.LENGTH_SHORT).show();

            }


        } else if (id == R.id.btnBorrar) {

            gridlayout.removeAllViews();
            aciertos.setText("Aciertos : 0");
            fallos.setText("Fallos: 0");

        }
    }

}


