package com.example.mob3000;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "School")
public class School {
    @ColumnInfo
    @PrimaryKey (autoGenerate = true)
    @NonNull
    int uid;

    @ColumnInfo
    String schoolname;

    @ColumnInfo
    String campus;

    public School(int uid, String schoolname, String campus) {
        this.uid = uid;
        this.schoolname = schoolname;
        this.campus = campus;
    }

    public int getUid() {
        return uid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public String getCampus() {
        return campus;
    }

    @Override
    public String toString() {
        return "School{" +
                "uid=" + uid +
                ", schoolname='" + schoolname + '\'' +
                ", campus='" + campus + '\'' +
                '}';
    }
}
