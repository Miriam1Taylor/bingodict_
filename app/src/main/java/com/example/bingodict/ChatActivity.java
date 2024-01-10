package com.example.bingodict;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initMsgs(); // Initialize message data

        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入框中的文本内容
                String content = inputText.getText().toString();

                // 检查内容是否非空
                if (!"".equals(content)) {
                    // 将用户的消息添加到聊天列表
                    Msg userMsg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(userMsg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");

                    // 检查用户的消息是否是一个问题
                    if (content.toLowerCase().contains("how are you")) {
                        // 回答问题
                        Msg botMsg = new Msg("I'm doing well, thank you!", Msg.TYPE_RECEIVED);
                        msgList.add(botMsg);
                        adapter.notifyItemInserted(msgList.size() - 1);
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    } else if (content.toLowerCase().contains("hello")) {
                        // 回答问题
                        Msg botMsg = new Msg("Hi, what a beautiful day!", Msg.TYPE_RECEIVED);
                        msgList.add(botMsg);
                        adapter.notifyItemInserted(msgList.size() - 1);
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    } else {
                        // 如果未检测到特定问题，可以有一个默认回复
                        Msg botMsg = new Msg("I didn't understand that. How may I assist you?", Msg.TYPE_RECEIVED);
                        msgList.add(botMsg);
                        adapter.notifyItemInserted(msgList.size() - 1);
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    }
                }
            }
        });


        showActionBar();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Customer Service");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class Msg {
        public static final int TYPE_RECEIVED = 0;
        public static final int TYPE_SENT = 1;
        private String content;
        private int type;

        public Msg(String content, int type) {
            this.content = content;
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public int getType() {
            return type;
        }
    }

    private void initMsgs() {
        Msg msg1 = new Msg("你好！", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("欢迎使用Bingo English！", Msg.TYPE_RECEIVED);
        msgList.add(msg2);
        Msg msg3 = new Msg("我是外教Mary，nice to meet you！", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
