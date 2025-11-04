package com.example.revisionbuscaminas;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener, CompoundButton.OnCheckedChangeListener {

    private EditText inputPrincipal;
    private CheckBox mostrar;
    private GridLayout gridLayout;
    private TextView fallos;
    private TextView aciertos;
    private Button btnBorrar;
    private CreadorBtn creadorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust padding only
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI once
        inicializar();
    }

    private void inicializar() {
        inputPrincipal = findViewById(R.id.inputNum);
        inputPrincipal.setOnEditorActionListener(this);

        mostrar = findViewById(R.id.mostrar);
        mostrar.setOnCheckedChangeListener(this);

        gridLayout = findViewById(R.id.gridGUI);
        fallos = findViewById(R.id.fallos);
        aciertos = findViewById(R.id.aciertos);

        btnBorrar = findViewById(R.id.btnBorar);
        btnBorrar.setOnClickListener(this);

        creadorBtn = new CreadorBtn(this, gridLayout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBorar) {
            if (gridLayout.getChildCount() > 0) {
                Toast.makeText(this, "¡Botones eliminados con éxito!", Toast.LENGTH_SHORT).show();
                reSet();
                mostrar.setChecked(false);
                mostrar.setEnabled(true);
            } else {
                Toast.makeText(this, "Error: faltan botones", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reSet() {
        inputPrincipal.setText("");
        gridLayout.removeAllViews();
        fallos.setText("0");
        aciertos.setText("0");
        inputPrincipal.setEnabled(true);
    }

    @Override
    public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
        if (gridLayout.getChildCount() > 0) {
            creadorBtn.comprobarMostrar();
        } else {
            Toast.makeText(this, "Error: no hay botones para mostrar", Toast.LENGTH_SHORT).show();
            mostrar.setChecked(false);
            mostrar.setEnabled(false);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() != R.id.inputNum) return false;

        boolean actionDone =
                actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);

        if (!actionDone) return false;

        String value = inputPrincipal.getText().toString().trim();
        if (value.isEmpty()) {
            inputPrincipal.setError("Valor inválido");
            reSet();
            inputPrincipal.requestFocus();
            return true;
        }

        try {
            int numPrincipal = Integer.parseInt(value);

            if (numPrincipal < 4 || numPrincipal > 30) {
                inputPrincipal.setError("Introduce un número entre 4 y 30");
                reSet();
                inputPrincipal.requestFocus();
                return true;
            }

            creadorBtn.empiezarJuego(inputPrincipal, numPrincipal, fallos, aciertos, mostrar);
            Toast.makeText(this, "¡Juego creado con éxito!", Toast.LENGTH_SHORT).show();
            inputPrincipal.setText("");
            inputPrincipal.setEnabled(false);

        } catch (NumberFormatException e) {
            inputPrincipal.setError("Debe ser un número válido");
        }

        return true;
    }
}
