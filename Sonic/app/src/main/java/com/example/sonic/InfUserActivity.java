package com.example.sonic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sonic.sharedPreferences.DataLocalManager;

public class InfUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_user);

        Toolbar mToolbar = findViewById(R.id.toolbarInf);
        setSupportActionBar(mToolbar);
        String name = DataLocalManager.getUserDTO().getName();

//        getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTextViewName = findViewById(R.id.textViewName);
        mTextViewName.setText(name);

    }
}