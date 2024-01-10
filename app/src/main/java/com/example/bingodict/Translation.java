package com.example.bingodict;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Translation extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        editText = findViewById(R.id.et_word);
        textView = findViewById(R.id.tv_translation);

        // Initialize Database Helper
        dbHelper = new DatabaseHelper(this);

        // Copy the database from assets to the app's data directory
        try {
            dbHelper.copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Open the copied database
        database = dbHelper.getWritableDatabase();
    }

    public void query(View view) {
        String word = editText.getText().toString();
        String translation = queryWord(word);
        textView.setText(translation);
    }

    private String queryWord(String word) {
        String[] columns = {"translation"};
        String selection = "word=?";
        String[] selectionArgs = {word};

        Cursor cursor = database.query("EnWords", columns, selection, selectionArgs, null, null, null);

        String translation = "";
        if (cursor.moveToFirst()) {
            int translationColumnIndex = cursor.getColumnIndex("translation");
            if (translationColumnIndex != -1) {
                translation = cursor.getString(translationColumnIndex);
            } else {
                translation = "Translation not available";
            }
        } else {
            translation = "Translation not available";
        }

        cursor.close();

        return translation;
    }
}
