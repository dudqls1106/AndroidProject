package com.cookandroid.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editTextNewID, editTextNewPassword;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        editTextNewID = findViewById(R.id.editTextNewID);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newID = editTextNewID.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();

                if (!newID.isEmpty() && !newPassword.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", newID);
                    resultIntent.putExtra("password", newPassword);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
