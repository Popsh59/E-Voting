package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeCandidate extends AppCompatActivity
{
    Button btnStats,btnCandidates,btnManifesto,btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_candidate);

        btnStats = findViewById(R.id.btnStats);

        btnCandidates = findViewById(R.id.btnCandidates);

        btnInfo = findViewById(R.id.btnInfo);

        btnManifesto = findViewById(R.id.btnManifesto);

        btnCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeCandidate.this,ViewCandidates.class));

            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnManifesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
