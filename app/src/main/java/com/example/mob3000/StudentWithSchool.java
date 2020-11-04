package com.example.mob3000;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithSchool {
    @Embedded
    public School school;

    @Relation(parentColumn = "schoolid", entityColumn = "uid", entity = School.class)
    public List<School> schoolList;
}
