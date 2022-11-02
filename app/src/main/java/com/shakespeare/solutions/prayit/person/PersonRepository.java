package com.shakespeare.solutions.prayit.person;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.shakespeare.solutions.prayit.util.db.PrayItDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.Completable;

class PersonRepository  {
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

    void update(Person person) {
        PrayItDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.updatePerson(person);
        });
    }

    void delete(Person person) {
        PrayItDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.deletePerson(person);
        });
    }


    LiveData<Person> getPersonById(int id) throws ExecutionException, InterruptedException {
        Future<LiveData<Person>> gettingPerson = PrayItDatabase.databaseWriteExecutor.submit(() -> mPersonDao.getPersonById(id));
        return gettingPerson.get();
    }

}
