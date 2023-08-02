package com.shakespeare.solutions.prayit.person;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository mRepository;
    private final LiveData<List<Person>> mAllPersons;

    public PersonViewModel (Application application) {
        super(application);
        mRepository = new PersonRepository(application);
        mAllPersons = mRepository.getAllPersons();
    }

    public LiveData<List<Person>> getAllPersons() {return mAllPersons;}

    public long insert(Person person) throws ExecutionException, InterruptedException {return mRepository.insert(person);}

    public void delete(Person person){mRepository.delete(person);}

    public void update(Person person) {mRepository.update(person);}

    public LiveData<Person> getPersonById(int id) throws ExecutionException, InterruptedException {return mRepository.getPersonById(id);}
}
