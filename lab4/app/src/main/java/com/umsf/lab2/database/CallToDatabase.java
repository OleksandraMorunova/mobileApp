package com.umsf.lab2.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.umsf.lab2.groups.StudentsGroup;

import java.util.ArrayList;

public class CallToDatabase {
    private final Context context;


    public CallToDatabase(Context context) {
        this.context = context;
    }

        public ArrayList<StudentsGroup> getFromDB(){
        ArrayList<StudentsGroup> groups = new ArrayList<>();
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(context);
        try{
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db(db, null, null, "number");
            while(cursor.moveToNext()){
                try{
                    groups.add(new StudentsGroup(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getInt(3),
                                    (cursor.getInt(4) > 0),
                                    (cursor.getInt(5) > 0)
                            )
                    );
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Log.e("SQLiteException", "Data unavailable");
        }
        return groups;
    }

    public StudentsGroup getFromDB(String number){
        StudentsGroup groups = null;
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(context);
        try{
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db(db, "id=?", number, null);
            if(cursor.moveToFirst()){
                groups = new StudentsGroup(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        (cursor.getInt(4) > 0),
                        (cursor.getInt(5) > 0)
                );
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Log.e("SQLiteException", "Data unavailable");
        }
        return groups;
    }

    private Cursor db(SQLiteDatabase db, Object selections, Object selectionArgs, Object orderBy){
        return db.query("groups",
                new String[]{"id","number",
                        "faculityName",
                        "educationLevel",
                        "contractExistsFlg",
                        "privilageExistsFlg"
                },
                selections == null ? null : selections.toString(),
                selectionArgs == null ? null : new String[]{selectionArgs.toString()},
                null,
                null,
                orderBy == null ? null : orderBy.toString());
    }
}