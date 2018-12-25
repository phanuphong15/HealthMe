package com.example.mulletz.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {
    protected TextView showBMI;
    protected Button calBMI;
    protected TextView showDesBMI;
    protected float height = -1;
    protected float weight = -1;
    protected float bmi = -1;

    Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        sqllite = new Database(this);
        showBMI = (TextView)findViewById(R.id.textView_bmi);
        showDesBMI = (TextView)findViewById(R.id.textView_showBMI);

         showHistory(sqllite);


        calBMI = (Button)findViewById(R.id.button_calbmi);
        calBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calBMI(sqllite);
                checkBMI(sqllite);
            }
        });
    }
    private void showHistory(Database sqllite){
        String str ="";
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                if (c.getString(10) != null){
                    str = c.getString(10);
                }
            }
        }
        showBMI.setText(str);
        if (!str.equals("")){
            bmi = Float.parseFloat(str);

            if (bmi < 18.5){
                showDesBMI.setText("น้ำหนักน้อยเกินไป");
            }
            else if (bmi >= 18.5 && bmi <= 23.4){
                showDesBMI.setText("น้ำหนักปกติ");
            }
            else if (bmi > 23.4 && bmi <= 28.4){
                showDesBMI.setText("น้ำหนักเกิน");
            }
            else if (bmi > 28.4 && bmi <= 34.9){
                showDesBMI.setText("โรคอ้วนขั้นที่ 1");
            }
            else if (bmi > 35.0){
                showDesBMI.setText("โรคอ้วนขั้นที่ 2");
            }
        }
    }
    private void calBMI(Database sqllite){
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")) {
                if (c.getString(6) == null || c.getString(7) == null || c.getString(6).equals("") || c.getString(7).equals("") || c.getString(6).equals("0") || c.getString(7).equals("0")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(BMIActivity.this);
                    alert.setTitle("แจ้งเตือน");
                    alert.setMessage("ไปกรอกข้อมูลก่อนเพิ่มเติมก่อนน้าาา");
                    alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(BMIActivity.this, SettingsActivity.class));
                        }
                    });
                    alert.show();
                } else {
                    height = Float.parseFloat(c.getString(6));
                    weight = Float.parseFloat(c.getString(7));
                    height = height/100;
                    bmi = weight/(height*height);
                    showBMI.setText(String.valueOf(bmi));
                }
            }
        }
        if (bmi != -1){
            sqllite.updateUserBMI(String.valueOf(bmi));
        }

    }
    private void checkBMI(Database sqllite){
        if (height != -1 || weight != -1){
            if (bmi < 18.5){
                showDesBMI.setText("น้ำหนักน้อยเกินไป");
            }
            else if (bmi >= 18.5 && bmi <= 23.4){
                showDesBMI.setText("น้ำหนักปกติ");
            }
            else if (bmi > 23.4 && bmi <= 28.4){
                showDesBMI.setText("น้ำหนักเกิน");
            }
            else if (bmi > 28.4 && bmi <= 34.9){
                showDesBMI.setText("โรคอ้วนขั้นที่ 1");
            }
            else if (bmi > 35.0){
                showDesBMI.setText("โรคอ้วนขั้นที่ 2");
            }
        }
    }
}
