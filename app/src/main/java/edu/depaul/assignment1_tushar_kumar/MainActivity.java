package edu.depaul.assignment1_tushar_kumar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText milesInput, kilometerInput;
    private TextView historyTextview;
    private RadioButton miki, kimi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miki = findViewById(R.id.miKiRadioButton);
        kimi = findViewById(R.id.kiMiRadiButton);
        milesInput = findViewById(R.id.milesInput);
        kilometerInput = findViewById(R.id.kilometerInput);
        historyTextview = findViewById(R.id.historyTextview);

        historyTextview.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("History", historyTextview.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        historyTextview.setText(savedInstanceState.getString("History"));
    }

    public void radioOnClick(View v){
        switch(v.getId()){
            case R.id.miKiRadioButton:
                milesInput.setText("");
                break;
            case R.id.kiMiRadiButton:
                kilometerInput.setText("");
                break;
        }
    }

    public void convertClicked(View v){
        double result;
        if(miki.isChecked()){
            if(milesInput.getText().length()!=0){
                double milesValue = Double.parseDouble(milesInput.getText().toString());
                result = milesToKilometer(milesValue);
                kilometerInput.setText(String.format(Locale.getDefault(), "%,.1f", result));
                setHistory("Mi", "Km", milesValue, result);
                milesInput.setText("");
            }
        }

        if(kimi.isChecked()){
            String ki = kilometerInput.getText().toString();
            if(!ki.trim().isEmpty()){
                double kilometerValue = Double.parseDouble(kilometerInput.getText().toString());
                result = kilometerToMiles(kilometerValue);
                milesInput.setText(String.format(Locale.getDefault(), "%,.1f", result));
                setHistory("Km", "Mi", kilometerValue, result);
                kilometerInput.setText("");
            }
        }
    }

    public void setHistory(String a, String b, double value, double answer){
        String oldHistoryText = historyTextview.getText().toString();
        historyTextview.setText(String.format(Locale.getDefault(),"%.1f %s ==> %.1f %s\n%s", value, a, answer, b, oldHistoryText));
    }

    public double milesToKilometer(double value){
        return value*1.60934;
    }

    public double kilometerToMiles(double value){
        return value*0.621371;
    }

    public void clearClicked(View v){
        historyTextview.setText("");
    }
}