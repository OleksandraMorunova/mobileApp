package com.umsf.lab2.groups;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.database.StudentsDatabaseHelper;
import com.umsf.lab2.databinding.ListForUpdateDeleteBinding;
import com.umsf.lab2.databinding.UpdateBinding;

import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {
    UpdateBinding binding;

    private final static String STUDENTS_NAME_S = "name";
    private final static String STUDENTS_GROUP_ID_S = "group_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String number = getIntent().getStringExtra("id");
        binding.buttonUpdate.setOnClickListener(v -> {
            SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
            String query = "UPDATE Students SET name = ? WHERE group_id = ?";
            db.rawQuery(query, new String[]{binding.changeName.getText().toString(), String.valueOf(number)});
            db.close();
        });

        binding.buttonAdd.setOnClickListener(v -> {
            SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(this);
            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(STUDENTS_NAME_S, binding.addName.getText().toString());
            values.put(STUDENTS_GROUP_ID_S, Integer.parseInt(binding.addGroup.getText().toString()));
            db.insert("Students", null, values);
            db.close();
        });
    }
}