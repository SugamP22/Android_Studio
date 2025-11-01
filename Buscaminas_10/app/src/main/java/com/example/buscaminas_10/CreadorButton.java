package com.example.buscaminas_10;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class CreadorButton {
    private final Context view;
    private final GridLayout gridLayout;

    public CreadorButton(Context view, GridLayout gridlayout) {
        this.view = view;
        this.gridLayout = gridlayout;
    }

    public void crearBtn(int num, View.OnClickListener listener, TextView fallos, TextView aciertos) {
        int totalMinesToLose = num / 4;
        int totalSafeToWin = num / 2;

        for (int i = 0; i < num; i++) {
            Button btn = new Button(view);
            btn.setId(View.generateViewId());
            btn.setText(Math.random() < 0.5 ? "M" : "S");

            try {
                btn.setBackground(ContextCompat.getDrawable(view, R.drawable.white_rounded));
            } catch (Exception e) {
                btn.setBackgroundColor(0xFFCCCCCC);
            }
            btn.setTextColor(ContextCompat.getColor(view, R.color.white));

            btn.setOnClickListener(v -> handleButtonClick((Button) v, listener, fallos, aciertos, totalMinesToLose, totalSafeToWin));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 200;
            params.height = 200;
            params.setMargins(10, 10, 10, 10);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            gridLayout.addView(btn, params);
        }

        Toast.makeText(view, "Todos los botones añadidos con éxito!!", Toast.LENGTH_SHORT).show();
    }

    private void handleButtonClick(Button btnPulsado, View.OnClickListener listener, TextView fallos, TextView aciertos, int totalMinesToLose, int totalSafeToWin) {
        String valorBtn = btnPulsado.getText().toString().trim();
        btnPulsado.setEnabled(false);

        if (valorBtn.equalsIgnoreCase("M")) {
            try {
                btnPulsado.setBackground(ContextCompat.getDrawable(view, R.drawable.red_button));
            } catch (Exception e) {
                btnPulsado.setBackgroundColor(0xFFFF0000);
            }

            int currentFallos = updateCounter(fallos);
            if (currentFallos >= totalMinesToLose) resetGame("Ha perdido!", fallos, aciertos);
        } else {
            try {
                btnPulsado.setBackground(ContextCompat.getDrawable(view, R.drawable.green_button));
            } catch (Exception e) {
                btnPulsado.setBackgroundColor(0xFF00FF00);
            }

            int currentAciertos = updateCounter(aciertos);
            if (currentAciertos >= totalSafeToWin) resetGame("Ha ganado!", fallos, aciertos);
        }

        if (listener != null) listener.onClick(btnPulsado);
    }

    private int updateCounter(TextView counter) {
        String text = counter.getText().toString().trim();
        String[] parts = text.split(":");
        int number = 1;
        if (parts.length > 1) {
            try {
                number = Integer.parseInt(parts[1].trim()) + 1;
            } catch (NumberFormatException e) {
                number = 1;
            }
        }
        counter.setText(parts[0] + ": " + number);
        return number;
    }

    private void resetGame(String message, TextView fallos, TextView aciertos) {
        Toast.makeText(view, message, Toast.LENGTH_SHORT).show();
        gridLayout.removeAllViews();
        fallos.setText("Fallos: 0");
        aciertos.setText("Aciertos: 0");
    }
}
