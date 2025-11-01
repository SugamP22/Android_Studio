package com.example.clase05;



import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ascensor.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputNum;
    private Button enterBtn;

    private Button borrar;
    private GridLayout gridLayout;
    private BtnCreador btnManager;



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnEnter) {
            try {
                int num = Integer.parseInt(inputNum.getText().toString().trim());
                System.out.println(num);
                if ((num < 1) || (num > 10)) {
                    Toast.makeText(this, "Error: Valor inválido!!", Toast.LENGTH_SHORT).show();
                    inputNum.setText("");
                    return;
                }
                crearButton(num);

            } catch (Exception e) {
                Toast.makeText(this, "Error: Valor inválido!!", Toast.LENGTH_SHORT).show();
                inputNum.setText("");
            }

        } else if (id == R.id.borador) {
            btnManager.clear();
            Toast.makeText(this, "Borrado con exitó!!", Toast.LENGTH_SHORT).show();

        }
    }

    private void crearButton(int num) {
        btnManager.clear();
        inputNum.setText("");
        btnManager.aniadirBtn(num, this);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            inputNum = findViewById(R.id.inputnumber);
            enterBtn = findViewById(R.id.btnEnter);
            enterBtn.setOnClickListener(this);
            borrar = findViewById(R.id.borador);
            borrar.setOnClickListener(this);
            gridLayout = findViewById(R.id.gridlayout01);
            btnManager = new BtnCreador(this, gridLayout);
            return insets;
        });
    }


}