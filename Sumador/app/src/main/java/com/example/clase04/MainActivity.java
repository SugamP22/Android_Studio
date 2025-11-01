package com.example.clase04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText num01;
    private EditText num02;

    private Button btn;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            inicializarAttributos();
            return insets;
        });
    }

    private void inicializarAttributos() {
        num01 = findViewById(R.id.inputName);
        num02 = findViewById(R.id.inputSurname);
        btn = findViewById(R.id.btnEnter);
        btn.setOnClickListener(this);
        txtResultado = findViewById(R.id.textViewResultado);
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Please insert a new data", Toast.LENGTH_LONG).show();
                return true;
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEnter) {
            String strNum1 = num01.getText().toString().trim();
            String strNum2 = num02.getText().toString().trim();

            if (strNum1.isEmpty() || strNum2.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int inputNumPrimero = Integer.parseInt(strNum1);
                int inputNumSecundo = Integer.parseInt(strNum2);

                int suma = inputNumPrimero + inputNumSecundo;


                txtResultado.setText("Resultado: " + suma);
                Toast.makeText(this, "Resultado: " + suma, Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            }
        }
    }
}