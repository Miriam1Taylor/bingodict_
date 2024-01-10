package com.example.bingodict;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    private ListView musicListView;
    private ImageView playerIcon;
    private TextView playerSongName;
    private SeekBar playerSeekBar;
    private MediaPlayer mediaPlayer;
    private ArrayList<String> songList;
    private int currentSongIndex = 0;
    private Handler handler;
    private boolean isPlaying = false;
    private int pausedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        // 初始化UI组件
        musicListView = findViewById(R.id.musicListView);
        playerIcon = findViewById(R.id.playerIcon);
        playerSeekBar = findViewById(R.id.playerSeekBar);
        playerSongName = findViewById(R.id.playerSongName);

        // 初始化音乐列表
        songList = new ArrayList<>();
        songList.add("Amazing Grace - Hayley Westenra");
        songList.add("Diamonds - Rihanna");
        songList.add("Enchanted - Taylor Swift");
        songList.add("How Far I'll Go - Auli'i Cravalho");
        songList.add("Lavender Haze - Taylor Swift");
        songList.add("Love Story - Taylor Swift");
        songList.add("Me! - Taylor Swift");
        songList.add("One Last Time - Ariana Grande");
        songList.add("Payphone - Maroon 5");
        songList.add("See You Again - Wiz Khalifa ft. Charlie Puth");
        songList.add("Someone Like You - Adele");
        songList.add("Umbrella - Rihanna");
        songList.add("When Will My Life Begin - Mandy Moore");

        // 设置初始文本
        playerSongName.setText(songList.get(currentSongIndex));

        // 初始化音乐播放器
        mediaPlayer = MediaPlayer.create(this, R.raw.love_story); // 示例音乐文件名为love_story.mp3
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // 播放完成时的处理，例如切换到下一首
                playNextSong();
            }
        });


        // 添加更多歌曲

        // Set up the ListView and custom adapter
        ListView musicListView = findViewById(R.id.musicListView);
        CustomAdapter adapter = new CustomAdapter(this, songList);
        musicListView.setAdapter(adapter);

        // 音乐列表点击事件
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSongIndex = position;
                playSelectedSong();
            }
        });

        // 初始化播放控制条
        initPlayerSeekBar();

        // 初始化Handler，用于更新UI
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                updateSeekBar();
                return true;
            }
        });
    }

    private void initPlayerSeekBar() {
        playerSeekBar.setMax(mediaPlayer.getDuration());

        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // 启动更新SeekBar的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void updateSeekBar() {
        playerSeekBar.setProgress(mediaPlayer.getCurrentPosition());
    }

    private void playSelectedSong() {
        mediaPlayer.stop();
        mediaPlayer.release(); // Release resources before creating a new instance
        mediaPlayer = MediaPlayer.create(this, getSongResourceId(currentSongIndex));

        if (isPlaying) {
            // If it was playing before, resume from the paused position
            mediaPlayer.seekTo(pausedPosition);
            mediaPlayer.start();
        } else {
            // Otherwise, start playing from the beginning
            mediaPlayer.start();
        }

        // Update the TextView with the current song name and artist
        playerSongName.setText(songList.get(currentSongIndex));
        playerSeekBar.setMax(mediaPlayer.getDuration());
        isPlaying = true;
    }

    private void playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % songList.size();
        playSelectedSong();
    }

    private void pauseSong() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pausedPosition = mediaPlayer.getCurrentPosition();
        }
        isPlaying = false;
    }

    // Toggles between play and pause
    public void togglePlayPause(View view) {
        if (isPlaying) {
            pauseSong();
        } else {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    pauseSong();
                } else {
                    // If it's not playing, resume from the paused position
                    mediaPlayer.seekTo(pausedPosition);
                    mediaPlayer.start();
                    isPlaying = true;
                }
            } else {
                playSelectedSong();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private int getSongResourceId(int index) {
        switch (index) {
            case 0:
                return R.raw.amazing_grace;
            case 1:
                return R.raw.diamonds;
            case 2:
                return R.raw.enchanted;
            case 3:
                return R.raw.how_far_ill_go;
            case 4:
                return R.raw.lavender_haze;
            case 5:
                return R.raw.love_story;
            case 6:
                return R.raw.me;
            case 7:
                return R.raw.one_last_time;
            case 8:
                return R.raw.payphone;
            case 9:
                return R.raw.see_you_again;
            case 10:
                return R.raw.someone_like_you;
            case 11:
                return R.raw.umbrella;
            case 12:
                return R.raw.when_will_my_life_begin;
            default:
                return 0;
        }
    }
}
