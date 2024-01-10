package com.example.bingodict;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    private MySQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MySQLiteOpenHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {MySQLiteOpenHelper.COLUMN_ID, MySQLiteOpenHelper.COLUMN_USERNAME};
        String selection = MySQLiteOpenHelper.COLUMN_USERNAME + "=? AND " + MySQLiteOpenHelper.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                MySQLiteOpenHelper.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst() && (cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_USERNAME)>=0)) {
            // Login successful
            String usernameFromDB = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_USERNAME));

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, Main_menu.class);
            intent.putExtra("username", usernameFromDB);
            startActivity(intent);
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}
