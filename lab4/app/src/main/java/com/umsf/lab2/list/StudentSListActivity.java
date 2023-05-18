package com.umsf.lab2.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.database.CallToDatabase;
import com.umsf.lab2.databinding.ListOfStudentsBinding;
import com.umsf.lab2.groups.StudentsGroup;
import com.umsf.lab2.groups.StudentsGroupActivity;
import com.umsf.lab2.groups.StudentsGroupAdapter;


public class StudentSListActivity extends AppCompatActivity {
    private ListOfStudentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ListOfStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listItem.setAdapter(getAdapter());

        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            StudentsGroup group = (StudentsGroup) parent.getItemAtPosition(position);
            this.startActivity(new Intent(StudentSListActivity.this, StudentsGroupActivity.class)
                    .putExtra("id", group.getId())
            );
        };

        binding.listItem.setOnItemClickListener(listener);

        binding.buttonList.setOnClickListener(v -> this.startActivity(new Intent(StudentSListActivity.this, AddItemToList.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle 'MainActivity'","onDestroy");
        binding = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.listItem.setAdapter(getAdapter());
    }

    private ArrayAdapter<StudentsGroup> getAdapter(){
        CallToDatabase call = new CallToDatabase(this);
        return new StudentsGroupAdapter(this, call.getFromDB());
    }
}