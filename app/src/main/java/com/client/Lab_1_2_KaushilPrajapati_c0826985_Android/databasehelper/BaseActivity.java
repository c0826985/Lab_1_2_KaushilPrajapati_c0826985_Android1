package com.client.Lab_1_2_KaushilPrajapati_c0826985_Android.databasehelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.client.Lab_1_2_KaushilPrajapati_c0826985_Android.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}