package com.shakespeare.solutions.prayit.person;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository mRepository;
    private final LiveData<List<Person>> mAllPersons;

    public PersonViewModel (Application application) {
        super(application);
        mRepository = new PersonRepository(application);
        mAllPersons = mRepository.getAllPersons();
    }

    public LiveData<List<Person>> getAllPersons() {return mAllPersons;}

    public void insert(Person person){mRepository.insert(person);}

    public void delete(Person person){mRepository.delete(person);}
}
