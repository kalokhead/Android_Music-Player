package com.example.sound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<File> arrayList;
    private ListView lv;
    private CuostomAdapter cAdapter;//initialize coustom adapter
    private MediaPlayer mp = null; // initialize mediaplayer as null

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview); //innitialize listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {//set on Clicklistener
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File itemName = arrayList.get(position); //get the file postion
                String path = itemName.getAbsolutePath(); //get the path of file to play
                Toast.makeText(MainActivity.this, "" + itemName.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                try {
                    if (mp != null && mp.isPlaying()) {//cheacking condition
                        mp.stop();//stop the song
                    }
                    mp = new MediaPlayer();//initialize the new object

                    mp.setDataSource(path + File.separator); //set the music path to the media player
                    mp.prepare();//preaper for playing
                    mp.start();//start the song
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            //check the permission granted or not
            getAudio();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 234);
            //give the storage permission
        }
    }

    public void getAudio() {
        List<File> allFile = getAllAudios(this); //get all audio from storage to the allfile
        arrayList = allFile; //assign allfile to the arraylist
        cAdapter = new CuostomAdapter(this, allFile);//acess coustom adapter from the coustomAdapter.java
        lv.setAdapter(cAdapter);//setting adapter
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 234) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAudio();//get the audio
            } else {
                Toast.makeText(this, "To list audio files, we require your permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<File> getAllAudios(Context c) {
        List<File> files = new ArrayList<>();
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.Media.DISPLAY_NAME};
        Cursor cursor = c.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                files.add((new File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)))));
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }
}

