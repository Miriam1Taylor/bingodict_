package com.example.bingodict;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager aboutVp;
    TextView shareTv;
    TextView feedbackTv;
    LinearLayout pointLayout;
    List<View> viewList;         //ViewPager的数据源
    private AboutAdapter adpter;

    int[] picIds={
            R.mipmap.i10,R.mipmap.i3,R.mipmap.i6,R.mipmap.i7,R.mipmap.i8 };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                //接受到消息之后，viewpager向下滑动一页
                int currentItem = aboutVp.getCurrentItem();//获取当前的页面
                aboutVp.setCurrentItem(currentItem + 1); //设置显示下一页
                handler.sendEmptyMessageDelayed(1,5000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutVp = findViewById(R.id.about_vp);
        shareTv = findViewById(R.id.about_tv_share);
        feedbackTv = findViewById(R.id.about_tv_feedback);
        feedbackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf = new Intent(AboutActivity.this,FeedbackActivity.class);
                startActivity(intentf);
            }
        });
        pointLayout = findViewById(R.id.about_layout_point);
        shareTv.setOnClickListener(this);
        viewList = new ArrayList<>();
        showactionbar();
        //初始化ViewPager页面信息
        for (int i = 0; i < picIds.length; i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_abouttv,null);
            ImageView iv = view.findViewById(R.id.item_aboutvp_iv);
            iv.setImageResource(picIds[i]);
            viewList.add(view);
        }
        //创建适配器对象
        adpter = new AboutAdapter(viewList);
        //设置适配器
        aboutVp.setAdapter(adpter);
        //发送切换页面消息
        handler.sendEmptyMessageDelayed(1,5000);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "和我一起遨游在英语的海洋中，发现新的自己!";
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        // 启动系统分享对话框，让用户选择分享方式
        startActivity(Intent.createChooser(intent,"软件分享"));
    }

//    @Override
//    public void onClick(View v) {
//        // 创建一个发送意图
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        // 设置发送的数据类型为应用程序文件
//        intent.setType("application/vnd.android.package-archive");
//        // 获取应用程序安装包的路径
//        String apkPath = "D:/Desktop/lukedict-master/app/release/app-release.apk";
//        // 创建一个文件URI，并将安装包路径添加到意图中
//        Uri fileUri = Uri.parse("file://" + apkPath);
//        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
//        // 启动系统分享对话框，让用户选择分享方式
//        startActivity(Intent.createChooser(intent, "分享应用"));
//    }
    private void showactionbar() {
        ActionBar actionBar = this.getSupportActionBar();//定义actionbar上的返回箭头
        actionBar.setTitle("关于我们");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {//定义actionbar上的返回箭头
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}