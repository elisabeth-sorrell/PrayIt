package com.shakespeare.solutions.prayit.person;

import static com.shakespeare.solutions.prayit.MainActivity.INTENT_PERSON;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shakespeare.solutions.prayit.R;

import java.util.concurrent.ExecutionException;

public class PersonDetail extends AppCompatActivity {

    private TextView personNameView;
    private TextInputEditText nameText;
    private PersonViewModel personViewModel;
    private Person personDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        // Action Bar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        Intent intent = getIntent();
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);


        // Name below the profile picture
        int personId = intent.getIntExtra(INTENT_PERSON, 0);

        personNameView = findViewById(R.id.person_name);
        nameText = findViewById(R.id.name_text_input);

        try {
            personViewModel.getPersonById(personId).observe(this, person -> {
                personDetail = person;
                personNameView.setText(personDetail.getName());
                nameText.setText(personDetail.getName());
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nameText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            System.out.println("tapped" + actionId);
            String newName = nameText.getText().toString();

            if(actionId == EditorInfo.IME_ACTION_DONE) {
                System.out.println("user clicked ime action not specified");
                updatePerson(newName);
                handled = true;
            }
            return handled;
        });



    }

    private void updatePerson(String newName) {
        if(checkInput(newName)) {
            personDetail.setName(newName);
            personViewModel.update(personDetail);
        }
    }

    private boolean checkInput(String name) {
        return !(TextUtils.isEmpty(name));
    }
}