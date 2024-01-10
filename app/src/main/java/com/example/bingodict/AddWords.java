package com.example.bingodict;
// AddWords.java

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AddWords extends AppCompatActivity implements View.OnClickListener {

    EditText addEtWord, addEtTrans, addEtSummary;
    Button addBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        showActionBar();
        initView();
    }

    private void initView() {
        addEtWord = findViewById(R.id.add_et_word);
        addEtTrans = findViewById(R.id.add_et_trans);
        addEtSummary = findViewById(R.id.add_et_summary);
        addBtnSave = findViewById(R.id.add_btn_save);
        addBtnSave.setOnClickListener(this);
    }

    private void showActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("添加单词");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_btn_save) {
            saveWord();
        }
    }

    private void saveWord() {
        String word = addEtWord.getText().toString().trim();
        String trans = addEtTrans.getText().toString().trim();
        String summary = addEtSummary.getText().toString().trim();

        if (TextUtils.isEmpty(word) || TextUtils.isEmpty(trans) || TextUtils.isEmpty(summary)) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        WordBean wordBean = new WordBean(word, trans, summary);
        List<WordBean> allWordList = SearchUtils.getAllWordLists(this); // Pass the context
        allWordList.add(wordBean);
        SearchUtils.saveAllWordLists(this, allWordList); // Pass the context

        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        clearEditText();
    }

    private void clearEditText() {
        addEtWord.setText("");
        addEtTrans.setText("");
        addEtSummary.setText("");
    }
}
