package com.example.a59almacenamiento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;


import android.annotation.SuppressLint;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText editfichero;

    EditText mEditText;

    private final String[] PERMISSIONS_STORAGE = {

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        editfichero = (EditText) findViewById(R.id.fichero);

        Button buttonGuardar = findViewById(R.id.buttonWrite);
        buttonGuardar.setOnClickListener(view -> writeFile(view));

        Button buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(view -> readFile(view));


    }


    public boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 101);
        }

    }

    public void writeFile(View view) {
        if (isExternalStorageWritable()) {
            checkStoragePermission();

            try {
                String fileName = "Documents/"+editfichero.getText().toString();
                File textFile = new File(Environment.getExternalStorageDirectory(),
                        fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write(mEditText.getText().toString().getBytes());
                fileOutputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error writing file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot write to External Storage",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void readFile(View view) {
        if (isExternalStorageReadable()) {
            checkStoragePermission();
            StringBuilder stringBuilder = new StringBuilder();
            try {

                String fileName = "Documents/"+editfichero.getText().toString();
                File textFile = new File(Environment.getExternalStorageDirectory(),
                        fileName);
                FileInputStream fileInputStream = new FileInputStream(textFile);
                if (fileInputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String newLine = null;

                    while ((newLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(newLine + "\n");
                    }
                    fileInputStream.close();
                }
                mEditText.setText(stringBuilder);
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot read External Storage",
                    Toast.LENGTH_LONG).show();
        }

    }
}