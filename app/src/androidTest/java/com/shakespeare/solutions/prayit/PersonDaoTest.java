package com.shakespeare.solutions.prayit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.shakespeare.solutions.prayit.person.Person;
import com.shakespeare.solutions.prayit.person.PersonDAO;
import com.shakespeare.solutions.prayit.util.db.PrayItDatabase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import kotlinx.coroutines.Dispatchers;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class PersonDaoTest {

    private PersonDAO personDAO;
    private PrayItDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setupDatabase() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                PrayItDatabase.class).allowMainThreadQueries().setQueryCallback(((sqlQuery, bindArgs) ->
                System.out.println("SQL QUERY: " + sqlQuery + ".... Args: " + bindArgs)), Executors.newSingleThreadExecutor()).build();

        personDAO = db.personDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetPerson() throws InterruptedException {
        Person person = new Person("Joe");
        personDAO.insert(person);

        List<Person> persons = LiveDataTestUtil.getValue(personDAO.getAlphabetizedPersons());
        assertEquals(persons.get(0).getName(), person.getName());
    }

    @Test
    public void deletePerson() throws InterruptedException {
        Person person = new Person("Joe");
        personDAO.insert(person);
        personDAO.deletePerson(person);
        List<Person> persons = LiveDataTestUtil.getValue(personDAO.getAlphabetizedPersons());
        assertTrue(!persons.contains(person));
    }

    @Test
    public void deleteAll() throws Exception {
        Person person = new Person("Joe");
        personDAO.insert(person);
        Person person2 = new Person("Jane");
        personDAO.insert(person2);
        personDAO.deleteAll();
        List<Person> persons = LiveDataTestUtil.getValue(personDAO.getAlphabetizedPersons());
        assertTrue(persons.isEmpty());
    }

    @Test
    public void updatePerson() throws InterruptedException {
        String oldName = "Joe";
        String newName = "Jane";
        Person person = new Person(oldName);

        long newPersonId = personDAO.insert(person);
        person.setName(newName);
        // For whatever reason, the person's id is incremented from the Person object's id when inserted,
        //  so we have to reset the id to the id from the insert. IMO, it's a weird RoomDB thing that shouldn't happen.
        person.setId((int) newPersonId);

        personDAO.updatePerson(person);

        Person personFromDb = LiveDataTestUtil.getValue(personDAO.getPersonById(person.getId()));

        assertEquals(personFromDb.getName(), newName);
    }

    @Test
    public void getPersonById() throws InterruptedException {
        Person person = new Person("Joe");
        personDAO.insert(person);

        int id = LiveDataTestUtil.getValue(personDAO.getAlphabetizedPersons()).get(0).getId();

        Person personFromDb = LiveDataTestUtil.getValue(personDAO.getPersonById(id));
        assertEquals(id, personFromDb.getId());
    }
}
