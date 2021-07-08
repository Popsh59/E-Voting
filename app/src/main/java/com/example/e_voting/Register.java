package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Register extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    TextView tvHeading;
    EditText edtName,edtSurname,edtIdNumber,edtStudentNo,edtEmail,edtPassword,edtRePassword;
    Spinner spInstitution,spGender,spCampus,spFaculty,spDepart,spYear;
    Button btnCancel,btnSubmit;
    static String gender,instu,depart,year,faculty,campus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvHeading = findViewById(R.id.tvHeading);
        edtStudentNo = findViewById(R.id.edtStudentNo);
        edtName = findViewById(R.id.edtName);
        edtSurname = findViewById(R.id.edtSurname);
        edtIdNumber = findViewById(R.id.edtIdNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);
        spInstitution = findViewById(R.id.spInstitution);
        spGender = findViewById(R.id.spGender);
        spDepart = findViewById(R.id.spDepart);
        spYear = findViewById(R.id.spYear);
        spFaculty = findViewById(R.id.spFaculty);
        spCampus = findViewById(R.id.spCampus);

        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);
        spFaculty.setOnItemSelectedListener(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.instu));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spInstitution.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.gender));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGender.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.campus));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCampus.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.faculty));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFaculty.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.year));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spYear.setAdapter(adapter4);




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                spInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                     instu = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

                spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                       gender = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        campus = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                spDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        depart = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        year = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                if (edtStudentNo.getText().toString().isEmpty() || edtName.getText().toString().isEmpty() ||
                        edtSurname.getText().toString().isEmpty() || edtIdNumber.getText().toString().isEmpty() ||
                         edtEmail.getText().toString().isEmpty()
                || edtPassword.getText().toString().isEmpty() || edtRePassword.getText().toString().isEmpty()  )
                {
                    showToast("Please Enter All Fields");
                }
                else if (!edtPassword.getText().toString().trim().equals(edtRePassword.getText().toString().trim()))
                {
                    showToast("Please Make Sure Passwords Match");
                }
                else
                {
                    BackendlessUser user = new BackendlessUser();

                    user.setEmail(edtEmail.getText().toString().trim());
                    user.setPassword(edtPassword.getText().toString().trim());
                    user.setProperty("name",edtName.getText().toString().trim());
                    user.setProperty("surname",edtSurname.getText().toString().trim());
                    user.setProperty("idNo",edtIdNumber.getText().toString().trim());
                    user.setProperty("studentNo",edtStudentNo.getText().toString().trim());
                    user.setProperty("role","Student");
                    user.setProperty("faculty",faculty);
                    user.setProperty("campus",campus);
                    user.setProperty("department",depart);
                    user.setProperty("year",year);
                    user.setProperty("gender",gender);
                    user.setProperty("institution",instu);
                    user.setProperty("hasVoted",false);

                    final AlertDialog dialog = new SpotsDialog.Builder().setContext(Register.this).build();

                    dialog.show();

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response)
                        {
                            showToast("Register successfully");
                            dialog.dismiss();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault)
                        {
                            dialog.dismiss();
                            showToast(fault.getMessage());

                        }
                    });
                }

            }
        });
    }
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        faculty = parent.getItemAtPosition(position).toString();
        if(faculty.contentEquals("Faculty of Engineering, Built Environment and Information Technology"))
        {
            List<String> list = new ArrayList<String>();
            list.add("Select department");
            list.add("Built Environment");
            list.add("Civil Engineering");
            list.add("Electrical, Electronic and Computer Engineering");
            list.add("Information Technology");
            list.add("Mathematics and Physical Sciences");
            list.add("Mechanical and Mechatronic Engineering");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spDepart.setAdapter(dataAdapter);

        }
        else if(faculty.contentEquals("Faculty of Health and Environmental Sciences"))
        {
            List<String> list = new ArrayList<String>();
            list.add("Select department");
            list.add("Agriculture");
            list.add("Clinical Sciences");
            list.add("Health Sciences");
            list.add("Life Sciences");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spDepart.setAdapter(dataAdapter);

        }
        else if(faculty.contentEquals("Faculty of Humanities"))
        {
            List<String> list = new ArrayList<String>();
            list.add("Select department");
            list.add("Communication Sciences");
            list.add("Design and Studio Art");
            list.add("Language and Social Sciences Education");
            list.add("Mathematics, Science and Technology Education");
            list.add("Post Graduate Studies in Education");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spDepart.setAdapter(dataAdapter);

        }
        else
        {
            List<String> list = new ArrayList<String>();
            list.add("Select department");
            list.add("Accounting and Auditing");
            list.add("Business Management");
            list.add("Business Support Studies");
            list.add("Government Management");
            list.add("Hospitality Management");
            list.add("Tourism and Event Management");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spDepart.setAdapter(dataAdapter);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
