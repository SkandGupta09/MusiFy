package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class musiclistadapter extends RecyclerView.Adapter<musiclistadapter.Viewholder>
    {
        public musiclistadapter(ArrayList<Audiomodel> songslist, Context context) {
            this.songslist = songslist;
            this.context = context;
        }

        ArrayList<Audiomodel> songslist;
        Context context;

        @Override
        public Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.recycleritem,parent,false);
            return new musiclistadapter.Viewholder(view);

        }

        @Override
        public void onBindViewHolder(Viewholder holder, @SuppressLint("RecyclerView") int position) {
            Audiomodel songdata=songslist.get(position);
            holder.textView.setText(songdata.getTitle());
            if(Mediaplayer.currentindex==position)
            {
                holder.textView.setTextColor(Color.parseColor("#FF0000"));
            }
            else
            {
                holder.textView.setTextColor(Color.parseColor("#000000"));

            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Mediaplayer.getInstance();
                    Mediaplayer.currentindex=position;
                    Intent intent=new Intent(context,Muscicplayeractivity.class);
                    intent.putExtra("List",songslist);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);



                }
            });

        }

        @Override
        public int getItemCount() {
            return songslist.size();
        }

        class Viewholder extends RecyclerView.ViewHolder
        {
            TextView textView;
            ImageView icon;
            public  Viewholder (View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.musictitle);
                icon=itemView.findViewById(R.id.icon);
            }
        }
    }



