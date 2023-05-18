package com.umsf.lab2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.umsf.lab2.groups.ArrayStudentsGroup;
import com.umsf.lab2.groups.StudentsGroup;
import com.umsf.lab2.students.ArrayListStudents;
import com.umsf.lab2.students.Student;

public class StudentsDatabaseHelper extends SQLiteOpenHelper {
    public final static String DATA_BASE_NAME = "students";
    private final static int DATA_BASE_VERSION = 2;

    private final static String STUDENTS_ID = "id";
    private final static String STUDENTS_NUMBER = "number";
    private final static String STUDENTS_F_NAME = "faculityName";
    private final static String STUDENTS_EDU_LEVEL = "educationLevel";
    private final static String STUDENTS_CONTRACT = "contractExistsFlg";
    private final static String STUDENTS_PRIVILAGE = "privilageExistsFlg";

    private final static String STUDENTS_ID_S = "id";
    private final static String STUDENTS_NAME_S = "name";
    private final static String STUDENTS_GROUP_ID_S = "group_id";

    public static SQLiteDatabase db;

    public StudentsDatabaseHelper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        getReadableDatabase();
        Log.i("DB", "dbManager");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB", "dbOnCreate");
        String groupsTableSql = "CREATE TABLE Groups (\n" +
                STUDENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                STUDENTS_NUMBER + " TEXT (10) NOT NULL,\n" +
                STUDENTS_F_NAME + " TEXT (100),\n" +
                STUDENTS_EDU_LEVEL + " INTEGER,\n" +
                STUDENTS_CONTRACT + " BOOLEAN,\n" +
                STUDENTS_PRIVILAGE + " BOOLEAN\n" +
                ");";
        db.execSQL(groupsTableSql);
        populateGroups(db);
        populateStudents(db);
    }

    private void populateGroups(SQLiteDatabase db){
        for(StudentsGroup group: ArrayStudentsGroup.studentsGroup){
            insertRowToGroups(db, group);
        }
    }

    private void populateStudents(SQLiteDatabase db){
        for(Student student: ArrayListStudents.students){
            insertRowToStudents(db, student);
        }
    }

    public void insertRowToGroups(SQLiteDatabase db, StudentsGroup group){
        ContentValues contentValue = new ContentValues();
        contentValue.put(STUDENTS_NUMBER, group.getNumber());
        contentValue.put(STUDENTS_F_NAME, group.getFacultyName());
        contentValue.put(STUDENTS_EDU_LEVEL, group.getEducationLevel());
        contentValue.put(STUDENTS_CONTRACT, group.isContractExistsFlg());
        contentValue.put(STUDENTS_PRIVILAGE, group.isPrivilageExistsFlg());
        db.insert("Groups", null, contentValue);
    }

    private void insertRowToStudents(SQLiteDatabase db, Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENTS_NAME_S, student.getName());
        contentValues.put(STUDENTS_GROUP_ID_S, getGroupIdByNumber(db, student.getGroupNumber()));
        db.insert("Students", null, contentValues);
    }

    private int getGroupIdByNumber(SQLiteDatabase db, String groupNumber) {
        String[] columns = {STUDENTS_ID};
        String selection = STUDENTS_NUMBER + " = ?";
        String[] selectionArgs = {groupNumber};
        Cursor cursor = db.query("Groups", columns, selection, selectionArgs, null, null, null);
        int groupId = -1;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(STUDENTS_ID);
            if (columnIndex >= 0) {
                groupId = cursor.getInt(columnIndex);
            } else {
                Log.e("getColumnIndex", "Column not found: " + STUDENTS_ID);
            }
        }
        cursor.close();
        return groupId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            String studentsTableSql = "CREATE TABLE Students (\n" +
                    STUDENTS_ID_S + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    STUDENTS_NAME_S + " TEXT (100) NOT NULL,\n" +
                    STUDENTS_GROUP_ID_S + " INTEGER REFERENCES Groups (id) ON DELETE RESTRICT ON UPDATE RESTRICT\n" +
                    ");";
            db.execSQL(studentsTableSql);

            populateStudents(db);
        }
    }
}