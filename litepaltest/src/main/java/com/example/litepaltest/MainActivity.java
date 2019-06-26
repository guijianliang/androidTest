package com.example.litepaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库
        Button createDatebase = findViewById(R.id.create_sqlite);
        createDatebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connector.getDatabase();
            }
        });

        //插入数据
        Button insertDatabase = findViewById(R.id.add_data);
        insertDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setAuthor("桂建亮");
                book.setName("建国大业");
                book.setPages(333);
                book.setPress("清华大学出版社");
                book.setPrice(55.90);
                book.save();
            }
        });

        //更新数据
        Button updataData = findViewById(R.id.updata_data);
        updataData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setPrice(999);
                book.setPress("工业出版社");
                book.updateAll("id = ? and author = ?", "1", "桂建亮");
            }
        });

        //删除数据
        Button deleteData = findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Book.class, "price < ?", "56");
            }
        });

        //查找数据
        Button queryData = findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> books = DataSupport.findAll(Book.class);
                for (Book book:books){
                    Log.i("book", book.getAuthor());
                    Log.i("book", book.getName());
                    Log.i("book", book.getPress());
                    Log.i("book", ""+book.getPages());
                }
            }
        });

    }
}
