package com.umsf.lab2.list;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.umsf.lab2.R;
import com.umsf.lab2.database.StudentsDatabaseHelper;
import com.umsf.lab2.databinding.AddItemToListBinding;
import com.umsf.lab2.groups.ArrayStudentsGroup;
import com.umsf.lab2.groups.StudentsGroup;

import java.util.Objects;

public class AddItemToList extends AppCompatActivity {
    private final String[] typeStudent = {"Контракт", "Бюджет"};
    private AddItemToListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddItemToListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.type.setAdapter(new ArrayAdapter<>(this, R.layout.item_of_list, typeStudent));

        binding.btnAddedItem.setOnClickListener(v -> {
            boolean type = !Objects.equals(binding.type.getText().toString(), typeStudent[1]);
            StudentsGroup group = new StudentsGroup(
                    binding.editTextNumber.getText().toString(),
                    binding.editTextFaculty.getText().toString(),
                    0,
                    type,
                    !type
            );
            StudentsDatabaseHelper databaseHelper = new StudentsDatabaseHelper(getApplicationContext());
            databaseHelper.insertRowToGroups(databaseHelper.getWritableDatabase(), group);
            NavUtils.navigateUpFromSameTask(this);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle 'MainActivity'","onDestroy");
        binding = null;
    }
}