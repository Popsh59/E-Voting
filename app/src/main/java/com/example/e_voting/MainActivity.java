package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnLogin, btnRegister;
    TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);

        edtPassword = findViewById(R.id.edtPassword);

        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin = findViewById(R.id.btnLogin);

        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty())
                {
                    showToast("Please enter all fields");

                }
                else
                {
                    final AlertDialog dialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();

                    dialog.show();
                    Backendless.UserService.login(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim(), new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response)
                        {
                            dialog.dismiss();
                            if (response.getProperty("role").equals("Candidate"))
                            {
                                startActivity(new Intent(MainActivity.this,HomeCandidate.class));
                            }
                            else if (response.getProperty("role").equals("Student"))
                            {
                                startActivity(new Intent(MainActivity.this,Home.class));
                            }
                            else
                            {
                                startActivity(new Intent(MainActivity.this,Admin.class));
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault)
                        {
                            dialog.dismiss();
                            showToast(fault.getMessage());

                        }
                    },true);
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

        final AlertDialog dialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();

        dialog.show();

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response)
            {
                if(response)
                {
                    String userObjId = UserIdStorageFactory.instance().getStorage().get();

                    Backendless.Data.of(BackendlessUser.class).findById(userObjId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response)
                        {
                            dialog.dismiss();
                            if (response.getProperty("role").equals("Candidate"))
                            {
                                startActivity(new Intent(MainActivity.this,HomeCandidate.class));
                            }
                            else if (response.getProperty("role").equals("Student"))
                            {
                                startActivity(new Intent(MainActivity.this,Home.class));
                            }
                            else
                            {
                                startActivity(new Intent(MainActivity.this,Admin.class));
                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault fault)
                        {
                            showToast(fault.getMessage());

                        }
                    });
                }
                else
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void handleFault(BackendlessFault fault)
            {
                showToast(fault.getMessage());
            }
        });


        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().isEmpty()) {
                    showToast("Please Enter Email For Instructions");
                }
                else
                {
                    final AlertDialog dialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();

                    dialog.show();
                    Backendless.UserService.restorePassword(edtEmail.getText().toString().trim(), new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            dialog.dismiss();
                            showToast("Reset Instructions sent to " + edtEmail );
                        }

                        @Override
                        public void handleFault(BackendlessFault fault)
                        {
                            dialog.dismiss();
                            showToast("Error" + fault.getMessage());

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
}
