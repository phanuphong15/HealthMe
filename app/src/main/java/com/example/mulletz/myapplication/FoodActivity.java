package com.example.mulletz.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodActivity extends AppCompatActivity {
    protected Button meal;
    protected Button dessert;
    protected Button drink;
    protected Button fruit;
    protected Button eat;
    protected Button add;
    protected Button delete;
    protected ListView listView_food;
    protected SearchView searchViewFood;

    private final Handler handler = new Handler();
    protected SimpleDateFormat formatdate;
    String username = "";


    Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        sqllite = new Database(this);
        listView_food = (ListView)findViewById(R.id.listview_food);
        searchViewFood = (SearchView)findViewById(R.id.searchView_food);

        formatdate = new SimpleDateFormat("dd-MM-yyyy");

        eat = (Button)findViewById(R.id.button_eat);
        meal = (Button)findViewById(R.id.button_meal);
        dessert = (Button)findViewById(R.id.button_dessert);
        drink = (Button)findViewById(R.id.button_drink);
        fruit = (Button)findViewById(R.id.button_fruit);
        add = (Button)findViewById(R.id.button_main_add);
        delete = (Button)findViewById(R.id.button_main_delete);

        listviewFood(sqllite);

        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listviewSelect(sqllite,"ของคาว");
            }
        });
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listviewSelect(sqllite,"ของหวาน");
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listviewSelect(sqllite,"เครื่องดื่ม");
            }
        });
        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listviewSelect(sqllite,"ผักผลไม้");
            }
        });
        searchViewFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchFood(sqllite,newText.trim());
                return false;
            }
        });
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c =sqllite.select("User");
                while (c.moveToNext()){
                    if (c.getString(5).equals("Y")){
                        if (c.getString(11) == null || c.getString(11).equals("") || c.getString(11).equals("0.0")){
                            AlertDialog.Builder alert = new AlertDialog.Builder(FoodActivity.this);
                            alert.setTitle("แจ้งเตือน");
                            alert.setMessage("กลับไปคำนวณค่า BMR ก่อนน้าาา");
                            alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        else{
                            eatFood(sqllite);
                        }
                    }
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this,AddActivity.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodActivity.this,DeleteActivity.class));
            }
        });

        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                username = c.getString(1);
            }
        }

    }

    private void listviewFood(Database sqllite){
        Cursor c = sqllite.select("Food");
        ArrayList<String> food = new ArrayList<>();
        while(c.moveToNext()){
            food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listView_food.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_food.setAdapter(adapter);
    }
    private void listviewSelect(Database sqllite,String WHERE){
        Cursor c = sqllite.selectFood(WHERE);
        ArrayList<String> food = new ArrayList<>();
        while (c.moveToNext()){
            food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listView_food.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_food.setAdapter(adapter);
    }
    private void searchFood(Database sqllite,String keyword){
        Cursor c = sqllite.selectSearchFood(keyword);
        ArrayList<String> food = new ArrayList<>();
        while(c.moveToNext()){
            food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listView_food.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_food.setAdapter(adapter);
    }
    private void eatFood(Database sqllite){
        String str = "";
        String state = "";
        int PRIMARY_FOOD = 0;
        int PRIMARY_USER = 0;
        int calorie = 0;
        String PRIMARY_DATE = "";
        String[] output = {"",""};
        SparseBooleanArray checkedItems = listView_food.getCheckedItemPositions();
        for (int i = 0; i < listView_food.getCount(); i++){
            if (checkedItems.get(i) == true){
                str += listView_food.getItemAtPosition(i).toString();
                output = str.split("\n");
            }
        }
        Cursor c = sqllite.select("Food");
        while (c.moveToNext()){
            if (output[0].equals(c.getString(1))){
                PRIMARY_FOOD = Integer.parseInt(c.getString(0));
                calorie = Integer.parseInt(c.getString(2));

                //String test = String.valueOf(PRIMARY_FOOD);
                //Toast.makeText(getApplicationContext(),test,Toast.LENGTH_LONG).show();

            }
        }
        c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                PRIMARY_USER = Integer.parseInt(c.getString(0));
                //String test = c.getString(1);
                //Toast.makeText(getApplicationContext(),test,Toast.LENGTH_LONG).show();
            }
        }
        c = sqllite.select("Date");
        while (c.moveToNext()){
            if (c.isLast()){
                PRIMARY_DATE = c.getString(0);
            }
        }
        if (PRIMARY_FOOD != 0){
                    int seq = 0;
                    state = "Y";
                    c = sqllite.select("Eat");
                    while (c.moveToNext()){
                        if (c.isLast()){
                            seq = Integer.parseInt(c.getString(0));
                            seq++;
                        }
                    }
                    sqllite.insertEat(seq,PRIMARY_USER,PRIMARY_DATE,PRIMARY_FOOD,state,output[0],calorie);

                    AlertDialog.Builder alert = new AlertDialog.Builder(FoodActivity.this);
                    alert.setTitle("คุณได้กิน");
                    LayoutInflater factory = LayoutInflater.from(FoodActivity.this);
                    final View view =  factory.inflate(R.layout.dialog_eat,null);
                    ImageView image = (ImageView)view.findViewById(R.id.imageView_dialog_eat);
                    TextView text1 = (TextView)view.findViewById(R.id.textView_dialog_1);
                    TextView text2 = (TextView)view.findViewById(R.id.textView_dialog_2);
                    text1.setText(output[0]);
                    text2.setText("ได้รับ "+calorie+" กิโลแคลอรี่");
                    alert.setView(view);
                    alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
            }

        }


    }

