package com.example.mob3000;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class myDbAdapter {

        myDbHelper myhelper;
        public myDbAdapter(Context context)
        {
            myhelper = new myDbHelper(context);
        }

        public long insertData(String name, String pass)
        {
            SQLiteDatabase dbb = myhelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(myDbHelper.NAME1, name);
            contentValues.put(myDbHelper.MyPASSWORD, pass);
            long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
            return id;
        }

        public String getData()
        {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD};
            Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
            StringBuffer buffer= new StringBuffer();
            while (cursor.moveToNext())
            {
                int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
                String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
                buffer.append(cid+ "   " + name + "   " + password +" \n");
            }
            return buffer.toString();
        }

        public  int delete(String uname)
        {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            String[] whereArgs ={uname};

            int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
            return  count;
        }

        public int updateName(String oldName , String newName)
        {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(myDbHelper.NAME,newName);
            String[] whereArgs= {oldName};
            int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
            return count;
        }

        static class myDbHelper extends SQLiteOpenHelper
        {
            private static final String DATABASE_NAME = "myDatabase";    // Database Name
            private static final int DATABASE_Version = 1;    // Database Version
            private static final String TABLE_NAME = "Skole"; //Table Name
            private static final String UID = "SkoleID"; // Column 1
            private static final String SCHOOL_NAME = "Skolenavn"; // Column 2
            private static final String CAMPUS = "Campus"; // Column 3
            private static final String TABLE_NAME2 = "Student";   // Table Name
            private static final String SID = "StudentID";     // Column 1 (Primary Key)
            private static final String PASSWORD = "Passord"; // Column 2
            private static final String NAME1 = "Fornavn";    //Column 3
            private static final String NAME2 = "Etternavn";    // Column 4
            private static final String SOCIAL = "Sosialemedier"; // Column 5
            private static final String BIO = "Biografi"; // Column 6
            private static final String SCHOOL = "SkoleID"; // Column 7
            private static final String SUBJECT = "Fag"; //Column 8
            private static final String YEAR = "Årskull"; // Column 9
            private static final String CREATE_TABLE = "CREATE TABLE "
                    + TABLE_NAME + " ("
                    + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SCHOOL_NAME + " VARCHAR(40), "
                    + CAMPUS +" VARCHAR(30));";
            private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
            private static final String CREATE_TABLE2 = "CREATE TABLE "
                    + TABLE_NAME2 + " ("
                    + SID + " CHAR(6), "
                    + PASSWORD + " VARCHAR(30), "
                    + NAME1 + " VARCHAR(30), "
                    + NAME2 + " VARCHAR(40), "
                    + SOCIAL + " VARCHAR(80), "
                    + BIO + " VARCHAR(100), "
                    + SCHOOL + " INTEGER, "
                    + SUBJECT + " VARCHAR(40), "
                    + YEAR + " INTEGER, "
                    + " FOREIGN KEY ("+SCHOOL+") REFERENCES "+TABLE_NAME+"("+UID+"));";
            private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS "+TABLE_NAME2;
            private Context context;

            public myDbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_Version);
                this.context=context;
            }

            public void onCreate(SQLiteDatabase db) {

                try {
                    db.execSQL(CREATE_TABLE);
                    db.execSQL(CREATE_TABLE2);
                } catch (Exception e) {
                    Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                try {
                    Toast.makeText(context, "OnUpgrade", Toast.LENGTH_SHORT).show();
                    db.execSQL(DROP_TABLE);
                    db.execSQL(DROP_TABLE2);
                    onCreate(db);
                }catch (Exception e) {
                    Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
