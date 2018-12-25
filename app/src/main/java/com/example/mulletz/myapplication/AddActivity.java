package com.example.mulletz.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    protected EditText addFoodname;
    protected EditText addCalorie;
    protected EditText addSize;
    protected RadioButton add1;
    protected RadioButton add2;
    protected RadioButton add3;
    protected RadioButton add4;
    protected RadioButton add5;
    protected Button addButton;

    protected String Vfoodname = "-1";
    protected String VcalorieConvert = "-1";
    protected int Vcalorie = 0;
    protected String Vsize = "-1";
    protected String Vtypefood = "-1";
    protected int Vprimary_foodID = 0;


    protected boolean verifyName = false;
    protected boolean verifyCalorie = false;
    protected boolean verifySize = false;
    protected boolean verifyTypefood = false;

    Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        addFoodname = (EditText)findViewById(R.id.editText_add_foodname);
        addCalorie = (EditText)findViewById(R.id.editText_add_calorie);
        addSize = (EditText)findViewById(R.id.editText_add_size);
        add1 = (RadioButton)findViewById(R.id.radioButton_add_1);
        add2 = (RadioButton)findViewById(R.id.radioButton_add_2);
        add3 = (RadioButton)findViewById(R.id.radioButton_add_3);
        add4 = (RadioButton)findViewById(R.id.radioButton_add_4);
        add5 = (RadioButton)findViewById(R.id.radioButton_add_5);
        addButton = (Button)findViewById(R.id.button_add);

        sqllite = new Database(this);
        saveFood();

    }
    public void saveFood(){
        addFood();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
                checkFood();
                if (verifyName && verifyCalorie && verifySize && verifyTypefood){
                    Vcalorie = Integer.parseInt(VcalorieConvert);
                    verifyName = false;
                    verifyCalorie = false;
                    verifySize = false;
                    verifyTypefood = false;
                    Toast.makeText(getApplicationContext(),"เพิ่มอาหารสำเร็จ",Toast.LENGTH_LONG).show();
                    sqllite.addFood(Vfoodname,Vcalorie,Vsize,Vprimary_foodID,Vtypefood);
                }
            }
        });
    }
    private void addFood(){
        Vfoodname = addFoodname.getText().toString().trim();
        VcalorieConvert = addCalorie.getText().toString().trim();
        Vsize = addSize.getText().toString();

        if (add1.isChecked()){
            Vtypefood = "ของคาว";
            Vprimary_foodID = 1;
        }
        else if (add2.isChecked()){
            Vtypefood = "ของหวาน";
            Vprimary_foodID = 2;
        }
        else if (add3.isChecked()){
            Vtypefood = "เครื่องดื่ม";
            Vprimary_foodID = 3;
        }
        else if (add4.isChecked()){
            Vtypefood = "ผักผลไม้";
            Vprimary_foodID = 4;
        }
        else if (add5.isChecked()){
            Vtypefood = "ไม่ระบุ";
        }
    }
    public void checkFood(){
        if (Vfoodname.length() > 0){
            verifyName = true;
        }
        if (VcalorieConvert.length() > 0){
            verifyCalorie = true;
        }
        if (Vsize.length() > 0){
            verifySize = true;
        }
        if (Vprimary_foodID != 0){
            verifyTypefood = true;
        }
    }
}
