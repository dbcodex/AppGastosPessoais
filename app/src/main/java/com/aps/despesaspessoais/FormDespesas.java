package com.aps.despesaspessoais;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormDespesas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_despesas);

        getSupportActionBar().hide();
    }
}