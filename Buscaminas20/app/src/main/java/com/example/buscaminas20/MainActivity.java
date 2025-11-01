package com.example.buscaminas20;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, CompoundButton.OnCheckedChangeListener {
    EditText inputPrincipal;
    CheckBox mostrar;
    GridLayout gridMain;
    TextView resMinas;
    TextView resAciertos;
    Button btnBorarMain;
    Creadorbtn creador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            inicilaizarAttributos();
            return insets;
        });
    }

    private void inicilaizarAttributos() {
        inputPrincipal = findViewById(R.id.inputNumber);
        inputPrincipal.setOnEditorActionListener(this);
        mostrar = findViewById(R.id.mostrar);
        mostrar.setOnCheckedChangeListener(this);
        gridMain = findViewById(R.id.mainGrid);
        resMinas = findViewById(R.id.numMinas);
        resAciertos = findViewById(R.id.numAciertos);
        btnBorarMain = findViewById(R.id.btnBorar);
        btnBorarMain.setOnClickListener(this);
        creador = new Creadorbtn(this, gridMain);

        // Initialize scores and disable hint by default
        resMinas.setText("0");
        resAciertos.setText("0");
        mostrar.setEnabled(false);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnBorar) {
            gridMain.removeAllViews();
            inputPrincipal.setText("");
            resMinas.setText("0");
            resAciertos.setText("0");
            mostrar.setChecked(false);
            mostrar.setEnabled(false); // Should be disabled after board clear
            Toast.makeText(this, "Datos borado con exitó!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int id = v.getId();
        if (id == R.id.inputNumber) {
            boolean esEnter =
                    actionId == EditorInfo.IME_ACTION_DONE ||
                            (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN);
            if (!esEnter) return false;
            String text = inputPrincipal.getText().toString().trim();
            if (text.isEmpty()) {
                inputPrincipal.setError("No puede ser vació!!");
                inputPrincipal.requestFocus();
            } else {
                int num = Integer.parseInt(text); // Safe to parse here after isEmpty check

                if (num < 4 || num > 32) {
                    inputPrincipal.setText("");
                    inputPrincipal.setError("Valor inválido!");
                    inputPrincipal.requestFocus();
                    return true;
                }

                gridMain.removeAllViews();

                // Creadorbtn.empiezarACrear will now handle resetting and enabling the CheckBox.
                // We keep these two lines here to ensure immediate visual feedback before Creadorbtn runs:
                mostrar.setChecked(false);
                mostrar.setEnabled(true);

                creador.empiezarACrear(num, resMinas, resAciertos, mostrar, this);
                inputPrincipal.setText("");


            }
            return true;
        }


        return false;
    }


    @Override
    public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();

        if (id == R.id.mostrar) {
            // FIX: Only run logic when the box is checked (toggled ON)
            if (isChecked) {
                if (gridMain.getChildCount() > 0) {
                    creador.mostrarMina(mostrar);
                } else {
                    Toast.makeText(this, "Esperando para empiezar", Toast.LENGTH_SHORT).show();
                    mostrar.setChecked(false); // Uncheck it since no game is running
                }
            }
        }
    }
}