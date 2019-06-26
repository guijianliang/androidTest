package com.example.fileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et_myedit);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        File file = MainActivity.this.getFilesDir();
//        String fileName1 = file.getAbsolutePath() ;
//        File[] files=file.listFiles();
//
////        String fileName = file.getAbsolutePath() + File.separator + "hehe.txt";
////        Log.i("filename:  ",fileName);
        String inputText = editText.getText().toString();
        save(inputText);
        try {
            read("log1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void read(String s) throws IOException {
        String result = "";
        FileInputStream fis = openFileInput(s);
        int length = fis.available();
        byte[] buffer = new byte[length];

        fis.read(buffer);

        result = new String(buffer,"UTF-8");
        Log.i("readddddd", result);

    }

    private void save(String inputText) {
        FileOutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;

        try {
            File file = getFilesDir();
            Log.i("heheh", file.getPath());
            outputStream = this.openFileOutput("log1.txt",Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(inputText);
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
