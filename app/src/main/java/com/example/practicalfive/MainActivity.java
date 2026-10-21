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

    // Key used for passing data to the Municipality Activity
    public static final String EXTRA_MUNICIPALITY = "extra_municipality";

    // Declare the input field UI component
    private EditText editMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the EditText component
        editMunicipality = findViewById(R.id.editMunicipality);

        // Set up an action listener to handle keyboard input (Enter/Go)
        editMunicipality.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Check if the action is Go, Done, or an Enter key press
                boolean isGo = actionId == EditorInfo.IME_ACTION_GO;
                boolean isDone = actionId == EditorInfo.IME_ACTION_DONE;
                boolean isEnter = event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN;

                // If an action is triggered, process the input and navigate
                if (isGo || isDone || isEnter) {
                    processAndNavigate();
                    return true;
                }
                return false;
            }
        });
    }

    // Method to validate input and navigate to the next screen
    private void processAndNavigate() {
        // Retrieve the text from the input field
        String input = editMunicipality.getText().toString();

        // Validate and normalize the input using the TextProcessor class
        String normalized = TextProcessor.processMunicipality(input);

        // Handle invalid input (empty, spaces, or unsupported names)
        if (normalized == null) {
            if (input.trim().isEmpty()) {
                // Show error for empty input
                editMunicipality.setError(getString(R.string.error_empty));
                Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show();
            } else {
                // Show error for unsupported input
                editMunicipality.setError(getString(R.string.error_invalid));
                Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_SHORT).show();
            }
            return; // Stay on the main screen
        }

        // If valid, create an explicit Intent to open the Municipality Activity
        Intent intent = new Intent(MainActivity.this, Municipality.class);

        // Pass the normalized municipality name as an extra
        intent.putExtra(EXTRA_MUNICIPALITY, normalized);

        // Start the second Activity
        startActivity(intent);
    }
}