package com.example.mob3000;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "Student")
        /*foreignKeys = @ForeignKey(entity = School.class,
        parentColumns = "uid",
        childColumns = "schoolid"))*/

public class Student {
    //private School school = new School(uid,schoolname,campus);
    @ColumnInfo
    @PrimaryKey
    @NonNull
    String sid;

    @ColumnInfo
    String password;

    @ColumnInfo
    String firstname;

    @ColumnInfo
    String lastname;


    @ColumnInfo
    int schoolid;

    @ColumnInfo
    String subject;

    @ColumnInfo
    String year;

    public Student(String sid, String password, String firstname, String lastname,
                   int schoolid, String subject, String year) {
        this.sid = sid;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.schoolid = schoolid;
        this.subject = subject;
        this.year = year;
    }

    public String getSid() {
        return sid;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    public int getSchoolid() {
        return schoolid;
    }

    public String getSubject() {
        return subject;
    }

    public String getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", schoolid=" + schoolid +
                ", subject='" + subject + '\'' +
                ", year=" + year +
                '}';
    }
}
