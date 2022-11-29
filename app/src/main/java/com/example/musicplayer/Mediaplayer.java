package com.example.musicplayer;

import android.media.MediaPlayer;

public class Mediaplayer {
    static MediaPlayer instance;
   public static MediaPlayer getInstance()
    {
        if(instance==null)//if there is no instance of media player
        {
            instance = new MediaPlayer();
        }

        return instance;
    }
    public static int currentindex=-1;


}
