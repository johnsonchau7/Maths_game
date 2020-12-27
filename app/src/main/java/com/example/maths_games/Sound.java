package com.example.maths_games;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

public class Sound {

    public Sound(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void get_vibration_effect(Context context){
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.cancel();
        vibrator.vibrate(80);
    }

    public static void get_question_correct_effect(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.question_correct);
        mediaPlayer.start();
    }

    public static void get_question_wrong_effect(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.question_wrong);
        mediaPlayer.start();

    }

    public static void get_game_win_effect(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.game_win);
        mediaPlayer.start();
    }

    public static void get_game_over_effect(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.game_over);
        mediaPlayer.setVolume(0.3F, 0.3F);
        mediaPlayer.start();
    }
}
