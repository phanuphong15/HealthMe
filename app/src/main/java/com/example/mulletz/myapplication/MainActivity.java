package com.example.mulletz.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    protected Button setting;
    protected Button logOut;
    protected Button calBMI;
    protected Button calBMR;
    protected Button food;
    protected Button detail;
    protected Button spin;
    protected TextView showBMI;
    protected TextView showDesBMI;
    protected TextView showBMR;
    protected TextView showTDEE;
    protected TextView testtime;
    protected TextView showCalorie;
    protected TextView showUsername;
    protected String username;

    private final Handler handler = new Handler();
    private int test = 0;

    protected float height = -1;
    protected float weight = -1;
    protected float bmi = -1;

    protected double bmr ;
    protected double tdee = 0;
    protected double bmr_height = 0;
    protected double bmr_weight = 0;
    protected double bmr_act = 0;
    protected double bmr_age = 0;

    protected String bmr_sex;
    protected String date;
    protected SimpleDateFormat sdf;

    protected Database sqllite;
    protected Cursor sum_c;
    int primary_user = 0;

    float convert_bmr = 0;
    int sum_bmr = 0;
    String temp_bmr = "";
    int sum_calorie = 0;
    int temp_calorie = 0;
    int temp_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqllite = new Database(this);
        showBMI = (TextView)findViewById(R.id.textView_main_showbmi);
        showDesBMI = (TextView)findViewById(R.id.textView_main_desbmi);
        showBMR = (TextView)findViewById(R.id.textView_main_showbmr);
        showTDEE = (TextView)findViewById(R.id.textView_main_showtdee);
        testtime = (TextView)findViewById(R.id.textView_testtime);
        showCalorie = (TextView)findViewById(R.id.textView_main_showcalorie);
        showUsername = (TextView)findViewById(R.id.textView_showusername);

        sdf = new SimpleDateFormat("dd-MM-yyyy");

        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                primary_user = Integer.parseInt(c.getString(0));
                username = c.getString(1).trim();
                temp_count = 0;
            }
        }

        showUsername.setText(username);
        doTheAutoRefresh();

        setting = (Button)findViewById(R.id.button_main_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });
        logOut = (Button)findViewById(R.id.button_main_logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("แจ้งเตือน");
                alert.setMessage("คุณต้องการที่จะออกจากระบบ ?");
                alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor c = sqllite.select("User");
                        while(c.moveToNext()){
                            if (c.getString(5).equals("Y")){
                                username = c.getString(1);
                            }
                        }
                        sqllite.updateLogout();
                        finish();
                    }
                });

                alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
        detail = (Button)findViewById(R.id.button_main_detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DetailActivity.class));
            }
        });
        calBMI = (Button)findViewById(R.id.button_main_bmi);
        calBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BMIActivity.class));
            }
        });
        calBMR = (Button)findViewById(R.id.button_main_bmr);
        calBMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BMRActivity.class));
            }
        });
        food = (Button)findViewById(R.id.button_main_food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FoodActivity.class));
            }
        });
        spin = (Button)findViewById(R.id.button_slot);
        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SlotActivity.class));
            }
        });

    }
    public void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkDate(sqllite,sdf);
                showHistory();
                sumCalorie();
                doTheAutoRefresh();

            }
        }, 1000);
    }
    public void showHistory(){
        float hisbmi = 0;
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (username.equals(c.getString(1))){
                if (c.getString(10) != null){
                  hisbmi = c.getFloat(10);
                }
            }
        }
        if (hisbmi == 0.0){
            showBMI.setText("");
        }
        else {
            showBMI.setText(String.valueOf(hisbmi));
        }
             if (hisbmi < 18.5 && hisbmi != 0) {
                showDesBMI.setText("น้ำหนักน้อยเกินไป");
            } else if (hisbmi >= 18.5 && hisbmi <= 23.4) {
                showDesBMI.setText("น้ำหนักปกติ");
            } else if (hisbmi > 23.4 && hisbmi <= 28.4) {
                showDesBMI.setText("น้ำหนักเกิน");
            } else if (hisbmi > 28.4 && hisbmi <= 34.9) {
                showDesBMI.setText("โรคอ้วนขั้นที่ 1");
            } else if (hisbmi > 35.0) {
                showDesBMI.setText("โรคอ้วนขั้นที่ 2");
            }
        float bmrHis = 0 ;
        float tdeeHis = 0;
        float actHis = 0;
        c = sqllite.select("User");
        while (c.moveToNext()){
            if (username.equals(c.getString(1))){
                if (c.getString(11) != null ){
                    bmrHis = c.getFloat(11);
                    actHis = c.getFloat(8);
                    tdeeHis = bmrHis*actHis;
                    showBMR.setText(String.valueOf(bmrHis));
                    showTDEE.setText(String.valueOf(tdeeHis));
                }
                else
                    break;
            }
        }
    }
    public void sumCalorie(){
        sum_calorie = 0;
        sum_c = sqllite.select("User");
        while (sum_c.moveToNext()){
            if (username.equals(sum_c.getString(1))){
                sum_bmr = sum_c.getInt(11);
            }
        }
        sum_c = sqllite.selectEat(date,primary_user);
        temp_count = sum_c.getCount();
           while (sum_c.moveToNext()){
               for (int i = 1 ; i <= temp_count ; i++)
                temp_count = sum_c.getCount();
                temp_calorie = sum_c.getInt(6);
                sum_calorie = sum_calorie + temp_calorie;
            }
        showCalorie.setText(String.valueOf(sum_calorie)+"/"+String.valueOf(sum_bmr));
    }
    public void checkDate(Database sqllite,SimpleDateFormat sdf){
        date = sdf.format(new Date());
        testtime.setText(date);
        String old_date = "";
        Cursor c = sqllite.select("Date");
        if (c.getCount() == 0){
            sqllite.insertDate(date);
        }
        else if (c.getCount() > 0){
            while (c.moveToNext()){
                if (c.isLast()){
                    old_date = c.getString(0);
                }
            }
            if (!old_date.equals(date)){
                //edit
                sqllite.insertDate(date);

            }
        }
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("แจ้งเตือน");
        alert.setMessage("คุณต้องการที่จะออกจากระบบ ?");
        alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor c = sqllite.select("User");
                while(c.moveToNext()){
                    if (c.getString(5).equals("Y")){
                        username = c.getString(1);
                    }
                }
                sqllite.updateLogout();
                finish();
            }
        });
        alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}



