package com.example.android.quizapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void grade(View view) {
        try {
            String answer1 = ((EditText) findViewById(R.id.mostUsedOS)).getText().toString();
            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
            String answer2 = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
            CheckBox invalidCheckBox1 = (CheckBox) findViewById(R.id.invalid1);
            CheckBox validCheckBox1 = (CheckBox) findViewById(R.id.valid1);
            CheckBox invalidCheckBox2 = (CheckBox) findViewById(R.id.invalid2);
            CheckBox invalidCheckBox3 = (CheckBox) findViewById(R.id.invalid3);
            CheckBox validCheckBox2 = (CheckBox) findViewById(R.id.valid2);
            String answer4 = ((EditText) findViewById(R.id.recommendedIde)).getText().toString();
            String answer5 = ((EditText) findViewById(R.id.layoutLanguage)).getText().toString();

            int score = 0;
            int total = 5;

            if (answer1.toLowerCase().contains("android")) ++score;
            if (answer2.equals("ImageView")) ++score;
            if (validCheckBox1.isChecked() && validCheckBox2.isChecked() &&
                    !(
                            invalidCheckBox1.isChecked() ||
                            invalidCheckBox2.isChecked() ||
                            invalidCheckBox3.isChecked()
                    ))
                ++score;
            if (answer4.toLowerCase().contains("android studio")) ++score;
            if (answer5.toLowerCase().contains("xml")) ++score;

            int percentage = (int) ((100.0 * score)/total);
            Toast.makeText(this, percentage + " %", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Please fill all the values!", Toast.LENGTH_SHORT).show();
        }
    }
}
