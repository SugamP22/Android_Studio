package com.example.deportes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etEdad;
    RadioGroup rgSexo;
    LinearLayout contenedorDeportes;
    Button btnMostrar;
    TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        rgSexo = findViewById(R.id.rgSexo);
        contenedorDeportes = findViewById(R.id.contenedorDeportes);
        btnMostrar = findViewById(R.id.btnMostrar);
        tvResultado = findViewById(R.id.tvResultado);

        // Cargar los CheckBoxes desde strings.xml
        String[] deportes = getResources().getStringArray(R.array.deportes);
        for (String deporte : deportes) {
            CheckBox cb = new CheckBox(this);
            cb.setText(deporte);
            contenedorDeportes.addView(cb);
        }

        btnMostrar.setOnClickListener(v -> mostrarResultado());
    }

    private void mostrarResultado() {
        int cantidad = 0;

        // Recorremos el contenedor para contar los seleccionados
        for (int i = 0; i < contenedorDeportes.getChildCount(); i++) {
            CheckBox cb = (CheckBox) contenedorDeportes.getChildAt(i);
            if (cb.isChecked()) cantidad++;
        }

        String nombre = etNombre.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();

        String sexo = "";
        int id = rgSexo.getCheckedRadioButtonId();
        if (id == R.id.rbHombre) sexo = "Hombre";
        else if (id == R.id.rbMujer) sexo = "Mujer";

        String mensaje = "Nombre: " + nombre +
                "\nSexo: " + sexo +
                "\nEdad: " + edad +
                "\nPractica " + cantidad + " deporte(s).";

        tvResultado.setText(mensaje);
    }
}
