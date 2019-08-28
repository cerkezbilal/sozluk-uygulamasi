package com.bilal.karademir.sozlukuygulamasi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetayActivity extends AppCompatActivity {

    TextView textViewturkce, textViewingilizce;
    Kelimeler kelime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        textViewingilizce = findViewById(R.id.textViewingilizce);
        textViewturkce = findViewById(R.id.textViewturkce);

        kelime =(Kelimeler) getIntent().getSerializableExtra("nesne");

        textViewturkce.setText(kelime.getTurkce());
        textViewingilizce.setText(kelime.getIngilizce());


    }
}
