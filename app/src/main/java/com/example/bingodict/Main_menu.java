package com.example.bingodict;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import readinggrid.reading_grid_Activity;

public class Main_menu extends AppCompatActivity implements View.OnClickListener {
    private ImageView block1;
    private ImageView block2;
    private ImageView block3;
    private ImageView block4;
    private LinearLayout music;
    private LinearLayout movie;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 通过 getMenuInflater() 方法获取菜单填充器，并通过 inflate 方法将菜单资源文件（R.menu）填充到 Menu 对象中
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.firstFragment:
                // Handle click on "单词翻译" menu item
                Intent translationIntent = new Intent(Main_menu.this, Translation.class);
                startActivity(translationIntent);
                return true;
            case R.id.secondFragment:
                // Handle click on "好词记录" menu item
                Intent addWordsIntent = new Intent(Main_menu.this, AddWords.class);
                startActivity(addWordsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initWidget() {
        block1 = findViewById(R.id.block_1);
        block2 = findViewById(R.id.block_2);
        block3 = findViewById(R.id.block_3);
        block4 = findViewById(R.id.block_4);
        music = findViewById(R.id.musiclearn);
        movie = findViewById(R.id.movielearn);
        block1.setOnClickListener(this);
        block2.setOnClickListener(this);
        block3.setOnClickListener(this);
        block4.setOnClickListener(this);
        music.setOnClickListener(this);
        movie.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initWidget();

        // Retrieve the username from the Intent
        String username = getIntent().getStringExtra("username");

        // Display welcome message
        if (username != null) {
            Toast.makeText(this, username + "，欢迎您！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.block_1:
                Intent intent = new Intent(Main_menu.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.block_2:
                Intent intent2 = new Intent(Main_menu.this, reading_grid_Activity.class);
                startActivity(intent2);
                break;
            case R.id.block_3:
                Intent intent3 = new Intent(Main_menu.this, ChatActivity.class);
                startActivity(intent3);
                break;
            case R.id.block_4:
                Intent intent4 = new Intent(Main_menu.this, AboutActivity.class);
                startActivity(intent4);
                break;
            case R.id.musiclearn:
                Intent intent5 = new Intent(Main_menu.this, MusicActivity.class);
                startActivity(intent5);
                break;
            case R.id.movielearn:
                Intent intent6 = new Intent(Main_menu.this, MovieActivity.class);
                startActivity(intent6);
                break;
        }
    }
}
