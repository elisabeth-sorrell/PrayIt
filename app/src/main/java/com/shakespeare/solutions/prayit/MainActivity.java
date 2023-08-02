package com.shakespeare.solutions.prayit;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shakespeare.solutions.prayit.person.NewPersonActivity;
import com.shakespeare.solutions.prayit.person.Person;
import com.shakespeare.solutions.prayit.person.PersonDetail;
import com.shakespeare.solutions.prayit.person.PersonListAdapter;
import com.shakespeare.solutions.prayit.person.PersonViewModel;
import com.shakespeare.solutions.prayit.util.ItemClickSupport;
import com.shakespeare.solutions.prayit.util.SwipeToDeleteCallback;

import java.util.concurrent.ExecutionException;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    private PersonViewModel personViewModel;
    private final String APP_NAME="Harvest Helper";
    public static final String INTENT_PERSON = "person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action Bar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);


        // List persons
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PersonListAdapter adapter = new PersonListAdapter(new PersonListAdapter.PersonDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Person person = adapter.getCurrentList().get(position);
                Intent intent = new Intent(MainActivity.this, PersonDetail.class);
                intent.putExtra(INTENT_PERSON, person.getId());
                MainActivity.this.startActivity(intent);
            }
        });

        // Delete a person
        enableSwipeToDeleteAndUndo(adapter, recyclerView);

        // Observe and update cache
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        personViewModel.getAllPersons().observe(this, persons -> {
            adapter.submitList(persons);
        });

        // Go to different activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewPersonActivity.class);
            newPersonActivityResultLauncher.launch(intent);
        });


    }


    // delete
    private void enableSwipeToDeleteAndUndo(PersonListAdapter adapter, RecyclerView recyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                Person person = adapter.getCurrentList().get(position);
                personViewModel.delete(person);

                Snackbar snackbar = Snackbar.make(recyclerView, "Item was removed from list", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO not sure if this is a good idea...
                        //       in the UI it won't make a difference,
                        //       but it had been really deleted, and now really added in the db.
                        try {
                            personViewModel.insert(person);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Result launchers
    private ActivityResultLauncher<Intent> newPersonActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Person person = new Person(result.getData().getStringExtra(NewPersonActivity.EXTRA_REPLY));
                        try {
                            personViewModel.insert(person);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(result.getResultCode());
                        Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
}