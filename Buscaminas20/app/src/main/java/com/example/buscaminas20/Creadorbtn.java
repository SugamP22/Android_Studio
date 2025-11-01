package com.example.buscaminas20;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Creadorbtn {
    private final Context view;
    private final GridLayout gridLayout;
    private boolean minaMostrado = false;


    Creadorbtn(Context view, GridLayout grilayout) {
        this.view = view;
        gridLayout = grilayout;
    }

    public void empiezarACrear(int num, TextView fallos, TextView aciertos, CheckBox mostrar, View.OnClickListener listener) {
        this.minaMostrado = false;
        mostrar.setChecked(false);
        mostrar.setEnabled(true);
        int numMinas = num / 4;
        int numNoMinas = num - numMinas;
        String[] minas = new String[numMinas];
        String[] normal = new String[numNoMinas];
        darValor(minas, "M");
        darValor(normal, "N/A");
        ArrayList<String> letras = new ArrayList<>();
        Collections.addAll(letras, minas);
        Collections.addAll(letras, normal);
        Collections.shuffle(letras);
        for (String s : letras) {
            Button btn = new Button(view);
            btn.setText(s);
            btn.setAllCaps(false);

            btn.setBackground(ContextCompat.getDrawable(view, R.drawable.grey_rounded));
            btn.setTextColor(ContextCompat.getColor(view, R.color.grey));

            btn.setOnClickListener(v -> darFuncionalidad((Button) v, numMinas, numNoMinas, fallos, aciertos, mostrar, listener));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 200;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(10, 10, 10, 10);
            gridLayout.addView(btn, params);

        }
        Toast.makeText(view, "Botones creado con exitó", Toast.LENGTH_SHORT).show();


    }

    private void darValor(String[] array, String m) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = m;
            }
        }
    }

    private void darFuncionalidad(Button btn, int numMinas, int numNoMinas, TextView fallos, TextView aciertos, CheckBox mostrar, View.OnClickListener listener) {
        int numFallos = Integer.parseInt(fallos.getText().toString());
        int numAciertos = Integer.parseInt(aciertos.getText().toString());
        if (btn.getText().equals("M")) {
            btn.setBackgroundResource(R.drawable.mina_roja);
            numFallos++;
            fallos.setText(String.valueOf(numFallos));
        } else {
            btn.setBackgroundResource(R.drawable.circular_verde);
            numAciertos++;
            aciertos.setText(String.valueOf(numAciertos));
        }
        if (numAciertos == numNoMinas) {
            Toast.makeText(view, "Has Gandao!!", Toast.LENGTH_SHORT).show();
            resetJuego(fallos, aciertos, mostrar);

        }
        if (numFallos == numMinas) {
            Toast.makeText(view, "Has Perdido!!", Toast.LENGTH_SHORT).show();
            resetJuego(fallos, aciertos, mostrar);
        }


        if (listener != null) {
            listener.onClick(btn);
        }


    }

    private void resetJuego(TextView fallos, TextView aciertos, CheckBox mostrar) {
        gridLayout.postDelayed(() -> {
            gridLayout.removeAllViews();
            fallos.setText("0");
            aciertos.setText("0");
            mostrar.setChecked(false);
            mostrar.setEnabled(true);
            minaMostrado = false;
        }, 1000);
    }

    public void mostrarMina(CheckBox mostrar) {
        if (minaMostrado) {
            Toast.makeText(view, "Error: Mina ya monstrado", Toast.LENGTH_SHORT).show();
            mostrar.setChecked(false);
            return;

        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof Button) {
                Button btn = (Button) child;
                if (btn.getText().toString().trim().equalsIgnoreCase("M") && Objects.equals(btn.getBackground().getConstantState(), Objects.requireNonNull(ContextCompat.getDrawable(view, R.drawable.grey_rounded)).getConstantState())) {
                    btn.setBackgroundResource(R.drawable.mina_roja);
                    btn.setOnClickListener(null);
                    Toast.makeText(view, "Mina monstrado con exitó", Toast.LENGTH_SHORT).show();
                    minaMostrado = true;
                    mostrar.setChecked(true);
                    mostrar.setEnabled(false);
                    return;
                }

            }
        }
        Toast.makeText(view, "Error: Falta Mina para mostrar", Toast.LENGTH_SHORT).show();
        mostrar.setChecked(false);

    }
}
