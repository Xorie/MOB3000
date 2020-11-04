package com.example.mob3000;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, School.class}, version=2)
public abstract class MyDatabase  extends RoomDatabase {

    // Database name to be used
    public static final String DBNAME = "Studentbay";
    // instance variable
    private static MyDatabase INSTANCE;

    // **********//
    // Declare your data access objects *DAO* as abstract
    public abstract StudentDao getStudentDao();
    public abstract SchoolDao getSchoolDao();
    // **********//

    static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DBNAME)
                            // Wipes and rebuilds instead of migrating // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
