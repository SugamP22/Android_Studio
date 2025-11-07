package com.example.viaje;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgTransporte, rgHoteles, rgComida, rgOcio                ;
    EditText etTransporte, etHoteles, etComida, etOcio;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarObjetos();
        rellenarContenedores();
        configurarListeners();
    }

    private void inicializarObjetos() {
        rgTransporte = findViewById(R.id.rgtransporte);
        rgHoteles = findViewById(R.id.rghoteles);
        rgComida = findViewById(R.id.rgcomida);
        rgOcio = findViewById(R.id.rgocio);
        resultado = findViewById(R.id.resultado);
        etTransporte = findViewById(R.id.etTransporte);
        etHoteles = findViewById(R.id.etHoteles);
        etComida = findViewById(R.id.etComida);
        etOcio = findViewById(R.id.etOcio);
    }


    private void rellenarContenedores() {
        String[] listaTransporte = getResources().getStringArray(R.array.transporte);
        for (String item : listaTransporte) {
            RadioButton rb = new RadioButton(this);
            rb.setText(item);
            rgTransporte.addView(rb);
        }

        String[] listaHoteles = getResources().getStringArray(R.array.hoteles);
        for (String item : listaHoteles) {
            RadioButton rb = new RadioButton(this);
            rb.setText(item);
            rgHoteles.addView(rb);
        }

        String[] listaComida = getResources().getStringArray(R.array.comida);
        for (String item : listaComida) {
            RadioButton rb = new RadioButton(this);
            rb.setText(item);
            rgComida.addView(rb);
        }

        String[] listaOcio = getResources().getStringArray(R.array.ocio);
        for (String item : listaOcio) {
            RadioButton rb = new RadioButton(this);
            rb.setText(item);
            rgOcio.addView(rb);
        }
    }

    private void configurarListeners() {
        RadioGroup.OnCheckedChangeListener radioListener = (group, checkedId) -> recalcularTotal();
        rgTransporte.setOnCheckedChangeListener(radioListener);
        rgHoteles.setOnCheckedChangeListener(radioListener);
        rgComida.setOnCheckedChangeListener(radioListener);
        rgOcio.setOnCheckedChangeListener(radioListener);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recalcularTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etTransporte.addTextChangedListener(watcher);
        etHoteles.addTextChangedListener(watcher);
        etComida.addTextChangedListener(watcher);
        etOcio.addTextChangedListener(watcher);
    }

    private void recalcularTotal() {
        double total = 0;

        total += calcularSeccion(rgTransporte, etTransporte);
        total += calcularSeccion(rgHoteles, etHoteles);
        total += calcularSeccion(rgComida, etComida);
        total += calcularSeccion(rgOcio, etOcio);

        resultado.setText("Precio total: " + total);
    }

    private double calcularSeccion(RadioGroup group, EditText personasField) {
        int checkedId = group.getCheckedRadioButtonId();
        if (checkedId == -1) return 0; // nada seleccionado

        RadioButton rb = findViewById(checkedId);
        String text = rb.getText().toString();
        double precio = extraerNumero(text);

        int personas = 1;
        String personasStr = personasField.getText().toString().trim();
        if (!personasStr.isEmpty()) {
            try {
                personas = Integer.parseInt(personasStr);
                if (personas < 1) personas = 1;
            } catch (NumberFormatException e) {
                personas = 1;
            }
        }

        return precio * personas;
    }

    private double extraerNumero(String texto) {
        try {
            String[] partes = texto.split("-");
            return Double.parseDouble(partes[1].trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
