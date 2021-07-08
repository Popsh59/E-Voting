package com.example.e_voting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidates extends AppCompatActivity implements CandidatesAdapter.ItemClicked {

    RecyclerView rvList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String ObjectId;
    BackendlessUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidates);
        rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        loadCandidates();



    }

    public void loadCandidates()
    {
        String whereClause = "role = 'Candidate'";
        DataQueryBuilder  queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setGroupBy("name");

        Backendless.Persistence.of(BackendlessUser.class).find(queryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response)
            {
                for(int i = 0; i <response.size(); i++)
                {
                    user = response.get(i);
                    ObjectId = user.getObjectId();
                }
                ApplicationClass.users = (ArrayList<BackendlessUser>) response;
                adapter = new CandidatesAdapter(ViewCandidates.this, ApplicationClass.users);
                rvList.setAdapter(adapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ViewCandidates.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(final int index)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(ViewCandidates.this);
        alert.setMessage("Are you sure you want to delete ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which)
                    {
                        String whereClause = "name = '" +  ApplicationClass.users.get(index).getProperty("name") + "'" ;
                      Backendless.Persistence.of(BackendlessUser.class).remove(whereClause, new AsyncCallback<Integer>() {
                          @Override
                          public void handleResponse(Integer response)
                          {
                              Toast.makeText(ViewCandidates.this, "Candidate deleted", Toast.LENGTH_SHORT).show();
                              loadCandidates();
                          }

                          @Override
                          public void handleFault(BackendlessFault fault)
                          {
                              Toast.makeText(ViewCandidates.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();


                          }
                      });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.setTitle("Delete this file");
        alertDialog.show();
    }
}
