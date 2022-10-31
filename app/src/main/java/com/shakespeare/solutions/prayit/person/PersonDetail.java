package com.shakespeare.solutions.prayit.person;

import static com.shakespeare.solutions.prayit.MainActivity.INTENT_PERSON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shakespeare.solutions.prayit.R;

public class PersonDetail extends AppCompatActivity {

    private TextView personNameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        // Action Bar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        Intent intent = getIntent();

        Person person = (Person)intent.getSerializableExtra(INTENT_PERSON);
        personNameView = findViewById(R.id.person_name);
        personNameView.setText(person.getName());

    }
}