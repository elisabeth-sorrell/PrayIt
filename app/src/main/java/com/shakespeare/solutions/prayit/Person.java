package com.shakespeare.solutions.prayit;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "person_table")
public class Person {
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Person(@NonNull String name) {this.name = name;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public void setId(int id) {this.id = id;}
    public int getId() {return this.id;}

}
