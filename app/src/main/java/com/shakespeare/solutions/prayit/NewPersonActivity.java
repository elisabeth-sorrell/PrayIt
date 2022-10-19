package com.shakespeare.solutions.prayit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class NewPersonActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.shakespeare.solutions.prayit.personlistsql.REPLY";

    private EditText editPersonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);
        editPersonView = findViewById(R.id.edit_person);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if(TextUtils.isEmpty(editPersonView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String person = editPersonView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, person);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}