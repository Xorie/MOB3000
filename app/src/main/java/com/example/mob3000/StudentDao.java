package com.example.mob3000;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface  StudentDao {

    @Query("Select * from Student ")
    List<Student> loadAllStudent();

    //henter studentinformasjon
    @Query("Select * from Student Where sid = (:username) AND password = (:password)")
    Student login(String username, String password);

    @Query("Select * from Student Where subject = (:subject)")
    List<Student> sok(String subject);

    @Insert
    void insertStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);
}

