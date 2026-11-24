
package com.example.gra;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvLicznik, tvKomunikat, tvTrafienia;
    Button btnStart, btnStop;

    Handler handler = new Handler(Looper.getMainLooper());

    int liczba = 0;
    int trafienia = 0;

    boolean czyDziala = false;

    Runnable zmianaLiczby = new Runnable() {
        @Override
        public void run() {

            liczba++;

            if (liczba > 9) {
                liczba = 0;
            }

            tvLicznik.setText(String.valueOf(liczba));

            handler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        tvLicznik = findViewById(R.id.tvLicznik);
        tvKomunikat = findViewById(R.id.tvKomunikat);
        tvTrafienia = findViewById(R.id.tvTrafienia);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v -> {

            if (!czyDziala) {

                liczba = 0;

                tvLicznik.setText("0");
                tvKomunikat.setText("");

                czyDziala = true;

                btnStart.setEnabled(false);
                btnStop.setEnabled(true);

                handler.postDelayed(zmianaLiczby, 500);
            }
        });
        
        btnStop.setOnClickListener(v -> {

            if (czyDziala) {

                handler.removeCallbacks(zmianaLiczby);

                czyDziala = false;

                btnStart.setEnabled(true);
                btnStop.setEnabled(false);

                if (liczba == 7) {

                    trafienia++;

                    tvKomunikat.setText("Trafienie!");

                } else {

                    tvKomunikat.setText(
                            "Pudło! Zatrzymano na " + liczba
                    );
                }

                tvTrafienia.setText("Trafienia: " + trafienia);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(zmianaLiczby);
    }
}
