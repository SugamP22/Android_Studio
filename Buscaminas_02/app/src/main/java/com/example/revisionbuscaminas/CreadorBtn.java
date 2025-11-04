package com.example.revisionbuscaminas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CreadorBtn {

    private final Context context;
    private final GridLayout gridLayout;
    private boolean mostrado = false;

    private int numTotal;
    private int numMinas;
    private int numBotones;

    private TextView aciertos;
    private TextView fallos;
    private CheckBox mostrar;
    private EditText inputPrincipal;

    public CreadorBtn(Context context, GridLayout gridLayout) {
        this.context = context;
        this.gridLayout = gridLayout;
    }

    public void empiezarJuego(EditText inputPrincipal, int num, TextView fallos, TextView aciertos, CheckBox mostrar) {
        this.mostrado = false;
        this.aciertos = aciertos;
        this.fallos = fallos;
        this.mostrar = mostrar;
        this.inputPrincipal = inputPrincipal;

        this.numTotal = num;
        this.numMinas = numTotal / 4;
        this.numBotones = numTotal - numMinas;

        this.mostrar.setEnabled(true);
        this.mostrar.setChecked(false);

        gridLayout.removeAllViews();

        ArrayList<String> celdas = new ArrayList<>();
        for (int i = 0; i < numMinas; i++) celdas.add("M");
        for (int i = 0; i < numBotones; i++) celdas.add("N/A");

        Collections.shuffle(celdas);

        for (String celda : celdas) {
            Button btn = new Button(context);
            btn.setText(celda);
            btn.setBackgroundResource(R.drawable.rounded_grey_btn);
            btn.setTextColor(ContextCompat.getColor(context, R.color.grey));

            btn.setOnClickListener(v -> manejarBoton((Button) v));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.setMargins(20, 20, 20, 20);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            gridLayout.addView(btn, params);
        }
    }

    private void manejarBoton(Button btn) {
        int fallosActual = Integer.parseInt(fallos.getText().toString().trim());
        int aciertosActual = Integer.parseInt(aciertos.getText().toString().trim());

        if (btn.getText().equals("M")) {
            btn.setBackgroundResource(R.drawable.round_red_btn);
            aciertosActual++;
        } else {
            btn.setBackgroundResource(R.drawable.round_green_btn);
            fallosActual++;
        }

        btn.setTextColor(ContextCompat.getColor(context, R.color.black));
        btn.setOnClickListener(null);

        fallos.setText(String.valueOf(fallosActual));
        aciertos.setText(String.valueOf(aciertosActual));

        int fallosMaximos = numBotones / 2;

        if (fallosActual == fallosMaximos) {
            Toast.makeText(context, "Has perdido 😢", Toast.LENGTH_SHORT).show();
            reset();
            return;
        }

        if (aciertosActual == numMinas) {
            Toast.makeText(context, "¡Has ganado! 🎉", Toast.LENGTH_SHORT).show();
            reset();
        }
    }

    private void reset() {
        mostrado = false;
        gridLayout.removeAllViews();
        fallos.setText("0");
        aciertos.setText("0");
        inputPrincipal.setText("");
        inputPrincipal.setEnabled(true);
        mostrar.setChecked(false);
        mostrar.setEnabled(false);
    }

    public void comprobarMostrar() {
        if (mostrado) return;

        Drawable greyDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_grey_btn);
        if (greyDrawable == null) return;

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View childView = gridLayout.getChildAt(i);
            if (!(childView instanceof Button)) continue;

            Button btn = (Button) childView;
            if (Objects.equals(btn.getText(), "M") &&
                    btn.getBackground().getConstantState() != null &&
                    btn.getBackground().getConstantState().equals(greyDrawable.getConstantState())) {

                btn.setBackgroundResource(R.drawable.round_red_btn);
                btn.setTextColor(ContextCompat.getColor(context, R.color.white));
                btn.setOnClickListener(null);

                int aciertosActual = Integer.parseInt(aciertos.getText().toString().trim()) + 1;
                aciertos.setText(String.valueOf(aciertosActual));

                Toast.makeText(context, "Mina mostrada con éxito!", Toast.LENGTH_SHORT).show();

                mostrado = true;
                mostrar.setEnabled(false);

                if (aciertosActual == numMinas) {
                    Toast.makeText(context, "¡Has ganado con éxito!", Toast.LENGTH_SHORT).show();
                    reset();
                }

                break;
            }
        }
    }
}
