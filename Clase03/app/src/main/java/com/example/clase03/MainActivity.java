package com.example.clase03;

import   android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText inputNombre;
    private EditText inputApellido;
    private  TextView viewRes;

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_enter){
          String nombre=inputNombre.getText().toString().trim();
          String apellido=inputApellido.getText().toString().trim();
          if(nombre.isEmpty()&&apellido.isEmpty()){
              String  errormsg="Error: Falta datos";
              viewRes.setText(errormsg);
          }else{
              String msg=nombre+" "+apellido;
              viewRes.setText(msg);
              inputApellido.setText("");
              inputNombre.setText("");

          }


        }else{
            System.out.println("Error: id inválido");
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
            inputNombre=findViewById(R.id.input_nombre);
            inputApellido=findViewById(R.id.input_apellido);
            viewRes=findViewById(R.id.viewRes);
            Button btn = findViewById(R.id.btn_enter);
            btn.setOnClickListener(this);
            return insets;
        });
    }
}