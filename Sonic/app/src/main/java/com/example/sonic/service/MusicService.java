package com.example.sonic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sonic.MainActivity;
import com.example.sonic.MyApplication;
import com.example.sonic.R;
import com.example.sonic.model.SongDTO;
import com.example.sonic.network.remote.RetrofitClient;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("viet Service", "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            SongDTO songDTO = (SongDTO) bundle.get("ob_song");
            if (songDTO != null) {
                sendNotification(songDTO);
                startMusic(songDTO);
            }
        }

        return START_NOT_STICKY;
    }

    private void startMusic(SongDTO songDTO) {
        if(mediaPlayer==null){
            String streamUrl=RetrofitClient.url+songDTO.getSound();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());
            try {
                mediaPlayer.setDataSource(streamUrl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(SongDTO song) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.images);
        //
        //
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tv_title_song, song.getName());
        remoteViews.setTextViewText(R.id.tv_single_song, song.getArtistName());
        remoteViews.setImageViewBitmap(R.id.imageViewSong, bitmap);

        remoteViews.setImageViewResource(R.id.img_play_or_pause_notification, R.drawable.ic_pause);
        //
        Notification notification = new Notification.Builder(this, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
//                .setSound(null)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
