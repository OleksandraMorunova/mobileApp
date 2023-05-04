package com.umsf.lab2.list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.umsf.lab2.databinding.PreStudentsListBinding;

public class PreStudentSListActivity extends AppCompatActivity {
    PreStudentsListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PreStudentsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDetails.setOnClickListener(v -> this.startActivity(new Intent(this, StudentSListActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle 'MainActivity'","onDestroy");
        binding = null;
    }
}
