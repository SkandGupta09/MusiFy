package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Muscicplayeractivity extends AppCompatActivity {
    TextView titletv,currenttimetv,totaltimetv;
    SeekBar seekBar;
    ImageView pauseplay,nextbtn,previousbtn,musicicon;
    ArrayList<Audiomodel> songslist;
    Audiomodel currentsong;
    MediaPlayer mediaplayer= Mediaplayer.getInstance();
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscicplayeractivity);
        titletv=findViewById(R.id.songtitle);
        currenttimetv=findViewById(R.id.currenttime);
        totaltimetv=findViewById(R.id.totaltime);
        seekBar=findViewById(R.id.seekbar);
        pauseplay=findViewById(R.id.pauseplay);
        nextbtn=findViewById(R.id.next);
        previousbtn=findViewById(R.id.previous);
        musicicon=findViewById(R.id.musicicon);
        songslist=(ArrayList<Audiomodel>)getIntent().getSerializableExtra("List");
        setresourceswithmusic();
       Muscicplayeractivity.this.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               currenttimetv.setText(convert(mediaplayer.getCurrentPosition()+""));
           }
       });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaplayer!=null&& b)
                {
                    mediaplayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
    void setresourceswithmusic()
    {
        currentsong=songslist.get(Mediaplayer.currentindex);
        titletv.setText(currentsong.getTitle());//will get title of current song
        titletv.setSelected(true);
        totaltimetv.setText(convert(currentsong.getDuration()));//will give total duration
        pauseplay.setOnClickListener(view -> pauseplay());
        previousbtn.setOnClickListener(view -> previous());
        nextbtn.setOnClickListener(view -> next());
        playmusic();




    }
    @SuppressLint("DefaultLocale")
    public static String convert(String duration)
    {
        Long milis=Long.parseLong(duration);

       return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(milis)%TimeUnit.HOURS.toMinutes(1)
        , TimeUnit.MILLISECONDS.toSeconds(milis)%TimeUnit.MINUTES.toSeconds(1));



    }
    private void playmusic()
    {



        mediaplayer.reset();
        try {
            mediaplayer.setDataSource(currentsong.getPath());
            mediaplayer.prepare();
            mediaplayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaplayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    private void next()
    {
        if(Mediaplayer.currentindex==songslist.size()-1)
        {
            return;
        }
        Mediaplayer.currentindex=rand.nextInt(10);
        mediaplayer.reset();
        setresourceswithmusic();


    }
    private void previous()
    {
        if(Mediaplayer.currentindex==0)
        {
            return;
        }
        Mediaplayer.currentindex-=1;
        mediaplayer.reset();
        setresourceswithmusic();

    }
    private void pauseplay()
    {
        if(mediaplayer.isPlaying())
        {
            mediaplayer.pause();
        }
        else
            mediaplayer.start();

    }
}