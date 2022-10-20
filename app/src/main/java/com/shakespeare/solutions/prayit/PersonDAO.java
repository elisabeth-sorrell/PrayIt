package com.shakespeare.solutions.prayit;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Person person);

    @Query("DELETE FROM person_table")
    void deleteAll();

    @Query("SELECT * from person_table ORDER BY name ASC")
    LiveData<List<Person>> getAlphabetizedPersons();

    @Delete
    void deletePerson(Person person);
}
