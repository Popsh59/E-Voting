package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class StudentInfo extends AppCompatActivity {

    TextView tvStudName, tvEmail,tvStudNo;
    RadioGroup rgRole;
    RadioButton rbStudent,rbCandidate;
    Button btnSub;

    BackendlessUser user;

    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        tvStudName = findViewById(R.id.tvStudName);

        tvEmail = findViewById(R.id.tvEmail);

        tvStudNo = findViewById(R.id.tvStudNo);

        rgRole = findViewById(R.id.rgRole);

        rbStudent = findViewById(R.id.rbStudent);

        rbCandidate = findViewById(R.id.rbCandidate);

        btnSub = findViewById(R.id.btnSub);

        if(getIntent().hasExtra("name"))
        {
            name = getIntent().getStringExtra("name");
            tvStudName.setText(name);
        }

        DataQueryBuilder builder = DataQueryBuilder.create();
        builder.setPageSize(10).setOffset(0);
        builder.setWhereClause("name='" + name +"'");

        Backendless.Persistence.of(BackendlessUser.class).find(builder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response)
            {
                for (int i = 0; i<response.size();i++)
                {
                    user = response.get(i);
                    tvEmail.setText("" +user.getProperty("email"));
                    tvStudNo.setText(""+user.getProperty("studentNo"));
                }

            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                Toast.makeText(StudentInfo.this, "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(rbStudent.isChecked())
                {
                    user.setProperty("role","Student");
                }
                else
                {
                    user.setProperty("role","Candidate");
                    user.setProperty("party","");
                    user.setProperty("position","");
                }

                final AlertDialog dialog = new SpotsDialog.Builder().setContext(StudentInfo.this).build();

                dialog.show();

                Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response)
                    {
                        dialog.dismiss();
                        Toast.makeText(StudentInfo.this, "User Details updated to "+response.getProperty("role"), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault)
                    {
                        dialog.dismiss();
                        Toast.makeText(StudentInfo.this, "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}
