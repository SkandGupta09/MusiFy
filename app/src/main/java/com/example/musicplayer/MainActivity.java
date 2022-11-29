package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerview;
    TextView nomuscic;
    ArrayList<Audiomodel> songslist=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = findViewById(R.id.recyclerview);
        nomuscic = findViewById(R.id.nosongs);
        if(checkpermission()==false)
        {
            getpermission();
            return;
        }
        String[] projection={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        String selection=MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor cursor=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext())
        {
            Audiomodel songdata= new Audiomodel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
         if(new File(songdata.getPath()).exists())
            songslist.add(songdata);
        }
        if(songslist.size()==0)
        {
            nomuscic.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            recyclerview.setAdapter( new musiclistadapter(songslist,getApplicationContext()));
        }
    }
        boolean checkpermission()
        {
            int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        void getpermission()
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(MainActivity.this, "REQUEST FOR PERMISSION", Toast.LENGTH_SHORT).show();
            }
            else
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);

        }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerview!=null)
            recyclerview.setAdapter( new musiclistadapter(songslist,getApplicationContext()));
    }
}
