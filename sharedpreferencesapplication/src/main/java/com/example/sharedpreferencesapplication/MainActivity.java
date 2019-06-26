package com.example.sharedpreferencesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    private Button savaData;
    private Button restoreData;
    private Button createSqlite;
    private Button addData;
    private Button updataData;
    private Button deleteData;
    private Button queryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savaData = findViewById(R.id.save_data);
        savaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("姓名", "桂建亮");
                editor.putInt("年龄", 28);
                editor.putBoolean("married", false);
                editor.apply();
            }
        });

        //读取数据
        restoreData = findViewById(R.id.restore_data);
        restoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("姓名", "");
                int age = pref.getInt("年龄", 0);
                Boolean married = pref.getBoolean("married", false);

                Log.i("姓名", name);
                Log.i("年龄", "" + age);
                Log.i("married", "" + married);
            }
        });

        //创建数据库
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        createSqlite = findViewById(R.id.create_sqlite);
        createSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });


        //插入数据
        addData = findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据;
                values.put("name","三国演义");
                values.put("author","施耐庵");
                values.put("pages",569);
                values.put("price",89.4);
                db.insert("Book",null,values);
                values.clear();

                //开始组装第二条数据;
                values.put("name","水浒传");
                values.put("author","忘记了");
                values.put("pages",549);
                values.put("price",45.4);
                db.insert("Book",null,values);

            }
        });

        //更新数据
        updataData = findViewById(R.id.updata_data);
        updataData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.00);
                db.update("Book",values,"name=?",new String[]{"三国演义"});
            }
        });

        //删除数据
        deleteData = findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book","pages>?",new String[]{"549"});
            }
        });

        //查询数据
        queryData = findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){  //移动到第一行;
                    do {
                        //author text,price real,pages integer,name text
                        String author  = cursor.getString(cursor.getColumnIndex("author"));
                        String text = cursor.getString(cursor.getColumnIndex("name"));
                        Integer pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.i("log:", author);
                        Log.i("log:", text);
                        Log.i("log:", ""+pages);
                        Log.i("log:",""+ price);

                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });


    }
}
