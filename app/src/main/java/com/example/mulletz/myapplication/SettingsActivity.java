package com.example.mulletz.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    protected EditText setAge;
    protected EditText setHeight;
    protected EditText setWeight;
    protected Button saveSetting;
    protected RadioButton act1;
    protected RadioButton act2;
    protected RadioButton act3;
    protected RadioButton act4;
    protected RadioButton act5;
    protected String age ;
    protected String height;
    protected String weight;
    protected String act ;
    protected int ACT_ID = 0;
    protected String ACT_VOLUME ="";

    Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setAge = (EditText)findViewById(R.id.editText_setting_age);
        setHeight = (EditText)findViewById(R.id.editText_setting_height);
        setWeight = (EditText)findViewById(R.id.editText_setting_weight);
        saveSetting = (Button)findViewById(R.id.button_setting_save);
        act1 = (RadioButton)findViewById(R.id.radioButton1);
        act2 = (RadioButton)findViewById(R.id.radioButton2);
        act3 = (RadioButton)findViewById(R.id.radioButton3);
        act4 = (RadioButton)findViewById(R.id.radioButton4);
        act5 = (RadioButton)findViewById(R.id.radioButton5);

        sqllite = new Database(this);

        showHistory(sqllite);

        saveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setSetting(sqllite);
                    finish();
            }
        });
    }
    private void showHistory(Database sqllite){
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                age = c.getString(4);
                height = c.getString(6);
                weight = c.getString(7);
                act = c.getString(9);
            }
        }
        if (act == null){
        }
        else if (act.equals("1")){
            act1.setChecked(true);
        }
        else if (act.equals("2")){
            act2.setChecked(true);
        }
        else if (act.equals("3")){
            act3.setChecked(true);
        }
        else if (act.equals("4")){
            act4.setChecked(true);
        }
        else if (act.equals("5")){
            act5.setChecked(true);
        }
        setAge.setText(age);
        setHeight.setText(height);
        setWeight.setText(weight);
    }
    private void setSetting(Database sqllite){
        age = setAge.getText().toString().trim();
        weight = setWeight.getText().toString().trim();
        height = setHeight.getText().toString().trim();

        if (act1.isChecked()){
            ACT_ID = 1;
            ACT_VOLUME = "1.2";
        }
        else if (act2.isChecked()){
            ACT_ID = 2;
            ACT_VOLUME = "1.375";
        }
        else if (act3.isChecked()){
            ACT_ID = 3;
            ACT_VOLUME = "1.55";
        }
        else if (act4.isChecked()){
            ACT_ID = 4;
            ACT_VOLUME = "1.725";
        }
        else if (act5.isChecked()){
            ACT_ID = 5;
            ACT_VOLUME = "1.90";
        }
        sqllite.updateUserSetting(age,height,weight,ACT_ID,ACT_VOLUME);
    }
}
