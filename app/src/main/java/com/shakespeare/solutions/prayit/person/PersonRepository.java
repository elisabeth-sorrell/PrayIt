package com.shakespeare.solutions.prayit.person;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.shakespeare.solutions.prayit.util.db.PrayItDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

class PersonRepository  {
    private PersonDAO mPersonDao;
    private LiveData<List<Person>> mAllPersons;
    private ExecutorService executorService;

    PersonRepository(Application application) {
        PrayItDatabase db = PrayItDatabase.getDatabase(application);
        mPersonDao = db.personDao();
        mAllPersons = mPersonDao.getAlphabetizedPersons();
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
    }

    LiveData<List<Person>> getAllPersons() {
        return mAllPersons;
    }

    long insert(Person person) throws ExecutionException, InterruptedException {
//        Future<Long> gettingNewPerson = (Future<Long>) PrayItDatabase.databaseWriteExecutor.submit(() -> {
//            mPersonDao.insert(person);
//        });
        Callable<Long> insertCallable = () -> mPersonDao.insert(person);
        long id = 0;
        Future<Long> future = executorService.submit(insertCallable);
        id = future.get();
//        PrayItDatabase.databaseWriteExecutor.execute(() -> {
//            mPersonDao.insert(person);
//        });
//        Long id = gettingNewPerson.get();
        return id;
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
