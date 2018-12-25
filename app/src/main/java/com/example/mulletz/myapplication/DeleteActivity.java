package com.example.mulletz.myapplication;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {
    protected ListView listviewDelete;
    protected Button delete;
    protected SearchView searchViewDelete;

    int primary_foodID[];
    Database sqllite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        listviewDelete = (ListView)findViewById(R.id.listview_delete);
        delete = (Button)findViewById(R.id.button_delete);
        searchViewDelete = (SearchView)findViewById(R.id.searchView_delete_food);
        sqllite = new Database(this);

        listviewFoodDelete();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFood();
            }
        });
        searchViewDelete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }
    public void listviewFoodDelete(){
        Cursor c = sqllite.selectFoodIncrese();
        primary_foodID = new int[c.getCount()];
        int i = 0;
        ArrayList<String> food = new ArrayList<>();
            while(c.moveToNext()){
                food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
                primary_foodID[i] = c.getInt(0);
                i++;
            }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listviewDelete.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listviewDelete.setAdapter(adapter);
    }
    public void deleteFood(){
        String str = "";
        int ID = 0;
        String[] output = {"","",""};
        SparseBooleanArray checkedItems = listviewDelete.getCheckedItemPositions();
        for (int i = 0; i < listviewDelete.getCount(); i++){
            if (checkedItems.get(i) == true){
                str += listviewDelete.getItemAtPosition(i).toString();
                output = str.split(" ");
                ID = primary_foodID[i];
            }
        }
        if (!output[0].equals("")){
            final int s = ID;
            AlertDialog.Builder alert = new AlertDialog.Builder(DeleteActivity.this);
            alert.setTitle("ลบรายการอาหาร");
            alert.setMessage("คุณต้องการลบ\n"+output[0]);
            alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sqllite.deleteFood(s);
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(DeleteActivity.this);
                    alert2.setTitle("ลบรายการอาหาร");
                    alert2.setMessage("ลบสำเร็จ !");
                    alert2.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listviewFoodDelete();
                        }
                    }).show();
                }
            });
            alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }
    }
    private void searchFood(Database sqllite,String keyword){
        Cursor c = sqllite.selectSearchFood(keyword,"Y");
        primary_foodID = new int[c.getCount()];
        int i = 0;
        ArrayList<String> food = new ArrayList<>();
        while(c.moveToNext()){
            food.add(c.getString(1).trim()+"\n"+c.getString(2)+" กิโลแคลอรี่ ("+c.getString(3)+")");
            primary_foodID[i] = c.getInt(0);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_checked,food);
        listviewDelete.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listviewDelete.setAdapter(adapter);
    }
}
