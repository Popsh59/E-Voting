package com.example.e_voting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadVideo extends AppCompatActivity {

    private VideoView video;
    private static final int PICK_VIDEO = 3;
    private Uri videoUri;
    private MediaController mediaController;
    String realPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);

        video = findViewById(R.id.video);


        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        mediaController = new MediaController(UploadVideo.this);
                        video.setMediaController(mediaController);
                        mediaController.setAnchorView(video);

                    }
                });
            }
        });

        video.start();

        if(ContextCompat.checkSelfPermission(UploadVideo.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(UploadVideo.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else
        {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUpload(View view)
    {
        Intent intent = new Intent();

        intent.setType("video/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"Select a Video"),PICK_VIDEO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FileOutputStream fileOutputStream = null;
        File file = null;
        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            videoUri = data.getData();
            video.setVideoURI(videoUri);
            realPath = RealPath.getPath(UploadVideo.this, videoUri);

            file = new File(realPath);

            Backendless.Files.upload(file, "/video", new AsyncCallback<BackendlessFile>() {
                @Override
                public void handleResponse(BackendlessFile response)
                {
                    Toast.makeText(UploadVideo.this, "Successfully saved!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault)
                {
                    Toast.makeText(UploadVideo.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            

        }







    }

}
