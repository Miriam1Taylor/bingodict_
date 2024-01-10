package com.example.bingodict;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegisterUsername, etRegisterPassword;
    private Button btnRegister;

    private MySQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new MySQLiteOpenHelper(this);

        etRegisterUsername = findViewById(R.id.etRegisterUsername);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String username = etRegisterUsername.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MySQLiteOpenHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteOpenHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(MySQLiteOpenHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            // Registration successful
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish(); // Close the registration activity
        } else {
            // Registration failed
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
