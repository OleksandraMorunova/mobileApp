package com.umsf.lab2.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.umsf.lab2.database.StudentsDatabaseHelper;
import com.umsf.lab2.groups.StudentsGroupActivity;
import com.umsf.lab2.databinding.ActivityMainBinding;
import com.umsf.lab2.list.PreStudentSListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String grpNumb;
    private final static String STUDENTS_NUMBER="student";
    private final static String GROUP_NUMBERS="id";

    private SQLiteDatabase db;
    private Cursor cursor;

    private final static String STUDENTS_ID = "id";
    private final static String STUDENTS_NAME_S = "name";
    private final static String STUDENTS_GROUP_ID_S = "group_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        if(savedInstanceState != null) {
            grpNumb = savedInstanceState.getString(STUDENTS_NUMBER);
            writeStudents(grpNumb);
        }

        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v -> btnClick());
        binding.btnIntent.setOnClickListener(v -> intentBtnClick());

        binding.btnDetails.setOnClickListener(v -> {
            String grpNumb = (String) binding.spinner.getSelectedItem();
            int integerGrpNumb = Integer.parseInt(grpNumb);
            this.startActivity(new Intent(this, StudentsGroupActivity.class)
                    .putExtra("id", integerGrpNumb)
            );
        });

        binding.btnList.setOnClickListener(v -> {
            this.startActivity(new Intent(this, PreStudentSListActivity.class));
        });
    }

    private void btnClick(){
        CharSequence text = "Ви натиснули на кнопку";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        grpNumb = (String) binding.spinner.getSelectedItem();
        writeStudents(grpNumb);
    }

    private List<String> writeStudents(String grpNumb){
        List<String> studentNames = new ArrayList<>();
        SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        String query = "SELECT Students." + STUDENTS_NAME_S +
                " FROM Students " +
                "JOIN Groups ON Students." + STUDENTS_GROUP_ID_S + " = Groups." + STUDENTS_ID +
                " WHERE Groups.number = '" + grpNumb + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int nameInt = cursor.getColumnIndex(STUDENTS_NAME_S);
                if (nameInt >= 0) {
                    studentNames.add(cursor.getString(nameInt));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        binding.text.setText(studentNames.toString());
        return studentNames;
    }

    private void intentBtnClick(){
        this.startActivity(new Intent(this, StudentsListActivityClass.class)
                .putExtra("id", grpNumb));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i("Lifecycle 'MainActivity'","onSaveInstanceState");
        outState.putString(STUDENTS_NUMBER, grpNumb);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle 'MainActivity'","onDestroy");
        binding = null;
        cursor.close();
        db.close();
    }
}