package com.example.mulletz.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SlotActivity extends AppCompatActivity {
    protected ListView listviewSlot;
    protected SearchView searchviewSlot;
    protected Button selectSlot;
    protected Button random;
    protected TextView t9;
    protected TextView showRandom;
    protected Button reset;

    int seq = -1;
    int PRIMARY_USER = -1;
    int calorie = -1;
    String PRIMARY_DATE = "";
    String FoodName = "";
    String state = "Y";
    int lastPrimary = -1;
    int foodID[];
    int[] primaryFood = {-1,-1,-1,-1,-1,-1,-1,-1};

    Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);

        sqllite = new Database(this);

        listviewSlot = (ListView)findViewById(R.id.listview_slot);
        searchviewSlot = (SearchView)findViewById(R.id.searchview_slot);
        selectSlot = (Button)findViewById(R.id.button_slot_select);
        random = (Button)findViewById(R.id.button_random);
        t9 = (TextView)findViewById(R.id.textView9);
        reset = (Button)findViewById(R.id.button_slot_reset);
        showRandom = (TextView)findViewById(R.id.textView_showRandom);

        showRandom.setVisibility(View.INVISIBLE);
        intialSlot();
        listviewFood(sqllite);
        random.setVisibility(View.INVISIBLE);
        //listviewSlot.setVisibility(View.INVISIBLE);
        //selectSlot.setVisibility(View.INVISIBLE);
        searchviewSlot.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        selectSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectslot();
            }
        });
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomFood();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primaryFood = new int[]{-1,-1,-1,-1,-1,-1,-1,-1};
                listviewFood(sqllite);
                random.setVisibility(View.INVISIBLE);
                t9.setText("0/8");
            }
        });

    }
    public void intialSlot(){
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
               PRIMARY_USER = c.getInt(0);
            }
        }
        c = sqllite.select("Date");
        while (c.moveToNext()){
            if (c.isLast()){
                PRIMARY_DATE = c.getString(0);
            }
        }
    }
    public void randomFood(){
        listviewSlot.setVisibility(View.INVISIBLE);
        searchviewSlot.setVisibility(View.INVISIBLE);
        selectSlot.setVisibility(View.INVISIBLE);
        random.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
        t9.setVisibility(View.INVISIBLE);
        showRandom.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listviewSlot.setVisibility(View.VISIBLE);
                searchviewSlot.setVisibility(View.VISIBLE);
                selectSlot.setVisibility(View.VISIBLE);
                random.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                t9.setVisibility(View.VISIBLE);
                showRandom.setVisibility(View.INVISIBLE);
                alertRandom();
            }
        }, 3000); // Millisecond 1000 = 1 sec

    }
    public void alertRandom(){
        Random r = new Random();
        int count = 0;
        for (int i = 0 ; i < 8 ; i++){
            if (primaryFood[i] == -1){
                count++;
            }
        }
        int n = 8 - count;
        int sumRandom = r.nextInt(n);

        lastPrimary = primaryFood[sumRandom];
        Cursor c = sqllite.selectFoodPrimary(lastPrimary);
        while (c.moveToNext()){
            FoodName = c.getString(1);
            calorie = c.getInt(2);
        }
        c = sqllite.select("Eat");
        while (c.moveToNext()){
            if (c.isLast()){
                seq = Integer.parseInt(c.getString(0));
                seq++;
            }
        }
        //Toast.makeText(getApplicationContext(),String.valueOf(lastPrimary),Toast.LENGTH_LONG).show();
        AlertDialog.Builder alert = new AlertDialog.Builder(SlotActivity.this);
        alert.setTitle("คุณสุ่มอาหารได้");
        alert.setMessage(FoodName+"\nต้องการจะกินหรือไม่ ?");
        alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqllite.insertEat(seq,PRIMARY_USER,PRIMARY_DATE,lastPrimary,state,FoodName,calorie);

                AlertDialog.Builder alert = new AlertDialog.Builder(SlotActivity.this);
                alert.setTitle("คุณได้กิน");
                LayoutInflater factory = LayoutInflater.from(SlotActivity.this);
                final View view =  factory.inflate(R.layout.dialog_eat,null);
                ImageView image = (ImageView)view.findViewById(R.id.imageView_dialog_eat);
                TextView text1 = (TextView)view.findViewById(R.id.textView_dialog_1);
                TextView text2 = (TextView)view.findViewById(R.id.textView_dialog_2);
                text1.setText(FoodName);
                text2.setText("ได้รับ "+calorie+" กิโลแคลอรี่");
                alert.setView(view);
                alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });
        alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
    public void listviewFood(Database sqllite){
        Cursor c = sqllite.select("Food");
        foodID = new int[c.getCount()];
        int i = 0;
        ArrayList<String> food = new ArrayList<>();
        while(c.moveToNext()){
            food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
            foodID[i] = c.getInt(0);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listviewSlot.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listviewSlot.setAdapter(adapter);
    }
    public void searchFood(Database sqllite,String keyword){
        Cursor c = sqllite.selectSearchFood(keyword);
        foodID = new int[c.getCount()];
        int i = 0;
        ArrayList<String> food = new ArrayList<>();
        while(c.moveToNext()){
            food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
            foodID[i] = c.getInt(0);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listviewSlot.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listviewSlot.setAdapter(adapter);
    }
    public void selectslot(){
        String str = "";
        int s = -1;
        SparseBooleanArray checkedItems = listviewSlot.getCheckedItemPositions();
        for (int i = 0; i < listviewSlot.getCount(); i++){
            if (checkedItems.get(i) == true){
                str += listviewSlot.getItemAtPosition(i).toString();
                s = foodID[i];
            }
        }
        if (s != -1){
            for (int i = 0 ; i < 8 ; i++){
                if (primaryFood[i] == -1){
                    primaryFood[i] = s;
                    if (primaryFood[0] != -1){
                        random.setVisibility(View.VISIBLE);
                    }
                    if (primaryFood[7] != -1) {
                      //  Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
                    }
                    t9.setText(String.valueOf(i+1)+"/8");
                    break;
                }
            }

        }
    }
}
