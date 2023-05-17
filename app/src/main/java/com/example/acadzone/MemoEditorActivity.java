package com.example.acadzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MemoEditorActivity extends AppCompatActivity {

    int memoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_editor);

        EditText editText= (EditText) findViewById(R.id.editText);
        Intent intent= getIntent();
        memoId= intent.getIntExtra("memoId",-1);

        if(memoId != -1){
            editText.setText(memoFragment.memo.get(memoId));
        }
        else{
            memoFragment.memo.add("");
            memoId = memoFragment.memo.size() -1;
            memoFragment.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                memoFragment.memo.set(memoId, String.valueOf(charSequence));
                memoFragment.arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}