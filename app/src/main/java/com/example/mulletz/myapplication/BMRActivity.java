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

public class BMRActivity extends AppCompatActivity {
    protected Button calBMR;
    protected TextView showBMR;
    protected TextView showTDEE;

    protected double bmr ;
    protected double tdee = 0;
    protected double bmr_height = 0;
    protected double bmr_weight = 0;
    protected double bmr_act = 0;
    protected double bmr_age = 0;

    protected String bmr_sex;

    Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr);

        sqllite = new Database(this);

        showBMR = (TextView)findViewById(R.id.textView_showBMR);
        showTDEE = (TextView)findViewById(R.id.textView_showTDEE);

        showHistory(sqllite);

        calBMR = (Button)findViewById(R.id.button_calbmr);
        calBMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calBMR(sqllite);
            }
        });
    }
    private void showHistory(Database sqllite){
        double bmrHis ;
        double tdeeHis ;
        double actHis ;
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                if (c.getString(11) != null ){
                    bmrHis = Double.parseDouble(c.getString(11).trim());
                    actHis = Double.parseDouble(c.getString(8).trim());
                    tdeeHis = bmrHis*actHis;
                    showBMR.setText(String.valueOf(bmrHis));
                    showTDEE.setText(String.valueOf(tdeeHis));
                }
                else
                    break;
            }
        }
    }
    private void calBMR(Database sqllite){
        Cursor c = sqllite.select("User");
        while (c.moveToNext()) {
            if (c.getString(5).equals("Y")) {
                if (c.getString(6) == null || c.getString(7) == null || c.getString(6).equals("") || c.getString(7).equals("") || c.getString(8) == null || c.getString(8).equals("") || c.getString(9) == null || c.getString(9).equals("") || c.getString(4).equals("")|| c.getString(6).equals("0") || c.getString(7).equals("0")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(BMRActivity.this);
                    alert.setTitle("แจ้งเตือน");
                    alert.setMessage("ไปกรอกข้อมูลก่อนเพิ่มเติมก่อนน้าาา");
                    alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(BMRActivity.this, SettingsActivity.class));
                        }
                    });
                    alert.show();

                } else {
                    bmr_height = Double.parseDouble(c.getString(6).trim());
                    bmr_weight = Double.parseDouble(c.getString(7).trim());
                    bmr_sex = c.getString(3);
                    bmr_act = Double.parseDouble(c.getString(8).trim());
                    bmr_age = Double.parseDouble(c.getString(4).trim());
                    if (bmr_sex.equals("M")) {
                        bmr = (10 * bmr_weight) + (6.25 * bmr_height) + (5 * bmr_age)+5;
                    } else if (bmr_sex.equals("F")) {
                        bmr = (10 * bmr_weight) + (6.25 * bmr_height) + (5 * bmr_age)-161;
                    }
                    tdee = bmr * bmr_act;
                    showBMR.setText(String.valueOf(bmr));
                    showTDEE.setText(String.valueOf(tdee));
                    sqllite.updateUserBMR(String.valueOf(bmr));
                }
            }
        }

    }
}
