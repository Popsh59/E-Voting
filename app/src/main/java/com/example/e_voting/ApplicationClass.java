package com.example.e_voting;

import android.app.Application;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.ArrayList;

public class ApplicationClass extends Application
{
    public static final String APPLICATION_ID = "53D6D975-0E31-9690-FFA8-508CD4FB5D00";
    public static final String API_KEY = "4440C3CC-FC28-437B-FFE8-CF7A3798E400";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static ArrayList<BackendlessUser> users;

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),
                APPLICATION_ID, API_KEY);

        users = new ArrayList<>();
    }


}
