package com.cookandroid.androidproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextMemoName;
    private EditText editTextMemo;
    private Spinner spinnerMemoList;
    private Button buttonSave;
    private Button buttonLoad;
    private Button buttonClear;
    private Button buttonClearFields;
    private Button buttonBack;
    private TextView textViewCurrentMemoName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextMemoName = findViewById(R.id.editTextMemoName);
        editTextMemo = findViewById(R.id.editTextMemo);
        spinnerMemoList = findViewById(R.id.spinnerMemoList);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);
        buttonClear = findViewById(R.id.buttonClear);
        buttonBack = findViewById(R.id.buttonBack);
        buttonClearFields = findViewById(R.id.buttonClearFields);

        sharedPreferences = getSharedPreferences("memo_pref", MODE_PRIVATE);

        // 저장 버튼 클릭 시
        buttonSave.setOnClickListener(v -> saveMemo());

        // 불러오기 버튼 클릭 시
        buttonLoad.setOnClickListener(v -> loadMemo());

        // 지우기 버튼 클릭 시
        buttonClear.setOnClickListener(v -> clearMemo());

        // 초기화 버튼 클릭 시
        buttonClearFields.setOnClickListener(v -> clearFields());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 저장된 메모 이름들을 Spinner에 설정
        setupSpinner();
    }

    private void saveMemo() {
        String memoName = editTextMemoName.getText().toString().trim();
        String memoText = editTextMemo.getText().toString().trim();

        // 메모 이름과 내용이 비어있지 않으면 저장
        if (!memoName.isEmpty() && !memoText.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(memoName, memoText);
            editor.apply(); // 비동기적으로 저장
            Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();

            // 저장 후 다시 Spinner 업데이트
            setupSpinner();
        } else {
            Toast.makeText(this, "메모 제목과 내용을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMemo() {
        String selectedMemoName = spinnerMemoList.getSelectedItem().toString();
        String memo = sharedPreferences.getString(selectedMemoName, "");

        // 선택된 메모 이름에 해당하는 메모 내용을 화면에 표시
        editTextMemoName.setText(selectedMemoName);
        editTextMemo.setText(memo);
    }

    private void clearMemo() {
        String selectedMemoName = spinnerMemoList.getSelectedItem().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(selectedMemoName);
        editor.apply();
        editTextMemoName.setText("");
        editTextMemo.setText("");
        Toast.makeText(this, "메모가 지워졌습니다.", Toast.LENGTH_SHORT).show();

        // 삭제 후 다시 Spinner 업데이트
        setupSpinner();
    }

    private void clearFields() {
        editTextMemoName.setText("");
        editTextMemo.setText("");
    }

    private void setupSpinner() {
        // 저장된 메모 이름들을 Spinner에 설정하기 위한 리스트 생성
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<String> memoNames = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (!entry.getKey().equals("memo_pref")) { // SharedPreferences 키 외의 값만 가져오기
                memoNames.add(entry.getKey());
            }
        }

        // Spinner에 어댑터 설정
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, memoNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemoList.setAdapter(spinnerAdapter);
    }
}

