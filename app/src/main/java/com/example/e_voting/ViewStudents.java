package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ViewStudents extends AppCompatActivity implements StudentsAdapter.ItemClicked {

    RecyclerView rvList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);

        String whereClause = "role = 'Student'";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setGroupBy("name");


        Backendless.Persistence.of(BackendlessUser.class).find(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response)
            {
                ApplicationClass.users = (ArrayList<BackendlessUser>) response;
                adapter = new StudentsAdapter(ViewStudents.this, ApplicationClass.users);
                rvList.setAdapter(adapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ViewStudents.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClicked(int index) {

    }
}
