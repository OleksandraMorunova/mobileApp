package com.umsf.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.umsf.lab1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String grpNumb;
    private final static String STUDENTS_NUMBER="student";

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
    }

    private void btnClick(){
        CharSequence text = "Ви натиснули на кнопку";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        grpNumb = (String) binding.spinner.getSelectedItem();
        writeStudents(grpNumb);
    }

    private void writeStudents(String grpNumb){
        StringBuilder textStudent = new StringBuilder();
        for(Student st: ArrayListStudents.getStudents(grpNumb)){
            textStudent.append(st.getName()).append("\n");
        }
        binding.text.setText(textStudent.toString());
    }

    private void intentBtnClick(){
        this.startActivity(new Intent(this, StudentsListActivityClass.class)
                .putExtra("groupnumber", grpNumb));
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
    }
}