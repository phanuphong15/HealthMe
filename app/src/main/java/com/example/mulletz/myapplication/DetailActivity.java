package com.example.mulletz.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    protected ListView listView_detail;
    protected Button detail_delete;
    protected String dateDetail = "";
    int primary_user_detail = 0;
    int seq[];

    protected Database sqllite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        listView_detail = (ListView)findViewById(R.id.listview_detail);
        detail_delete = (Button)findViewById(R.id.button_detail_delete);
        sqllite = new Database(this);

        intialDetail();
        listviewDetail();

        detail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDetail();
            }
        });
    }
    public void intialDetail(){
        Cursor c = sqllite.select("User");
        while (c.moveToNext()){
            if (c.getString(5).equals("Y")){
                primary_user_detail = c.getInt(0);
            }
        }
        c = sqllite.select("Date");
        while (c.moveToNext()){
            if (c.isLast()){
                dateDetail = c.getString(0);
            }
        }
    }
    public void listviewDetail(){
        Cursor c = sqllite.selectEat(dateDetail,primary_user_detail);
        seq = new int[c.getCount()];
        int i = 0;
        ArrayList<String> food = new ArrayList<>();
        while(c.moveToNext()){
            food.add(c.getString(5));
            seq[i] = c.getInt(0);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listView_detail.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView_detail.setAdapter(adapter);
    }
    public void deleteDetail(){
        String str = "";
        int s = -1;
        SparseBooleanArray checkedItems = listView_detail.getCheckedItemPositions();
        for (int i = 0; i < listView_detail.getCount(); i++){
            if (checkedItems.get(i) == true){
                str += listView_detail.getItemAtPosition(i).toString();
                s = seq[i];
            }
        }
        if (s != -1){
            sqllite.deleteEat(s,dateDetail,primary_user_detail);
            listviewDetail();
        }
    }
}
