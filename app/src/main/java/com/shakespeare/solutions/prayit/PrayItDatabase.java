package com.shakespeare.solutions.prayit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class PrayItDatabase extends RoomDatabase {
    public abstract PersonDAO personDao();

    private static volatile PrayItDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor  =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PrayItDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (PrayItDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PrayItDatabase.class, "prayit_database").build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // TODO: this is not something we want for production later on
            //    this is more for POC right now. Should take it out later.
            databaseWriteExecutor.execute(() -> {
                PersonDAO dao = INSTANCE.personDao();
                dao.deleteAll();

                Person person = new Person("Dad");
                dao.insert(person);
                person = new Person("Elisabeth");
                dao.insert(person);
                person = new Person("Mom");
                dao.insert(person);
                person = new Person("Timothy");
                dao.insert(person);
                person = new Person("Baby Jill");
                dao.insert(person);
            });
        }
    };
}
