package com.shakespeare.solutions.prayit.person;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "person_table")
public class Person implements Serializable {
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    public Person(@NonNull String name) {this.name = name;}

    public Person(@NonNull int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public void setId(int id) {this.id = id;}
    public int getId() {return this.id;}

}
