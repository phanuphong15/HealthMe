package com.example.mulletz.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Database sqllite = new Database(this);


       checkInsert(sqllite);
        waitScreen();
    }

    private void checkInsert(Database sqllite) {
        Cursor c = sqllite.select("Typefood");
        if (c.getCount() == 0) {
            sqllite.insertTypefood();
            sqllite.insertActivity();
            sqllite.insertFood();
        }
    }

    private void waitScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                finish();
                // System.exit(0);
            }
        }, 2500); // Millisecond 1000 = 1 sec
    }
}
