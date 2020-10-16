package com.example.mob3000;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface SchoolDao {
    @Query("Select * from School ")
        // public Drink getalldrinks()
    List<School> loadAllSchool();

    @Insert
    void insertSchool(School school);

    @Update
    void updateSchool(School school);

    @Delete
    void deleteSchool(School school);
}
