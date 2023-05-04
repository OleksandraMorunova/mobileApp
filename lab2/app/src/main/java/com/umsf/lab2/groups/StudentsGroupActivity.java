package com.umsf.lab2.groups;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.databinding.StudentsGroupActivityBinding;


public class StudentsGroupActivity extends AppCompatActivity {
    private StudentsGroupActivityBinding binding;
    private final static String GROUP_NUMBERS="groupnumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StudentsGroupActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String grpNumber = getIntent().getStringExtra(GROUP_NUMBERS);
        StudentsGroup group = ArrayStudentsGroup.getStudents(grpNumber);
       if(group != null){
           binding.editTextNumber.setText(group.getNumber());
           binding.editTextFaculty.setText(group.getFacultyName());
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
       }

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
}
