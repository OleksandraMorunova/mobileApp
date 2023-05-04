package com.umsf.lab2.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.R;
import com.umsf.lab2.databinding.ListOfStudentsBinding;
import com.umsf.lab2.databinding.PreStudentsListBinding;
import com.umsf.lab2.groups.ArrayStudentsGroup;
import com.umsf.lab2.groups.StudentsGroup;
import com.umsf.lab2.groups.StudentsGroupActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentSListActivity extends AppCompatActivity {
    ListOfStudentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ListOfStudentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.listItem.setAdapter(new ArrayAdapter<>(StudentSListActivity.this, R.layout.item_of_list, getListNumbers()));

        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            String group = parent.getItemAtPosition(position).toString();
            StudentSListActivity.this.startActivity(new Intent(StudentSListActivity.this, StudentsGroupActivity.class)
                    .putExtra(StudentsGroupActivity.GROUP_NUMBERS, group));
        };

        binding.listItem.setOnItemClickListener(listener);

        binding.buttonList.setOnClickListener(v -> {
            StudentSListActivity.this.startActivity(new Intent(StudentSListActivity.this, AddItemToList.class));
        });
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
        Log.i("Lifecycle 'StudentListActivityClass'","onStart()");
        binding.listItem.setAdapter(new ArrayAdapter<>(this, R.layout.item_of_list, getListNumbers()));
    }

    private static List<String> getListNumbers(){
        List<String> list = new ArrayList<>();
        for(StudentsGroup s: ArrayStudentsGroup.studentsGroup){
            list.add(s.getNumber());
        }
        return list;
    }
}
