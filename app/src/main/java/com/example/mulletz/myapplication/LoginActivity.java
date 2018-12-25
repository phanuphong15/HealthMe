package com.example.mulletz.myapplication;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    protected Button submit ;
    protected Button register ;
    protected EditText editUser;
    protected EditText editPswd;

    protected String username;
    protected String password;

    protected boolean verifyUsername = false;
    protected boolean verifyPassword = false;

    Database sqllite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqllite = new Database(this);

        editUser = (EditText)findViewById(R.id.editText_login_username);
        editPswd = (EditText)findViewById(R.id.editText_login_password);
        submit = (Button)findViewById(R.id.button_login_submit);
        register = (Button)findViewById(R.id.button_goRegister);

        continueLogin(sqllite);

        editUser.setFocusable(false);
        editUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editUser.setFocusableInTouchMode(true);
                return false;
            }
        });
        editPswd.setFocusable(false);
        editPswd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editPswd.setFocusableInTouchMode(true);
                return false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               checkLogin(sqllite);
                if(verifyUsername == true && verifyPassword == true){
                    sqllite.updateLogout();
                    sqllite.updateLogin(username,"Y");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    verifyUsername = false;
                    verifyPassword = false;
                    editPswd.getText().clear();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });
    }
    private void goRegister(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
    private void checkLogin(Database sqllite) {
        username = editUser.getText().toString().trim();
        password = editPswd.getText().toString().trim();

        Cursor c = sqllite.select("User");
        while(c.moveToNext()){
            if(c.getString(1).equals(username)){
                verifyUsername = true;
                if(c.getString(2).equals(password)){
                    verifyPassword = true;
                }
            }
        }
    }
    private void continueLogin(Database sqllite){
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}