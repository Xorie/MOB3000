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

    @Query("Select * from Student Where sid = (:studentid)")
    List<Student> loadAllStudent(String studentid);

    @Query("Select * from Student Where sid = (:id)")
    Student loadStudentById(String id);

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

    @Query("DELETE FROM Student WHERE sid = :id")
    void deleteById(String id);
    /*
    @Query("Delete from Student Where sid = (:studentid)")
    Student deleteStudent(String studentid);
    */


}

