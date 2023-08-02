package com.shakespeare.solutions.prayit.person;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Person person);

    @Query("DELETE FROM person_table")
    void deleteAll();

    @Query("SELECT * from person_table ORDER BY name ASC")
    LiveData<List<Person>> getAlphabetizedPersons();

    @Delete
    void deletePerson(Person person);

    @Update
    void updatePerson(Person person);

    @Query("SELECT * FROM person_table WHERE id = :id")
    LiveData<Person> getPersonById(int id);
}
