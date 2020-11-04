package com.example.mob3000;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Student")
public class Student implements Serializable {
    @ColumnInfo(name = "sid")
    @PrimaryKey
    @NonNull
    private String sid;

    @ColumnInfo (name = "password")
    private String password;

    @ColumnInfo (name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo (name = "schoolid")
    private String schoolid;

    @ColumnInfo (name = "subject")
    private String subject;

    @ColumnInfo (name ="year")
    private String year;


    //gettere
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

    public String getSchoolid() {
        return schoolid;
    }

    public String getSubject() {
        return subject;
    }

    public String getYear() {
        return year;
    }

    public void setSid(@NonNull String sid) {
        this.sid = sid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setYear(String year) {
        this.year = year;
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


