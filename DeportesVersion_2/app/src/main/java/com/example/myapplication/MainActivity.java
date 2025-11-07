package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputInfo;
    TextView resultado;
    Button enter;
    RadioButton rbHombre;
    RadioButton rbMujer;
    LinearLayout container;

    RadioGroup sex;

    int num;


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
        initializar();
        actualizarCheckBox();


    }

    private void initializar() {
        inputInfo = findViewById(R.id.etInputInfo);
        resultado = findViewById(R.id.tvResultado);
        enter = findViewById(R.id.btnmostrar);
        enter.setOnClickListener(this);
        rbHombre = findViewById(R.id.rbHombre);
        rbMujer = findViewById(R.id.rbMujer);
        sex = findViewById(R.id.rgSexo);
        container = findViewById(R.id.containerCheckBox);
    }

    private void actualizarCheckBox() {
        RadioGroup.OnCheckedChangeListener listener = (group, checkedId) -> mostrarCheckBox();
        sex.setOnCheckedChangeListener(listener);
    }

    private void mostrarCheckBox() {
        int id = sex.getCheckedRadioButtonId();
        if (id == rbHombre.getId()) {
            container.removeAllViews();
            rellenarArray(R.array.Hombre);
        } else {
            container.removeAllViews();
            rellenarArray(R.array.Mujer);
        }


    }

    private void contarDeportes() {
        for (int i = 0; i < container.getChildCount(); i++) {
            CheckBox cb = (CheckBox) container.getChildAt(i);
            num = cb.isChecked() ? num + 1 : num;

        }
    }

    private void rellenarArray(int num) {
        String[] listaDeportes = getResources().getStringArray(num);


        int limite = (sex.getCheckedRadioButtonId() == rbHombre.getId()) ? 3 : 4;

        for (String s : listaDeportes) {
            CheckBox cBox = new CheckBox(this);
            cBox.setText(s);

          
            cBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    int seleccionados = contarSeleccionados();

                    if (seleccionados > limite) {
                        // Exceeded the limit → uncheck and show message
                        buttonView.setChecked(false);
                        Toast.makeText(this, "Solo puedes seleccionar " + limite + " deportes.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            container.addView(cBox);
        }
    }

    private int contarSeleccionados() {
        int count = 0;
        for (int i = 0; i < container.getChildCount(); i++) {
            CheckBox cb = (CheckBox) container.getChildAt(i);
            if (cb.isChecked()) count++;
        }
        return count;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnmostrar) {
            resultado.setText("");
            num = 0;
            contarDeportes();
            String nombre = inputInfo.getText().toString().trim();
            String sexo;
            if (sex.getCheckedRadioButtonId() == -1) sexo = " ";
            else {
                sexo = sex.getCheckedRadioButtonId() == rbHombre.getId() ? "Hombre" : "Mujer";
            }
            resultado.setText(String.format("Nombre: %s%n Sex: %s%n Numero Deportes: %s", nombre, sexo, num));
            inputInfo.setText("");
            sex.clearCheck();
            container.removeAllViews();

        }
    }
}