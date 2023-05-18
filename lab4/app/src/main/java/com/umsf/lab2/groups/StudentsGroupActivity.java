package com.umsf.lab2.groups;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.database.CallToDatabase;
import com.umsf.lab2.databinding.StudentsGroupActivityBinding;

import java.util.Objects;

public class StudentsGroupActivity extends AppCompatActivity {
    private StudentsGroupActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StudentsGroupActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int grpNumber = getIntent().getIntExtra("id", -1);
        CallToDatabase call = new CallToDatabase(this);
        StudentsGroup group = call.getFromDB(String.valueOf(grpNumber));

        if(String.valueOf(grpNumber).length() == 1){
            binding.editTextNumber.setText(group.getNumber());
            binding.editTextFaculty.setText(group.getFacultyName());
        } else {
            binding.editTextNumber.setText(String.valueOf(grpNumber));
            //binding.editTextFaculty.setText(Objects.requireNonNull(ArrayStudentsGroup.getStudents(String.valueOf(grpNumber))).getFacultyName());
        }

        binding.btnList.setOnClickListener(v -> {
            this.startActivity(new Intent(this, ListStudents.class)
                    .putExtra("id",  binding.editTextNumber.getText().toString()));
        });

        binding.textNumberImage.setText(binding.editTextNumber.getText());

        binding.editTextNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                   String s = editable.toString();
                   binding.textNumberImage.setText(s);
            }
        });

       binding.btnIntent.setOnClickListener(v -> {
          String outString = "Група " +  binding.editTextNumber.getText() + "\n";
          outString += "Факультет " + binding.editTextFaculty.getText() + "\n";
          if(binding.eduLevelMaster.isChecked()) outString += "Рівень освіти " + "магістр" + "\n";
          else outString += "Рівень освіти " + "бакалавр" + "\n";

          if(binding.contractFlg.isChecked()) outString += "Контрактники є" +"\n";
          else outString += "Контрактників немає" + "\n";

          if(binding.privilageFlg.isChecked()) outString += "Пільговики є" + "\n";
          else outString += "Пільговиків немає" + "\n";

           AlertDialog.Builder builder = new AlertDialog.Builder(this)
                   .setTitle("Студенти")
                   .setMessage(outString)
                   .setPositiveButton("OK", null);
           AlertDialog dialog = builder.create();
           dialog.show();
       });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle 'MainActivity'","onDestroy");
        binding = null;
    }
}