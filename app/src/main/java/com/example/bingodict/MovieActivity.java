package com.example.bingodict;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {
    VideoView videoView1;
    VideoView videoView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie); // 设置XML布局文件

        // 获取布局中的VideoView和Button
        videoView1 = findViewById(R.id.videoView1);
        videoView2 = findViewById(R.id.videoView2);

        Button btnPlay1 = findViewById(R.id.btnPlay1);
        Button btnPlay2 = findViewById(R.id.btnPlay2);

        // 设置点击事件
        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo1();
            }
        });

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo2();
            }
        });

        // 设置MediaController
        MediaController controller1 = new MediaController(this);
        videoView1.setMediaController(controller1);

        MediaController controller2 = new MediaController(this);
        videoView2.setMediaController(controller2);
    }

    private void playVideo1() {
        String videoFileName = "movie1";
        playVideo(videoFileName, videoView1);
    }

    private void playVideo2() {
        String videoFileName = "movie2";
        playVideo(videoFileName, videoView2);
    }

    private void playVideo(String videoFileName, VideoView videoView) {
        int resId = getResources().getIdentifier(videoFileName, "raw", getPackageName());

        if (resId != 0) {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resId);
            videoView.setVideoURI(uri);
            videoView.start();
        } else {
            Log.e("movietest", "Invalid resource ID for the video file");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView1.suspend();
        videoView2.suspend();
    }
}
