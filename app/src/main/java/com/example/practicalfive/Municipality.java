package com.example.practicalfive;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Municipality extends AppCompatActivity {

    private String currentMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        String rawData = getIntent().getStringExtra(MainActivity.EXTRA_MUNICIPALITY);

        currentMunicipality = TextProcessor.processMunicipality(rawData);

        if (currentMunicipality == null) {
            Toast.makeText(this, R.string.error_missing_data, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
}