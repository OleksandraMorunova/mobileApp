package com.umsf.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab1.databinding.StudentListIntentBinding;

import java.util.Locale;

public class StudentsListActivityClass extends AppCompatActivity {
    private static final String GROUP_NUMBER = "groupnumber";
    private StudentListIntentBinding binding;
    private Boolean isRunning = true;
    private float textSize = 0;
    private int seconds = 0;

    public static final String SECONDS_KEY = "seconds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StudentListIntentBinding.inflate(getLayoutInflater());
        String grpNumber = getIntent().getStringExtra(GROUP_NUMBER);
        StringBuilder textStudent = new StringBuilder();
        for(Student st: ArrayListStudents.getStudents(grpNumber)){
            textStudent.append(st.getName()).append("\n");
        }

        binding.btnSend.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, binding.text.getText().toString())
                    .putExtra(Intent.EXTRA_SUBJECT, "Список студентів");
            startActivity(intent);
        });

        binding.text.setText(textStudent);
        textSize = binding.text.getTextSize();
        binding.btnPlus.setOnClickListener(v -> {
            textSize *= 1.1f;
            binding.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        });

        if(savedInstanceState != null){
            Log.d("SavedInstanceState", "Call SavedInstanceState");
            seconds = savedInstanceState.getInt(SECONDS_KEY);
        }

        getCountDownTimer(binding.timer);
        setContentView(binding.getRoot());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i("Lifecycle 'StudentListActivityClass'","onSaveInstanceState");
        outState.putFloat("textSize", textSize);
        outState.putInt(SECONDS_KEY, seconds);
        super.onSaveInstanceState(outState);
    }

    private void getCountDownTimer(TextView timer){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                timer.setText(time);
                if(isRunning) seconds++;
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle 'StudentListActivityClass'","onDestroy()");
        isRunning = false;
        binding = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle 'StudentListActivityClass'","onStart()");
        isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle 'StudentListActivityClass'","onStop()");
        isRunning = false;
    }
}