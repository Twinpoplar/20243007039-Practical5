package com.example.practicalfive;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Municipality extends AppCompatActivity {

    // Store the validated municipality name
    private String currentMunicipality;

    // Declare UI components
    private TextView textMunicipalityName;
    private ImageView imageMunicipality;
    private TextView textMunicipalityInfo;
    private Button buttonVisitSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        // 1. Retrieve the raw data passed from MainActivity
        String rawData = getIntent().getStringExtra(MainActivity.EXTRA_MUNICIPALITY);

        // 2. Validate and normalize the data
        currentMunicipality = TextProcessor.processMunicipality(rawData);

        // 3. Safety check: handle missing or invalid data safely
        if (currentMunicipality == null) {
            Toast.makeText(this, R.string.error_missing_data, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 4. Initialize UI components using findViewById()
        textMunicipalityName = findViewById(R.id.textMunicipalityName);
        imageMunicipality = findViewById(R.id.imageMunicipality);
        textMunicipalityInfo = findViewById(R.id.textMunicipalityInfo);
        buttonVisitSite = findViewById(R.id.buttonVisitSite);

        // 5. Customize the UI based on the validated municipality name
        customizeUI();
    }

    private void customizeUI() {
        int themeColorRes;
        int imageRes;
        int arrayIndex;

        // Determine resources based on the municipality
        switch (currentMunicipality) {
            case "Beijing":
                themeColorRes = R.color.municipality_Beijing;
                imageRes = R.drawable.beijing;
                arrayIndex = 0; // Beijing is the 1st item in the array
                break;
            case "Shanghai":
                themeColorRes = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                arrayIndex = 1; // Shanghai is the 2nd item in the array
                break;
            case "Chongqing":
                themeColorRes = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                arrayIndex = 2; // Chongqing is the 3rd item in the array
                break;
            case "Tianjin":
                themeColorRes = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                arrayIndex = 3; // Tianjin is the 4th item in the array
                break;
            default:
                return; // Should not happen, but just in case
        }

        // Get the actual color value from the resource ID
        int themeColor = ContextCompat.getColor(this, themeColorRes);
        int lightTextColor = ContextCompat.getColor(this, R.color.color_text_light);

        // Set Name TextView: text, background color, and text color
        textMunicipalityName.setText(currentMunicipality);
        textMunicipalityName.setBackgroundColor(themeColor);
        textMunicipalityName.setTextColor(lightTextColor);

        // Set ImageView resource
        imageMunicipality.setImageResource(imageRes);

        // Set Information TextView: text from array, text color
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);
        textMunicipalityInfo.setText(summaries[arrayIndex]);
        textMunicipalityInfo.setTextColor(themeColor);

        // Set Visit site Button background color using ColorStateList
        buttonVisitSite.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        buttonVisitSite.setTextColor(lightTextColor);
    }
}