package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Admin extends AppCompatActivity {

    Button btnSetRole,btnViewStudents,btnViewCandidates,btnLogout,btnUploadVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnUploadVideo = findViewById(R.id.btnUploadVideo);

        btnSetRole = findViewById(R.id.btnRole);

        btnViewStudents = findViewById(R.id.btnViewStudents);

        btnViewCandidates = findViewById(R.id.btnViewCandidates);

        btnLogout = findViewById(R.id.btnLogout);


        btnSetRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,SetRoles.class));
            }
        });

        btnViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,ViewStudents.class));
            }
        });

        btnViewCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,ViewCandidates.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {

                        showToast("Successfully Logged out");
                        finish();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        showToast("Error: " + fault.getMessage());
                    }
                });
            }
        });
        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Admin.this,UploadVideo.class));
            }
        });
    }
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
