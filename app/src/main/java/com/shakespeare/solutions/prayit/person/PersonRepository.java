package com.shakespeare.solutions.prayit.person;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.shakespeare.solutions.prayit.util.db.PrayItDatabase;

import java.util.List;

class PersonRepository {
    private PersonDAO mPersonDao;
    private LiveData<List<Person>> mAllPersons;

    PersonRepository(Application application) {
        PrayItDatabase db = PrayItDatabase.getDatabase(application);
        mPersonDao = db.personDao();
        mAllPersons = mPersonDao.getAlphabetizedPersons();
    }

    LiveData<List<Person>> getAllPersons() {
        return mAllPersons;
    }

    void insert(Person person) {
        PrayItDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.insert(person);
        });
    }

    void delete(Person person) {
        PrayItDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.deletePerson(person);
        });
    }
}
