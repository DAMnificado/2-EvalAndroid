package com.example.a56almacenamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String NAME="NAME";
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.leer);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME,null);
        if (name==null) {
            textView.setText("Hello");
        } else {
            textView.setText("Welcome back " + name + "!");
        }
        mEditTextName = findViewById(R.id.escribir);

        Button botonGuardar = findViewById(R.id.guardar);
        botonGuardar.setOnClickListener(view -> saveName(view));
    }

    public void saveName(View view) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString(NAME, mEditTextName.getText().toString());
        editor.commit();
    }


}