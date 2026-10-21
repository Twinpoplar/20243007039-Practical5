package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MUNICIPALITY = "extra_municipality";

    private EditText editMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMunicipality = findViewById(R.id.editMunicipality);

        editMunicipality.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isGo = actionId == EditorInfo.IME_ACTION_GO;
                boolean isDone = actionId == EditorInfo.IME_ACTION_DONE;
                boolean isEnter = event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN;

                if (isGo || isDone || isEnter) {
                    processAndNavigate();
                    return true;
                }
                return false;
            }
        });
    }

    private void processAndNavigate() {
        String input = editMunicipality.getText().toString();

        String normalized = TextProcessor.processMunicipality(input);

        if (normalized == null) {
            if (input.trim().isEmpty()) {
                editMunicipality.setError(getString(R.string.error_empty));
                Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show();
            } else {
                editMunicipality.setError(getString(R.string.error_invalid));
                Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_SHORT).show();
            }
            return;
        }

        Intent intent = new Intent(MainActivity.this, Municipality.class);

        intent.putExtra(EXTRA_MUNICIPALITY, normalized);

        startActivity(intent);
    }
}