package com.example.mulletz.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity{
    protected Button compute ;
    protected Spinner spinnerAge;
    protected ArrayAdapter<CharSequence> listAge;
    protected EditText editUser;
    protected EditText editPswd;
    protected EditText editPswd2nd;
    protected RadioButton radioMale;
    protected RadioButton radioFemale;

    protected int age = 0;
    protected int countUsercheck;
    protected int countPassword;

    protected String username;
    protected String password;
    protected String password2nd;
    protected String sex = "";
    protected String stringVerify;

    protected boolean verifyUsername = false;
    protected boolean verifyPassword = false;
    protected boolean verifyAge = false;
    protected boolean verifySex = false;

    protected char[] charUsername ;
    protected char[] charPassword;
    protected char[] charPassword2nd;
    protected char[] charVerify;

    protected String ageConvert ; //delete
    protected String ageConvert2 ; //delete
    protected TextView testUsername; //delete
    protected TextView testPassword;
    protected TextView testAge;
    protected TextView testSex;

    protected String INTSERT_DATABASE_USER;
    protected int PRIMARY_USER = 1;
    protected String CHECK_DATABASE_USER;
    protected String SHOW_SUCCESS;

    Database sqllite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        testSex = (TextView)findViewById(R.id.textView_testsex);
        testAge = (TextView)findViewById(R.id.textView_testage);
        testUsername = (TextView)findViewById(R.id.textView_testusername);
        testPassword = (TextView)findViewById(R.id.textView_testpswd);
        editUser = (EditText) findViewById(R.id.editText_username);
        editPswd = (EditText) findViewById(R.id.editText_password);
        editPswd2nd = (EditText) findViewById(R.id.editText_password2nd);
        spinnerAge = (Spinner) findViewById(R.id.spinner_age);
        listAge = ArrayAdapter.createFromResource(this, R.array.list_age, android.R.layout.simple_spinner_item);
        listAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(listAge);
        ageConvert = spinnerAge.getItemAtPosition(0).toString();
        radioMale = (RadioButton) findViewById(R.id.radioButton_male);
        radioFemale = (RadioButton) findViewById(R.id.radioButton_female);
        compute = (Button)findViewById(R.id.button_compute);

        sqllite = new Database(this);



        register();

    }
    private void register(){


        setInformation();
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInformation();
                verifyInformation(sqllite);
                if (verifyUsername == true && verifyPassword == true && verifyAge == true && verifySex == true){
                        Cursor c = sqllite.select("User");
                        String PRIMARY_USER = "" ;
                        int CONVERT_PRIMARY = 1;
                        while (c.moveToNext()){
                            if (c.getCount() > 0){
                                if (c.isLast()){
                                    PRIMARY_USER = c.getString(0);
                                    CONVERT_PRIMARY = Integer.parseInt(PRIMARY_USER);
                                    CONVERT_PRIMARY++;
                                }
                            }
                        }
                        sqllite.insertUserTest(username,password,sex,age);
                        String str = "register success!";
                        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void setInformation(){
        username = editUser.getText().toString().trim();
        password = editPswd.getText().toString().trim();
        password2nd = editPswd2nd.getText().toString().trim();
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ageConvert = spinnerAge.getItemAtPosition(position).toString();
                if(!ageConvert.equals("อายุ")){
                    age = Integer.parseInt(ageConvert);
                }
                else
                    age = 0;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (radioMale.isChecked()) {
            sex = "M";
        } else if (radioFemale.isChecked()) {
            sex = "F";
        }
    }
    private void verifyInformation(Database sqllite){
        stringVerify = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        countUsercheck = 0;
        countPassword = 0;

        verifyUsername = false;
        verifyPassword = false;
        verifyAge = false;
        verifySex = false;

        charVerify = stringVerify.toCharArray();
        charUsername = username.toCharArray();
        charPassword = password.toCharArray();

        /*check username*/
        if (username.length()>=4&&username.length()<20){
                for(int i = 0; i < username.length(); i++){
                    for (int j = 0;j < stringVerify.length();j++){
                    if (charUsername[i] == charVerify[j]){
                        countUsercheck++;
                    }
            }
            }
            if (countUsercheck == username.length()){
                        int countCheck = 0;
                        Cursor c = sqllite.select("User");
                        while(c.moveToNext()){
                            if(c.getString(1).equals(username)){
                                countCheck++;
                            }
                        }
                        if(countCheck > 0 ){
                            testUsername.setText("username ซ้ำ");
                        }
                        else {
                            testUsername.setText("correct");
                            verifyUsername = true;
                        }

            }
            else
                testUsername.setText("incorrect name.char");
        }
        else {
            testUsername.setText("incorrect name.length");
        }

        /*check password*/
        if (password.length()>=6&&password.length()<20) {
            for (int i = 0; i < password.length(); i++){
                for (int j = 0; j < stringVerify.length(); j++){
                    if (charPassword[i] == charVerify[j]){
                        countPassword++;
                    }
                }
            }
            if (countPassword == password.length()){
                if (password.equals(password2nd)){
                    testPassword.setText("correct p1==p2");
                    verifyPassword = true;

                }
                else
                    testPassword.setText("incorrect p1!=p2");
            }
            else
                testPassword.setText("incorrect p.char");
        }
        else {
            testPassword.setText("incorrect p.length");
        }

        /*check age*/
        if (age != 0){
            testAge.setText("correct");
            verifyAge = true;
        }
        else
            testAge.setText("incorrect");

        /*check sex*/
        if (!sex.equals("")){
            testSex.setText("correct");
            verifySex = true;
        }
        else
            testSex.setText("incorrect");
    }

}
