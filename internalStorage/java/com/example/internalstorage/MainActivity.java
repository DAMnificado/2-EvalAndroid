package com.example.internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText editfichero;
    private final String FILENAME="testfile.txt";
    EditText mEditText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText)findViewById(R.id.editText);
        editfichero = (EditText)findViewById(R.id.fichero);

        Button buttonGuardar=findViewById(R.id.buttonWrite);
        buttonGuardar.setOnClickListener(view -> writeFile(view));

        Button buttonRead=findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(view -> readFile(view));

    }


    public void readFile(View view) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            
            InputStream inputStream = openFileInput(String.valueOf(editfichero.getText().toString()));
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newLine = null;
                while ((newLine = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(newLine+"\n");
                }
                inputStream.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        mEditText.setText(stringBuilder);
    }

    public void writeFile(View view) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(editfichero.getText().toString(),
                    Context.MODE_PRIVATE);
            fileOutputStream.write(mEditText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }






}
