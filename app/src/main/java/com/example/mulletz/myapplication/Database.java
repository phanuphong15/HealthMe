package com.example.mulletz.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.Date;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE = "healthmeDB.db";
    private static final int VERSION = 1;

    protected String CREATE_DATABASE_USER;
    protected String CREATE_DATABASE_FOOD;
    protected String CREATE_DATABASE_TYPEFOOD;
    protected String CREATE_DATABASE_BODY;
    protected String CREATE_DATABASE_TYPEBODY;
    protected String CREATE_DATABASE_ACTIVITY;
    protected String CREATE_DATABASE_DATE;
    protected String CREATE_DATABASE_EAT;
    protected String CREATE_DATABASE_FAVORITE;
    protected String CREATE_DATABASE_SPIN;
    protected String CREATE_DATABASE_MEASURE;

    protected String INSERT_DATABASE_FOOD;
    protected String INSERT_DATABASE_TYPEFOOD;
    protected String INSERT_DATABASe_ACTIVITY;
    protected int PRIMARY_TYPEFOOD = 1;
    protected int PRIMARY_ACTIVITY = 1;
    protected int PRIMARY_FOOD = 1;


    public Cursor selectFoodPrimary(int PRIMARY_FOOD){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Food WHERE foodNO = '"+PRIMARY_FOOD+"'";
        return db.rawQuery(sql,null);
    }
    public void deleteEat(int SEQ,String DATE,int PRIMARY_USER){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM Eat WHERE seq = '"+SEQ+"' AND userID = '"+PRIMARY_USER+"' AND startdate = '"+DATE+"'";
        db.execSQL(sql);
    }
    public void deleteFood(int FOODID){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM Food WHERE foodNO = '"+FOODID+"'";
        db.execSQL(sql);
    }
    public Cursor selectFoodIncrese(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Food WHERE incresasState = 'Y'";
        return db.rawQuery(sql,null);
    }
    public void addFood(String FOODNAME,int CALORIE,String SIZE,int TYPEFOODID,String TYPEFOOD){
        int i = -1;
        Cursor c = select("Food");
        while (c.moveToNext()){
            if (c.isLast()){
                 i = c.getInt(0);
                 i++;
            }
        }
        SQLiteDatabase db = getWritableDatabase();
        String INSERT = "INSERT INTO food (foodNO,foodname,calorie,size,incresasState,typefoodID,typefoodname) VALUES ('"+i+"',?,'"+CALORIE+"',?,'Y','"+TYPEFOODID+"',?)";
        String[] VALUES = {FOODNAME,SIZE,TYPEFOOD};
        db.execSQL(INSERT,VALUES);
    }
    public Cursor selectEat(String DATE,int PRIMARY_USER){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Eat WHERE startdate ='"+DATE+"'AND userID = '"+PRIMARY_USER+"'";
        return db.rawQuery(sql,null);
    }
    public void insertEat(int SEQ,int USER,String DATE,int FOOD,String STATE,String FOODNAME,int CALORIE){
        SQLiteDatabase db = getWritableDatabase();
        String INSERT = "INSERT INTO Eat (seq,userID,startdate,foodNO,todayState,foodname,calorie) VALUES ('"+SEQ+"','"+USER+"',?,'"+FOOD+"',?,?,'"+CALORIE+"')";
        String VALUES[] = {DATE,STATE,FOODNAME};
        db.execSQL(INSERT,VALUES);
    }
    public void insertDate (String DATE){
        SQLiteDatabase db = getWritableDatabase();
        String INSERT = "INSERT INTO Date (startdate) VALUES ('"+DATE+"')";
        db.execSQL(INSERT);
    }
    public Cursor select(String TABLE_NAME){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME;
        return db.rawQuery(sql,null);
    }
    public Cursor selectSearchFood(String KEYWORD){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Food WHERE foodname LIKE '"+KEYWORD+"%'";
        return db.rawQuery(sql,null);
    }
    public Cursor selectSearchFood(String KEYWORD,String STATE){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Food WHERE foodname LIKE '"+KEYWORD+"%' AND incresasState ='"+STATE+"'";
        return db.rawQuery(sql,null);
    }
    public Cursor selectFood(String WHERE){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Food WHERE typefoodname = '"+WHERE+"'";
        return db.rawQuery(sql,null);
    }
    public Cursor selectFoodAll(String username){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Food WHERE username = '"+username+"'";
        return db.rawQuery(sql,null);
    }
    public void insertUser(int PRIMARY,String USERNAME,String PASSWORD,String SEX,int AGE){
        SQLiteDatabase db = getWritableDatabase();
        String INSERT = "INSERT INTO User (userID,username,password,sex,age) VALUES('"+PRIMARY+"',?,?,?,?)";
        String VALUES[] = {USERNAME,PASSWORD,SEX,String.valueOf(AGE)};
        db.execSQL(INSERT,VALUES);
    }
    public void insertUserTest(String USERNAME,String PASSWORD,String SEX,int AGE){
        Cursor c = select("User");
        if (c.getCount() == 0){
            SQLiteDatabase db = getWritableDatabase();
            String INSERT = "INSERT INTO User (userID,username,password,sex,age) VALUES('1',?,?,?,?)";
            String VALUES[] = {USERNAME,PASSWORD,SEX,String.valueOf(AGE)};
            db.execSQL(INSERT,VALUES);
        }
        else {
            while (c.moveToNext()){
                if (c.isLast()){
                    int PRIMARY = Integer.parseInt(c.getString(0));
                    PRIMARY++;
                    SQLiteDatabase db = getWritableDatabase();
                    String INSERT = "INSERT INTO User (userID,username,password,sex,age) VALUES('"+PRIMARY+"',?,?,?,?)";
                    String VALUES[] = {USERNAME,PASSWORD,SEX,String.valueOf(AGE)};
                    db.execSQL(INSERT,VALUES);
                }
            }
        }

    }
    public void updateLogin(String username,String LOGIN_STATE){
        SQLiteDatabase db = getWritableDatabase();
        String UPDATE = "UPDATE User SET loginState = ? WHERE username ='"+username+"'";
        String  VALUES2[] = {LOGIN_STATE};
        db.execSQL(UPDATE,VALUES2);
    }
    public void updateLogout(){
        SQLiteDatabase db = getWritableDatabase();
        String UPDATE = "UPDATE User SET loginState = ?";
        String  VALUES2[] = {""};
        db.execSQL(UPDATE,VALUES2);
    }
    public void updateUserSetting(String AGE,String HEIGHT,String WEIGHT,int ACT_ID,String ACT_VOLUME){
        SQLiteDatabase db = getWritableDatabase();
        String UPDATE = "UPDATE User SET age = ?,height = ?,weight = ?,actvolume = ? ,actID ='"+ACT_ID+"' WHERE loginState = 'Y'";
        String VALUES[] = {AGE,HEIGHT,WEIGHT,ACT_VOLUME};
        db.execSQL(UPDATE,VALUES);
    }
    public void updateUserBMI(String BMI){
        SQLiteDatabase db = getWritableDatabase();
        String UPDATE = "UPDATE User SET bmi = ? WHERE loginState = 'Y'";
        String VALUES[] = {BMI};
        db.execSQL(UPDATE,VALUES);
    }
    public void updateUserBMR(String BMR){
        SQLiteDatabase db = getWritableDatabase();
        String UPDATE = "UPDATE User SET bmr = ? WHERE loginState = 'Y'";
        String VALUES[] = {BMR};
        db.execSQL(UPDATE,VALUES);
    }
    public void insertTypefood(){
        SQLiteDatabase db = getWritableDatabase();
        INSERT_DATABASE_TYPEFOOD = "INSERT INTO Typefood (typefoodID,typefoodname)VALUES('"+PRIMARY_TYPEFOOD+"','ของคาว')";
        db.execSQL(INSERT_DATABASE_TYPEFOOD);
        PRIMARY_TYPEFOOD++;

        INSERT_DATABASE_TYPEFOOD = "INSERT INTO Typefood (typefoodID,typefoodname)VALUES('"+PRIMARY_TYPEFOOD+"','ของหวาน')";
        db.execSQL(INSERT_DATABASE_TYPEFOOD);
        PRIMARY_TYPEFOOD++;

        INSERT_DATABASE_TYPEFOOD = "INSERT INTO Typefood (typefoodID,typefoodname)VALUES('"+PRIMARY_TYPEFOOD+"','เครื่องดื่ม')";
        db.execSQL(INSERT_DATABASE_TYPEFOOD);
        PRIMARY_TYPEFOOD++;

        INSERT_DATABASE_TYPEFOOD = "INSERT INTO Typefood (typefoodID,typefoodname)VALUES('"+PRIMARY_TYPEFOOD+"','ผักผลไม้')";
        db.execSQL(INSERT_DATABASE_TYPEFOOD);
        PRIMARY_TYPEFOOD++;
    }
    public void insertActivity(){
        SQLiteDatabase db = getWritableDatabase();
        INSERT_DATABASe_ACTIVITY = "INSERT INTO Activity (actID,actname,actvolume)VALUES ('"+PRIMARY_ACTIVITY+"','ไม่เคย','1.2')";
        db.execSQL(INSERT_DATABASe_ACTIVITY);
        PRIMARY_ACTIVITY++;

        INSERT_DATABASe_ACTIVITY = "INSERT INTO Activity (actID,actname,actvolume)VALUES ('"+PRIMARY_ACTIVITY+"','น้อย','1.375')";
        db.execSQL(INSERT_DATABASe_ACTIVITY);
        PRIMARY_ACTIVITY++;

        INSERT_DATABASe_ACTIVITY = "INSERT INTO Activity (actID,actname,actvolume)VALUES ('"+PRIMARY_ACTIVITY+"','สม่ำเสมอ','1.55')";
        db.execSQL(INSERT_DATABASe_ACTIVITY);
        PRIMARY_ACTIVITY++;

        INSERT_DATABASe_ACTIVITY = "INSERT INTO Activity (actID,actname,actvolume)VALUES ('"+PRIMARY_ACTIVITY+"','เยอะ','1.725')";
        db.execSQL(INSERT_DATABASe_ACTIVITY);
        PRIMARY_ACTIVITY++;

        INSERT_DATABASe_ACTIVITY = "INSERT INTO Activity (actID,actname,actvolume)VALUES ('"+PRIMARY_ACTIVITY+"','ระดับนักกีฬา','1.90')";
        db.execSQL(INSERT_DATABASe_ACTIVITY);
        PRIMARY_ACTIVITY++;
    }
    public void insertFood(){
        SQLiteDatabase db = getWritableDatabase();
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กระเพาะปลา','150','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กระเพาะปลาตุ๋นน้ำแดง','225','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยไข่','40','1 ชาม','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยคลุกมะพร้าว','100','1 ถ้วย ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยฉาบ','29','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยตาก','30','1 ผล','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยทอด (กล้วยแขก)','50','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยน้ำว้า','36','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยบวชชี','152','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยเล็บมือนาง','30','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยหอม','77','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยจั๊บ','240 ','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยจั๊บญวณ','235','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวแขก','380','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวคั่วไก่','435','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวต้มยำกุ้ง','320','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเนื้อเรียง','370','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเนื้อสับ','370','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวผัดกะเพราไก่','440','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวผัดไทยใส่ไข่','577',' 1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวราดหน้าปลากระพง','435','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเรือน้ำตก','180','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเรือน้ำตกแห้ง','225','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นปลาน้ำ','375','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นปลาน้ำ','420','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นเล็กต้มยำหมู','335','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นเล็กหมูแห้ง','330','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นหมี่น้ำลูกชิ้นเนื้อวัว','226','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นหมี่ลูกชิ้นเนื้อ','258','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นใหญ่ผัดซีอิ๊วใส่ไข่','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นใหญ่ราดหน้าไก่','397','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวเส้นใหญ่ราดหน้าหมู','397','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ก๋วยเตี๋ยวหลอด','50','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กะละแม','65','1 ห่อเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กะหรี่พัฟ','157','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งแช่น้ำปลา','14','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งทอดกระเทียมพริกไทย','86','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งนางนึ่งนมสด','185','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งนึ่งกระเทียม','124','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งผัดพริกอ่อน','235','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งฝอยชุบแป้งทอด','308','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้งอบวุ้นเส้น','300','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุนเชียงทอด','120','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กุ้ยช่ายทอด','114','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกาเหลาราดหน้า','300','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกาเหลาลูกชิ้นน้ำ','225','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกี๊ยวกรอบราดหน้ากุ้ง','635','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกี๊ยวซ่า','63','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกี๊ยวน้ำกุ้ง','275','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกี๊ยวปลาน้ำ','165','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงกะหรี่ไก่','450','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงกะหรี่หมู','325','1 ถ้วย ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงขี้เหล็ก','195','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงขี้เหล็กหมูย่าง','245','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเขียวหวานไก่','240','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเขียวหวานปลาดุก','235','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเขียวหวานลูกชิ้นปลากราย','240','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเขียวหวานหมู','235','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดไข่เจียว','120','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดตำลึงหมูสับ','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดเต้าหู้ไม่ใส่หมูสับ','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดเต้าหู้ยัดไส้','110','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดถั่วงอกไม่ใส่หมูสับ','50','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดถั่วงอกหมูสับ','50','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดฟักยัดไส้','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดมะระยัดไส้','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดมะระสอดไส้หมูวุ้นเส้น','66','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงจืดวุ้นเส้น','85','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงไตปลา','50','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงป่าไก่','110','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดไก่ใส่มะเขือ','235','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดไก่ใส่หน่อไม้','245','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดเป็ดย่าง','240','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดฟักทองใส่หมู','250','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดฟักทองหมู','250','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดมะเขือไก่','235','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดลูกชิ้นปลา','240','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดหน่อไม้ไก่','245','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเผ็ดหมูยอดมะพร้าว','245','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงมัสมั่นไก่','325','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเลียง','115','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเลียงผักรวม','48','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้ม','28','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มชะอมไข่ทอดใส่กุ้ง','270','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มดอกแคกุ้ง','105','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มปลาช่อนผักบุ้ง','105', '1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มปลาแปะซะ','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มผักกะเฉด-ปลา','110','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มผักบุ้งปลาช่อน','105','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงส้มผักรวม','120','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงหมูเทโพ','300','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเหลืองมะละกอกุ้ง','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แกงเหลืองหน่อไม้ดองกับปลา','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แก้วมังกร','60','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โกยซีหมี่','550','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ KFC สะโพก','355','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ KFC ฮอทวิงส์','78','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ตุ๋นมะนาวดอง','110','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ทอด','345','1 น่อง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ผัดขิง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ยอชุบแป้งทอด','73','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไก่ย่าง','165','1 น่อง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมกรวย','63','1 กรวย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมกล้วย','120','1 ห่อ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมกลีบลำดวน','23','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมกุ้ยช่าย','53','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเกลียว','25','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมขี้หนู','165','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเข่ง','120','1 กระทง','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมครก','92','1 คู่','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีน','80','1 จับ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีนแกงเขียวหวานไก่','594','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีนซาวน้ำ','320','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีนน้ำเงี้ยว','243','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีนน้ำพริก','228','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีนน้ำยา','332','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีนน้ำยาปักษ์ใต้','146','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมจีบหมู','32','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมตาล','58','1 กระทง','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมถั่วแปป','14','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเทียน','103','1 ห่อ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมบ้าบิ่น','130','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเบื้องไทยไส้เค็ม','50','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเบื้องไทยไส้หวาน','60','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเปียกปูน','195','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมผิง','5','1 ก้อนเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมฝักบัว','70','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมเล็บมือนาง','275','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมหน้านวล','22','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมหัวผักกาดผัด','560','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมหัวผักกาดผัดใส่ไข่','630','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวกะเพราเนื้อ','622','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวกุ้งทอดกระเทียม','495','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเกรียบกุ้ง','37','1 แผ่น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเกรียบปากหม้อ','26','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวแกงกะหรี่ไก่','389','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวแกงเผ็ดไก่','485','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวไก่อบ','490','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวขาหมู','690','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวไข่เจียว','445','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวคลุกกะปิ','410','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวแช่','350','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวซอยไก่','395','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวซอยหมู','395','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวต้ม (ข้าวกล้อง) ','120','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวต้ม (ข้าวขาว) ','120','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวต้มทรงเครื่อง','230','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวต้มปลา','325','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวต้มมัด','285','1 มัด','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวตังหน้าตั้ง','90','1 แผ่น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวตังหมูหยอง','80','1 แผ่น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวแตนราดน้ำตาล','150','1 แผ่น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวปุ้น(ส้มตำ-ขนมจีน) ','180','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกระเพราหมูกรอบ','650','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกะเพรากุ้ง','540','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกะเพราไก่','554','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกะเพราไก่ไข่ดาว','630','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกะเพราหมู','580','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกุ้งใส่ไข่','595','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดกุนเชียง','590','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดแกงเขียวหวานไก่','630','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดคะน้าหมูกรอบ','670','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดต้มยำทะเลแห้ง','400','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดน้ำพริกกุ้งสด','460','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดน้ำพริกเผาหมู','665','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดน้ำพริกลงเรือ','605','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดปลาเค็ม','405','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดปลาหมึกน้ำพริกเผา','535','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดปูใส่ไข่','610','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดผักกระเฉดหมูกรอบ','600','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดมันกุ้งใส่ไข่','575','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดรวมมิตร (น้ำมันน้อย) ','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดสับปะรด','335','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดไส้กรอก','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดหนำเลียบหมูใส่ไข่','370','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดหมูน้ำพริกเผา','665','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดหมูใส่ไข่','557','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดแหนม','610','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวผัดอเมริกัน','790','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวพะแนงเนื้อ','457','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวโพดต้ม','200','1 ฝัก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวมันไก่','596','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวมันไก่ทอด','695','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเม่าทอด','209','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวยำปักษ์ใต้','248','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวราดแกงเขียวหวานไก่','483','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวราดผัดผักบุ้ง ไข่ดาว','410','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวราดผัดผักใส่หมู','370','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวราดหน้าไก่','400','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวสตูว์ไก่','465','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวสวย','68','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวสวย (ข้าวกล้อง)','80','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวสวย (ข้าวขาว)','80','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหน้ากุ้งผัดพริดสด','540','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหน้าเป็ด','495','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหมกไก่','534','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหมูแดง','541','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหมูทอด','416','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหมูทอดกระเทียม','525','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวหมูอบ','389','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวกะทิทุเรียน','225','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวดำ','205','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวตัด','210','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวนึ่ง','160','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวมูลกะทิ','197','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้าสัขยา','223','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูทอด','440','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูสวรรค์','480','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวกะทิทุเรียน','225','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวดำ','205','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวตัด','210','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวนึ่ง','160','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวมูลกะทิ','197','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้าสัขยา','223','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูทอด','440','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวอบเผือก','385','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวกะทิทุเรียน','225','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวดำ','205','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวตัด','210','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวนึ่ง','160','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวมูลกะทิ','197','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้าสัขยา','223','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูทอด','440','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่เค็ม','75','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่เจียว','215','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ดาว','215','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ดาวทรงเครื่อง','250','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ต้ม','73','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ตุ๋น','75','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ตุ๋นทรงเครื่อง','159','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่นกกระทา','191','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่พะโล้','180','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ยัดไส้','310','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ลวก','75','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ลูกเขย','205','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่หงส์','58','1 ใบ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครองแครงกรอบเค็ม','19','1 ตัว','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครัวซอง','235','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครีมซุปไก่','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คอหมูย่าง','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คะน้าหมูกรอบ','420','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เครื่องในไก่ผัดขิง','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แคนตาลูป','4','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แคบหมูมีมัน','13','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แคบหมูไร้มัน','10','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โครงไก่ทอก','656','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เงาะ','12','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แจ่วบอง','25','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โจ๊กใส่ไข่','250','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โจ๊กหมู','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โจ๊กหมูตับใส่ไข่ลวก','230','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เฉาก๊วย','90','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ชมพู่','16','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เชอรี่','15','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาลาเปาทอด','157','1 ลูก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาลาเปาไส้หมู','202','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาหริ่ม','217','1 ชุด','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาหริ่ม','275','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซุปข้าวโพด','140','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซุปผักใส','15','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซุปหน่อไม้','40','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มกะทิสายบัวปลาทูนึ่ง','225','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มข่าไก่','210','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มโคล้งไก่ย่าง','115','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มโคล้งปลากรอบ','60','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มจับฉ่าย','15','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มจืดเลือดหมู','120','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มตือฮวน','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มผักกาดดองซี่โครงหมู','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำกุ้ง','65','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำไก่','60','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำไก่ใส่เห็ด','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำปลากระป๋อง','55','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำปลากระพง','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำทู','10','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำปลาหมึก','9','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำเห็ดสด','30','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มส้มปลาทู','130','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มหัวผักกาดขาวซี่โครงหมู','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ตะโก้แห้ว','78','1 กระทง','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ตับไก่ปิ้ง','60','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าส่วน','215','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู้แข็ง','210','1 ก้อน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู้ไข่','70','1 หลอด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู่ทอด','57','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู้นมสด','150','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าฮวยน้ำขิง','130','1 ถ้วย','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แตงไทย','4','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แตงโม','150','1 กิโลกรัม','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วทอด','82','1 แพ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วลิสงทอด','62','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทองม้วน','35','1 อัน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทอดมันกุ้ง','255','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทอดมันปลากลาย','37','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทับทิมกรอบ','250','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทุเรียน','59','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทุเรียนทอดกรอบ','9','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เทมปุระ','77','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','นมถั่วเหลือง(ไม่มีน้ำตาล)','55','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น่องไก่ทอด','345','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น่องไก่ย่าง','97','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้อยหน่า','61','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำกระเจี๊ยบ','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำโค้ก,น้ำแป๊ปซี่','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำปลามะนาว','165','1 ตัวเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกกะปิผักสด','28','1 ช้อนโต๊ะ','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกกุ้งเผา','90','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกปลาป่น','35','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกมะขามปียก','55','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกมะขามสด','210','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกมะม่วง','100','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกลงเรือ','195','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกหนุ่ม','18','1 ช้อนโต๊ะ','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกอ่อง','80','1 ช้อนโต๊ะ','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะเขือเทศ','48','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะตูม','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะนาว','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะพร้าว','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำลำใย','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำส้มคั้น','160','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำสับปะรด','125','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำองุ่น','112','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำอ้อย','240','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เนื้อไก่ชุปแป้งทอด','71','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เนื้อน้ำตก','165','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เนื้อผัดหวาน','590','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บรั่นดี','2','1 cc','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะจ่าง','300','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กรอบราดหน้า','515','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กรอบราดหน้าไก่หน่อไม้','660','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กรอบราดหน้ารวมมิตร','690','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กึ่งสำเร็จรูป','253','1 ก้อน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กึ่งสำเร็จรูปผัดกระเพราหมู','540','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กึ่งสำเร็จรูปผัดขี้เมา','530','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่เกี๊ยวเป็ดย่าง','415','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น่องไก่-น้ำ','375','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำเกี๊ยวหมูแดง','305','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำต้มยำ','300','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำน่องไก่','375','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำเป็ด','370','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำหมูต้มยำ','300','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่หมูแดง','231','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บัวลอย','223','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บัวลอยเผือก','300','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บาร์บีคิวซี่โครงหมูข้าวคลุกเนย','340','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เบียร์ไทย','148','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลากะพงนึ่งมะนาว','155','1 ชิ้นกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาช่อนทอด','1840','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาช่อนอบเกลือ','220','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาชุบขนมปังทอด+สลัดผัก','595','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาซาบะย่าง','220','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาแซลมอนย่าง','260','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาทอดสามรส','470','1 ตัวกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาทูทอด','280','1 ตัวกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลานึ่ง','156','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาเผา','156','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาราดซอสมะนาวมันฝรั่งทอด','560','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาราดพริก','300','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาร้าทรงเครื่อง','155','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาร้าสับ','155','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาเล็กปลาน้อยทอดกรอบ','80','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาสลิดทอด','190','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกชุปแป้งทอด','49','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกนึ่งมะนาว','75','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกผัดฉ่า','260','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกย่าง','37','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาอินทรีย์เค็มทอด','115','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปอเปี๊ยะทอด','158','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปาท่องโก๋','124','1 ตัว','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปีกไก่ทอด','107','1 ชิ้นกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปีกไก่สอดไส้ทอด','103','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปีกไก่อบ','169','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เป็ดตุ๋นมะนาวดอง','110','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เป็ดพะโล้','110','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดกระปลีหมู','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดกุ้งสดสะตอหมู','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดกุ้งหน่อไม้ฝรั่ง','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไข่หน่อไม้','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดคะน้าปลาเค็ม','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไชโป๊วใส่ไข่','125','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดซีอิ๊วเส้นใหญ่หมูใส่ไข่','679','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดดอกกะหล่ำกุ้ง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดดอกกุ่ยช่ายตับ','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดถั่วงอกเต้าหู้','155','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดถั่วลันเตากุ้ง','190','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยกุ้งสด','486','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยกุ้งสดใส่ไข่','545','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยไร้เส้น','182','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยวุ้นเส้นกุ้งสด','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยห่อไข่','565','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดบร็อกโคลี่กุ้ง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดบวบใส่ไข่','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเปรี้ยวหวานไก่','215','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักกระเฉดน้ำมันหอย','185','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักกาดขาวหมูวุ้นเส้น','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักกาดดองใส่ไข่','205','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักคะน้าน้ำมันหอย','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักบุ้ง(ไม่ใส่น้ำมัน)','40','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักบุ้งไฟแดง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักรวมหมู','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเผ็ดปลาดุก','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเผ็ดปลาทอดกรอบ','290','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเผ็ดมะเขือหมู','250','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดพริกแกงหมูหน่อไม้','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดพริกแกงขิงกุ้งกับถั่วฝักยาว','245','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดพริกขิงหมูกับถัวฝักยาว','265','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดฟักทองใส่ไข่','255','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดมะเขือยาวหมูสับ','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดมักกะโรนีกุ้ง','420','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดมักกะโรนีหมู','514','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดยอดมะระน้ำมันหอย','185','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดวุ้นเส้นใส่ไข่','265','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดหมี่ซั่ว','395','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวกะทิทุเรียน','225','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวดำ','205','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวตัด','210','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวนึ่ง','160','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวมูลกะทิ','197','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้าสัขยา','223','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูทอด','440','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูสวรรค์','480','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวกะทิทุเรียน','225','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวดำ','205','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวตัด','210','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวนึ่ง','160','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวมูลกะทิ','197','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้าสัขยา','223','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูทอด','440','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวอบเผือก','385','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวกะทิทุเรียน','225','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวดำ','205','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวตัด','210','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวนึ่ง','160','1 ทัพพี','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวมูลกะทิ','197','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้าสัขยา','223','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหมูทอด','440','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่เค็ม','75','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่เจียว','215','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ดาว','215','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ดาวทรงเครื่อง','250','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ต้ม','73','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ตุ๋น','75','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ตุ๋นทรงเครื่อง','159','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่นกกระทา','191','1 จานเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่พะโล้','180','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ยัดไส้','310','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ลวก','75','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่ลูกเขย','205','1 ฟอง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไข่หงส์','58','1 ใบ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครองแครงกรอบเค็ม','19','1 ตัว','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครัวซอง','235','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครีมซุปไก่','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คอหมูย่าง','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คะน้าหมูกรอบ','420','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เครื่องในไก่ผัดขิง','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แคนตาลูป','4','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แคบหมูมีมัน','13','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แคบหมูไร้มัน','10','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โครงไก่ทอก','656','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เงาะ','12','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แจ่วบอง','25','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โจ๊กใส่ไข่','250','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โจ๊กหมู','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โจ๊กหมูตับใส่ไข่ลวก','230','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เฉาก๊วย','90','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ชมพู่','16','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เชอรี่','15','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาลาเปาทอด','157','1 ลูก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาลาเปาไส้หมู','202','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาหริ่ม','217','1 ชุด','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซาหริ่ม','275','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซุปข้าวโพด','140','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซุปผักใส','15','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ซุปหน่อไม้','40','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มกะทิสายบัวปลาทูนึ่ง','225','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มข่าไก่','210','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มโคล้งไก่ย่าง','115','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มโคล้งปลากรอบ','60','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มจับฉ่าย','15','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มจืดเลือดหมู','120','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มตือฮวน','160','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มผักกาดดองซี่โครงหมู','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำกุ้ง','65','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำไก่','60','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำไก่ใส่เห็ด','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำปลากระป๋อง','55','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำปลากระพง','80','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำทู','10','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำปลาหมึก','9','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มยำเห็ดสด','30','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มส้มปลาทู','130','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ต้มหัวผักกาดขาวซี่โครงหมู','90','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ตะโก้แห้ว','78','1 กระทง','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ตับไก่ปิ้ง','60','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าส่วน','215','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู้แข็ง','210','1 ก้อน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู้ไข่','70','1 หลอด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู่ทอด','57','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าหู้นมสด','150','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เต้าฮวยน้ำขิง','130','1 ถ้วย','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แตงไทย','4','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แตงโม','150','1 กิโลกรัม','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วทอด','82','1 แพ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วลิสงทอด','62','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทองม้วน','35','1 อัน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทอดมันกุ้ง','255','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทอดมันปลากลาย','37','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทับทิมกรอบ','250','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทุเรียน','59','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทุเรียนทอดกรอบ','9','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เทมปุระ','77','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','นมถั่วเหลือง(ไม่มีน้ำตาล)','55','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น่องไก่ทอด','345','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น่องไก่ย่าง','97','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้อยหน่า','61','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำกระเจี๊ยบ','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำโค้ก,น้ำแป๊ปซี่','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำปลามะนาว','165','1 ตัวเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกกะปิผักสด','28','1 ช้อนโต๊ะ','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกกุ้งเผา','90','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกปลาป่น','35','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกมะขามปียก','55','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกมะขามสด','210','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกมะม่วง','100','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกลงเรือ','195','1 ถ้วย','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกหนุ่ม','18','1 ช้อนโต๊ะ','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำพริกอ่อง','80','1 ช้อนโต๊ะ','ของคราว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะเขือเทศ','48','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะตูม','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะนาว','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำมะพร้าว','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำลำใย','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำส้มคั้น','160','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำสับปะรด','125','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำองุ่น','112','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำอ้อย','240','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เนื้อไก่ชุปแป้งทอด','71','1 ชิ้นเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เนื้อน้ำตก','165','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เนื้อผัดหวาน','590','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บรั่นดี','2','1 cc','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะจ่าง','300','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กรอบราดหน้า','515','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กรอบราดหน้าไก่หน่อไม้','660','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กรอบราดหน้ารวมมิตร','690','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กึ่งสำเร็จรูป','253','1 ก้อน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กึ่งสำเร็จรูปผัดกระเพราหมู','540','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่กึ่งสำเร็จรูปผัดขี้เมา','530','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่เกี๊ยวเป็ดย่าง','415','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น่องไก่-น้ำ','375','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำเกี๊ยวหมูแดง','305','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำต้มยำ','300','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำน่องไก่','375','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำเป็ด','370','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่น้ำหมูต้มยำ','300','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บะหมี่หมูแดง','231','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บัวลอย','223','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บัวลอยเผือก','300','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บาร์บีคิวซี่โครงหมูข้าวคลุกเนย','340','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เบียร์ไทย','148','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลากะพงนึ่งมะนาว','155','1 ชิ้นกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาช่อนทอด','1840','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาช่อนอบเกลือ','220','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาชุบขนมปังทอด+สลัดผัก','595','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาซาบะย่าง','220','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาแซลมอนย่าง','260','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาทอดสามรส','470','1 ตัวกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาทูทอด','280','1 ตัวกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลานึ่ง','156','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาเผา','156','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาราดซอสมะนาวมันฝรั่งทอด','560','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาราดพริก','300','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาร้าทรงเครื่อง','155','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาร้าสับ','155','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาเล็กปลาน้อยทอดกรอบ','80','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาสลิดทอด','190','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกชุปแป้งทอด','49','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกนึ่งมะนาว','75','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกผัดฉ่า','260','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาหมึกย่าง','37','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปลาอินทรีย์เค็มทอด','115','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปอเปี๊ยะทอด','158','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปาท่องโก๋','124','1 ตัว','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปีกไก่ทอด','107','1 ชิ้นกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปีกไก่สอดไส้ทอด','103','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ปีกไก่อบ','169','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เป็ดตุ๋นมะนาวดอง','110','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เป็ดพะโล้','110','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดกระปลีหมู','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดกุ้งสดสะตอหมู','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดกุ้งหน่อไม้ฝรั่ง','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไข่หน่อไม้','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดคะน้าปลาเค็ม','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไชโป๊วใส่ไข่','125','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดซีอิ๊วเส้นใหญ่หมูใส่ไข่','679','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดดอกกะหล่ำกุ้ง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดดอกกุ่ยช่ายตับ','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดถั่วงอกเต้าหู้','155','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดถั่วลันเตากุ้ง','190','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยกุ้งสด','486','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยกุ้งสดใส่ไข่','545','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยไร้เส้น','182','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยวุ้นเส้นกุ้งสด','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดไทยห่อไข่','565','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดบร็อกโคลี่กุ้ง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดบวบใส่ไข่','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเปรี้ยวหวานไก่','215','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักกระเฉดน้ำมันหอย','185','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักกาดขาวหมูวุ้นเส้น','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักกาดดองใส่ไข่','205','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักคะน้าน้ำมันหอย','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักบุ้ง(ไม่ใส่น้ำมัน)','40','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักบุ้งไฟแดง','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดผักรวมหมู','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเผ็ดปลาดุก','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเผ็ดปลาทอดกรอบ','290','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดเผ็ดมะเขือหมู','250','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดพริกแกงหมูหน่อไม้','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดพริกแกงขิงกุ้งกับถั่วฝักยาว','245','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดพริกขิงหมูกับถัวฝักยาว','265','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดฟักทองใส่ไข่','255','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดมะเขือยาวหมูสับ','210','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดมักกะโรนีกุ้ง','420','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดมักกะโรนีหมู','514','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดยอดมะระน้ำมันหอย','185','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดวุ้นเส้นใส่ไข่','265','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ผัดหมี่ซั่ว','395','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เผือกทอด','99','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ฝรั่ง','174','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ฝอยทอง','146','1 แพ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เฝอ','240','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พอร์คชอปทอดผักผัดเนย','545','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พะแนงไก่','230','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พะโล้','210','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พุทรา','20','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ฟักตุ๋นไก่มะนาวดอง','125','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ฟักทองแกงบวด','165','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะกอกฝรั่ง','20','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะขามหวาน','20','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะปรางสุก','20','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะเฟือง','120','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะไฟ','4','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะม่วงเขียวเสวย','120','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะม่วงดิบ','110','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะม่วงน้ำดอกไม้','15','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะม่วงสุก','98','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะม่วงอกร่องสุก','15','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะยม','4','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะละกอ','8','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มักกะโรนีขี้เมาไก่','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มักกะโรนีผัดกุ้ง','420','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มังคุด','13','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มันแกงบวด','184','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มันทอด','124','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มันฝรั่งทอด (เฟร้นฟราย)','20','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เม็ดขนุน (ขนมไทย)','35','1 เม็ด','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เมล็ดมะม่วงหิมพานต์ทอด','28','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เมี่ยงก๋วยเตี๋ยว','20','1 คำ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เมี่ยงคำ','30','1 คำ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;


        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยากิโซบะ','400','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำกุนเชียง','220','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำขนมจีน','220','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำไข่ต้ม','105','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำไข่ต้มทรงเครื่อง','188','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำถั่วพู','185','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำเนื้อย่าง','165','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำปลากระป๋อง','55','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำปลาดุกฟู','275','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำปลาหมึกย่าง','99','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำผักกะเฉด','115','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำผักกาดดอง','30','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำผักบุ้งทอดกรอบ','310','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำมะเขือยาว','35','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำมะม่วงทะเล','136','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำมาม่า','215','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำแมงกะพรุน','105','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำรวมมิตรทะเล','132','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำวุ้นเส้น','120','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำไส้กรอก','110','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำหนังหมู','220','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำหมูย่าง','165','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ยำหอยแมลงภู่','60','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เย็นตาโฟ','290','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ระกำ','8','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ราดหน้าบะหมี่กรอบ','515','1จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ราดหน้าบะหมี่กรอบไก่','660','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ราดหน้าปลากะพง','435','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ราดหน้าเส้นใหญ่หมู','405','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ราดหน้าหมูหมี่กรอบ','690','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','รูทเบียร์','105','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โรตีใส่นมข้มและน้ำตาล','192','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โรตีแกงเนื้อ,หมู','675','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลองกอง','6','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลอดช่องน้ำกะทิ','210','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลอดช่องสิงคโปร์','215','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ละมุด','120','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลางสาด','6','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลาบไก่','125','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลาบเนื้อ','119','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลาบปลา','94','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลาบหมู','119','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลำไย','8','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลิ้นจี่','17','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกเกด','4','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกชิ้นไก่','6','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกชิ้นทอด','35','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกชิ้นเนื้อวัว','8','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกชิ้นปลา','4','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกชุบ','57','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกตาลอ่อน','120','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกพลับ','120','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','วุ้นเส้นต้มยำ','245','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','วุ้นเส้นผัดไทยกุ้งสด','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไวตามิลค์','150','1 ขวด','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สตรอว์เบอร์รี่','60','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สเต็กไก่ทอด + มันบด','615','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สเต็กปลาย่าง','260','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สเต็กหมู + ผักสลัด','505','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สปาเก็ตตี้กะเพรากุ้ง','485','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สปาเก็ตตี้ไก่อบ','430','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มเขียวหวาน','32','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มเช้ง','60','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มตำ (ไม่ใส่ถั่ว)','80','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มตำไทย (ไม่ใส่ถั่ว)','55','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มตำปู (ไม่ใส่ถั่ว)','35','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มตำปูไทย (ไม่ใส่ถั่ว)','118','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มแป้น','340','1 กิโลกรัม','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ส้มโอ','60','1 กลีบ','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สละหวาน','60','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดกุ้ง','92','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดกุ้งน้ำใส','92','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดไก่','97','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดแขก','230','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดไข่','123','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดเนื้อสันในทอด','490','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สลัดทูน่า','122','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สับปะรด','8','1 ชิ้นเล็ก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สัมปันนี','21','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สาคูไส้หมู','51','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สาลี่','116','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สาลี่หอม','60','1 ผล','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สุกี้กุ้ง (ไม่ใส่วุ้นเส้น)','200','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สุกี้ไก่ (ไม่ใส่วุ้นเส้น)','200','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สุกี้น้ำไก่','345','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สุกี้หมู (ไม่ใส่วุ้นเส้น)','200','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สุกี้แห้งทะเล','280','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เส้นก๋วยเตี๋ยวผัดขี้เมา','605','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เส้นจันท์ผัดปู','575','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เส้นหมี่ลูกชิ้นน้ำใส','225','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เส้นหมี่ลูกชิ้นหมูแห้ง','430','1 ชาม','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เส้นใหญ่ผัดซีอิ๊วใส่ไข่','520','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไส้กรอกเนื้อลูกวัวอบสลัดผัก','465','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไส้กรอกอีสาน','90','1 ลูก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไส้อั่ว','60','1 คำ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หน่อไม้ผัดไข่','200','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมี่กรอบราดหน้าหมู','690','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมี่กะทิ','405','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมี่ซั่วผัด','395','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูกรอบ','560','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูกะทะ','375','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูคลุกเกล็ดขนมปังทอดซอส','645','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูจุ่ม','375','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูทอดเนยสลัดน้ำใส','635','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูน้ำตก','165','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูบด','50','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูปิ้ง','125','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูผัดขิง','275','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูแผ่น','120','1 แผ่นกลาง','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูฝอย','68','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูยอชุบแป้งทอด','125','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูย่างเกาหลี','375','1 ชุด','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูสะเต๊ะ','115','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หมูหยอง','38','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หลนปูเค็ม','205','1 ถ้วย','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หอยแมลงภู่ทอด','605','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','หอยแมลงภู่อบหม้อดิน','17','1 ตัว','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เห็ดผัดน้ำมันหอย','185','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แหนมสด','175','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','อกไก่ย่าง','110','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','อกไก่อบ','169','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','องุ่น','16','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','องุ่นเปรี้ยว','3','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','องุ่นหวาน','4','1 เม็ด','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','อ้อยควั่น','12','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แอปเปิ้ล','42','1 ลูก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โอวัลติน','220','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ฮะเก๋า','115','1 ลูกเล็ก','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมถ้วย','133','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กระทงทองไส้ข้าวโพด','72','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กระทงทองไส้ไก่','93','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กระยาสารท','210','1 ชิ้นกลาง','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','กล้วยคลุกมะพร้าว','100','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมชั้น','92','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมต้มขาว','41','1 ลูก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปลากริมไข่เต่า','250','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังกระเทียม','85','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังขาไก่','65','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังปอนด์','80','1 แผ่น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังมะพร้าว','235','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังสังขยา','230','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังอบกรอบ','20','1 แผ่นเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังไส้กรอก','130','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมปังไส้หมูหยอง','185','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมหม้อแกง','179','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมไหว้พระจันทร์ไส้ทุเรียนกวน','340','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ขนมไหว้พระจันทร์ไส้ลูกบัวไข่เค็ม','375','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวกุ้งกระเทียมพริกไทย','495','1 จาน','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวหน้ากุ้ง','179','1 ห่อ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ข้าวเหนียวสังขยา','370','1 ห่อ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ครีมเทียม','45','1 ช้อนชา','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คอร์นเฟลค','110','1 ถ้วยตวง','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คุกกี้ข้าวโอ๊ต','75','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คุกกี้ชาเขียว','75','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คุกกี้ช็อคโกแลตชิพ','215','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คุกกี้สิงคโปร์','95','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','้คุกกี้เนย','105','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','คุกกี้ไส้สับปะรด','190','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ชาดำเย็น','110','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ชามะนาว','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ชาร้อน','55','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ชาเย็น','100','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ช็อคโกแลตร้อน','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วลิสงแผ่นทอด','150','1 แผ่น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วลิสงต้ม','45','1 ช้อนโต๊ะ','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ถั่วเขียวต้มน้ำตาล','160','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทองหยิบ','105','1 ดอก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ทุเรียนกวน','115','1 ช้อนโต๊ะ','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำนมข้าวโพด','80','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำตาลทราย','20','1 ช้อนชา','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำฝรั่ง','100','1 กล่อง','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','บราวนี่','340','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำใบเตย','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','น้ำใบบัวบก','120','1 แก้ว','เครื่องดื่ม','3') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พายกรอบ','118','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พายชีสบลูเบอร์รี่','350','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พายทูน่า','280','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พิซซ่าขอบไส้กรอกชีส','340','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พิซซ่าทะเล','335','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พิซซ่าฮาวายเอี้ยน','345','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','พิซซ่าไส้กรอก','290','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ฟักทองเชื่อม','167','1 จาน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะขามหวาน','30','1 ฝัก','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มะม่วงอกร่องสุก','15','1 ชิ้น','ผักผลไม้','4') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มันฝรั่งแผ่น','13','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มันเทศเชื่อม','230','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','มันแกงบวด','184','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','รวมมิตร','230','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกชิ้นปิ้ง','230','1 ไม้','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ลูกตาลลอยแก้ว','180','1 ถ้วย','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','วุ้นมะพร้าวอ่อน','80','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','สังขยา','204','1 ถ้วยเล็ก','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เกี๊ยวกรอบ','78','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เค้กกล้วยหอม','370','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เค้กช็อคโกแลต','275','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เค้กเนย','255','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','เค้กใบเตย','250','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แซนด์วิชแฮมชีส','290','1 คู่','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แซนด์วิชทูน่า','180','1 คู่','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แซนด์วิชไก่','240','1 คู่','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แยมโรล','310','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แฮมเบอร์เกอร์ไก่ชีส','280','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','แฮมเบอร์เกอร์หมู','245','1 ชิ้น','ของคาว','1') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โดนัท','270','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','โรตีสายไหม','145','1 ชิ้น','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไอศกรีมกะทิ','108','1 ก้อน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไอศกรีมกาแฟ','142','1 ก้อน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไอศกรีมช็อคโกแลต','110','1 ก้อน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไอศกรีมวนิลา','140','1 ก้อน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;

        INSERT_DATABASE_FOOD = " INSERT INTO Food (foodNO,foodname,calorie,size,typefoodname,typefoodID) VALUES ('"+PRIMARY_FOOD+"','ไอศกรีมสตรอว์เบอร์รี่','110','1 ก้อน','ของหวาน','2') ";
        db.execSQL(INSERT_DATABASE_FOOD);
        PRIMARY_FOOD++;
    }
    public Database (Context context){
        super(context,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CREATE_DATABASE_ACTIVITY = "CREATE TABLE IF NOT EXISTS Activity (actID INTEGER PRIMARY KEY," +
                "actname TEXT NOT NULL ," +
                "actvolume FLOAT NOT NULL)";

        CREATE_DATABASE_USER = "CREATE TABLE IF NOT EXISTS User (userID INTERGER PRIMARY KEY ," +
                "username TEXT NOT NULL ," +
                "password TEXT NOT NULL," +
                "sex TEXT NOT NULL, " +
                "age TEXT NOT NULL," +
                "loginState TEXT," +
                "height TEXT," +
                "weight TEXT," +
                "actvolume TEXT," +
                "actID INTEGER," +
                "bmi TEXT," +
                "bmr TEXT," +
                "FOREIGN KEY (actID) REFERENCES Activity(actID))";

        CREATE_DATABASE_TYPEFOOD = "CREATE TABLE IF NOT EXISTS Typefood (typefoodID INTERGER PRIMARY KEY ," +
                "typefoodname TEXT NOT NULL)";

        CREATE_DATABASE_FOOD = "CREATE TABLE IF NOT EXISTS Food (foodNO INTERGER PRIMARY KEY ," +
                "foodname TEXT NOT NULL ," +
                "calorie INTEGER NOT NULL," +
                "size TEXT NOT NULL," +
                "incresasState TEXT," +
                "typefoodID INTEGER ," +
                "typefoodname TEXT ," +
                "username TEXT," +
                "FOREIGN KEY (typefoodID) REFERENCES Typefood(typefoodID))";
        CREATE_DATABASE_DATE = "CREATE TABLE IF NOT EXISTS Date (startdate TEXT PRIMARY KEY)";

        CREATE_DATABASE_EAT = "CREATE TABLE IF NOT EXISTS Eat (seq INTEGER ," +
                "userID INTEGER ," +
                "startdate TEXT ,"+
                "foodNO INTEGER ," +
                "todayState TEXT ," +
                "foodname TEXT ," +
                "calorie INTERGER," +
                "FOREIGN KEY (userID) REFERENCES User(userID) ," +
                "FOREIGN KEY (foodNO) REFERENCES Food(foodNO) ," +
                "FOREIGN KEY (startdate) REFERENCES Date(startdate) ," +
                "PRIMARY KEY(seq,userID,startdate))";

        CREATE_DATABASE_FAVORITE = "CREATE TABLE IF NOT EXISTS Favorite (userID INTEGER ," +
                "foodNO INTEGER ," +
                "favorState TEXT ," +
                "FOREIGN KEY (userID) REFERENCES User(userID)," +
                "FOREIGN KEY (foodNO) REFERENCES Food(foodNO)," +
                "PRIMARY KEY(userID,foodNO))";

        CREATE_DATABASE_SPIN = "CREATE TABLE IF NOT EXISTS Spin (seq INTEGER , " +
                "userID INTEGER ," +
                "startdate TEXT," +
                "foodNO INTEGER ," +
                "spineState TEXT ," +
                "foodname TEXT, "+
                "calorie INTERGER," +
                "FOREIGN KEY (userID) REFERENCES User(userID) ," +
                "FOREIGN KEY (foodNO) REFERENCES Food(foodNO) ," +
                "FOREIGN KEY (startdate) REFERENCES Date(startdate) ," +
                "PRIMARY KEY(seq,userID,startdate))";

        db.execSQL(CREATE_DATABASE_DATE);
        db.execSQL(CREATE_DATABASE_USER);
        db.execSQL(CREATE_DATABASE_TYPEFOOD);
        db.execSQL(CREATE_DATABASE_FOOD);
        db.execSQL(CREATE_DATABASE_ACTIVITY);
        db.execSQL(CREATE_DATABASE_EAT);
        db.execSQL(CREATE_DATABASE_FAVORITE);
        db.execSQL(CREATE_DATABASE_SPIN);
    }
    public void delelte(String TABLE_NAME){
        SQLiteDatabase db = getWritableDatabase();
        String str = "DELETE FROM "+TABLE_NAME;
        db.execSQL(str);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onCreate(db);
    }
}
