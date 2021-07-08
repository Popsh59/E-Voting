package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class SetRoles extends AppCompatActivity implements StudentsAdapter.ItemClicked
{

    RecyclerView rvList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    EditText edtStudentNo;
    Button btnSearch;
    List<BackendlessUser> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_roles);

        userList = new ArrayList<>();

        btnSearch = findViewById(R.id.btnSearch);

        edtStudentNo = findViewById(R.id.edtStudentNo);

        rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtStudentNo.getText().toString().isEmpty())
                {
                    Toast.makeText(SetRoles.this, "Please student number to search", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String whereClause = "studentNo = '" + edtStudentNo.getText().toString().trim() +"'";
                    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                    queryBuilder.setWhereClause(whereClause);

                    Backendless.Persistence.of(BackendlessUser.class).find(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
                        @Override
                        public void handleResponse(List<BackendlessUser> response)
                        {
                            ApplicationClass.users = (ArrayList<BackendlessUser>) response;

                            for(int i = 0; i <response.size(); i++)
                            {
                                if(response.get(i).getProperty("studentNo").equals(edtStudentNo.getText().toString().trim()))
                                {

                                }
                                else
                                {
                                    Toast.makeText(SetRoles.this, "Student not found!", Toast.LENGTH_SHORT).show();
                                }
                            }



                            adapter = new StudentsAdapter(SetRoles.this, ApplicationClass.users);
                            rvList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });

                }
            }
        });

        String whereClause = "role = 'Student'";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setGroupBy("name");


        Backendless.Persistence.of(BackendlessUser.class).find(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response)
            {
                ApplicationClass.users = (ArrayList<BackendlessUser>) response;
                adapter = new StudentsAdapter(SetRoles.this, ApplicationClass.users);
                rvList.setAdapter(adapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(SetRoles.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClicked(int index)
    {
        Intent intent = new Intent(SetRoles.this,StudentInfo.class);
        intent.putExtra("name", "" + ApplicationClass.users.get(index).getProperty("name"));
        startActivity(intent);
    }
}
