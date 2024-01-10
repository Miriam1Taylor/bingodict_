// SearchActivity.java
package com.example.bingodict;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText searchEt;
    private ImageView searchIv, flushIv;
    private ListView showlv;
    private List<WordBean> mDatas;
    private List<WordBean> allWordList;
    private InfoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        showActionBar();
        initview();

        // Ensure mDatas is initialized
        mDatas = new ArrayList<>();

        // Always initialize allWordList, even if there is savedInstanceState
        allWordList = SearchUtils.getAllWordLists(this);

        // Initialize the adapter
        adapter = new InfoListAdapter(this, mDatas);

        showlv.setAdapter(adapter);
        // Set listeners
        setListener();

        // Update data list
        updateDataList();
    }

    private void updateDataList() {
        if (adapter != null) {
            mDatas.clear();
            mDatas.addAll(allWordList);

            // Log statement for debugging
            Log.d("SearchActivity", "Data list updated. Size: " + mDatas.size());

            adapter.notifyDataSetChanged();
        } else {
            Log.e("SearchActivity", "Adapter is null. Unable to update data list.");
        }
    }

    private void setListener() {
        showlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WordBean wordBean = mDatas.get(position);
                Intent intent = new Intent(SearchActivity.this, WordDescActivity.class);
                intent.putExtra("word", wordBean);
                startActivity(intent);
            }
        });

        showlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return true;
            }
        });
    }

    private void showDeleteDialog(final int position) {
        final WordBean wordToDelete = mDatas.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.AlertDialogCustom);
        builder.setTitle("删除确认");
        builder.setMessage("您确定要删除单词 '" + wordToDelete.getTitle() + "'吗?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteWord(position);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteWord(int position) {
        WordBean wordToDelete = allWordList.get(position);
        allWordList.remove(position);
        SearchUtils.saveAllWordLists(this, allWordList);
        updateDataList();
        SearchUtils.deleteWordFromList(this, wordToDelete);
    }

    @Override
    protected void onResume() {
        super.onResume();
        allWordList = SearchUtils.getAllWordLists(this);
        updateDataList();
    }

    private void initview() {
        searchEt = findViewById(R.id.info_et_search);
        flushIv = findViewById(R.id.info_iv_flush);
        searchIv = findViewById(R.id.info_iv_search);
        showlv = findViewById(R.id.infolist_lv);
        searchIv.setOnClickListener(this);
        flushIv.setOnClickListener(this);
    }

    private void showActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle("单词搜索");
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
        switch (v.getId()) {
            case R.id.info_iv_flush:
                searchEt.setText("");
                mDatas.clear();
                mDatas.addAll(allWordList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.info_iv_search:
                String msg = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatas.clear();
                List<WordBean> list = new ArrayList<>();
                for (int i = 0; i < allWordList.size(); i++) {
                    String title = allWordList.get(i).getTitle();
                    if (title.startsWith(msg)) {
                        list.add(allWordList.get(i));
                    }
                }
                mDatas.addAll(list);
                adapter.notifyDataSetChanged();
                break;
            case R.id.add_btn_save:
                Intent intent = new Intent(SearchActivity.this, AddWords.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            WordBean newWord = (WordBean) data.getSerializableExtra("newWord");
            if (newWord != null) {
                allWordList = SearchUtils.addWordToList(this, newWord);
                mDatas.clear();
                mDatas.addAll(allWordList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
