package com.umsf.lab2.groups;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.database.CallToDatabase;
import com.umsf.lab2.database.StudentsDatabaseHelper;
import com.umsf.lab2.databinding.ListForUpdateDeleteBinding;
import com.umsf.lab2.students.Student;

import java.util.ArrayList;
import java.util.Arrays;

public class ListStudents extends AppCompatActivity {
    ListForUpdateDeleteBinding binding;
    private final static String STUDENTS_ID = "id";
    private final static String STUDENTS_NAME_S = "name";
    private final static String STUDENTS_GROUP_ID_S = "group_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ListForUpdateDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String number = getIntent().getStringExtra("id");
        binding.listItem.setAdapter(getAdapter(number));

        binding.buttonAddList.setOnClickListener(v -> {
            this.startActivity(new Intent(this, UpdateActivity.class)
            );
        });

    }

    private StudentsListAdapter getAdapter(String number){
        CallToDatabase call = new CallToDatabase(this);
        return new StudentsListAdapter(this, writeStudents(number));
    }

    private ArrayList<Student> writeStudents(String grpNumb){
        ArrayList<Student> studentNames = new ArrayList<>();
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT Students." + STUDENTS_ID + ", Students." + STUDENTS_NAME_S +
                " FROM Students " +
                " JOIN Groups ON Students." + STUDENTS_GROUP_ID_S + " = Groups." + STUDENTS_ID +
                " WHERE Groups.number = '" + grpNumb + "'";;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int studentId = cursor.getInt(cursor.getColumnIndex(STUDENTS_ID));
                String studentName = cursor.getString(cursor.getColumnIndex(STUDENTS_NAME_S));
                if (studentId >= 0) {
                    studentNames.add(new Student(studentName, String.valueOf(studentId)));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return studentNames;
    }
}