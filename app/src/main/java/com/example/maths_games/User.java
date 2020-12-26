package com.example.maths_games;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;

public class User {
    Integer user_input;
    int user_score = 0;
    int user_goal = 10;
    long user_start_time = 0;
    long user_milli_second_time = 0L;

    String user_win_condition = "Lose";

    double user_game_duration = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String get_user_encoded(){
        String current_game_condition = String.format("gameCondition:%s", this.user_win_condition);
        String current_game_score = String.format("gameScore:%s", this.user_score);
        String current_game_time = String.format("gameTime:%d", this.user_milli_second_time);

        List<String> encoding_strings = Arrays.asList(
                current_game_condition,
                current_game_score,
                current_game_time);

        return String.join(",", encoding_strings);
    }

    public String get_time_string(){
        int seconds = (int) (this.user_milli_second_time / 1000);

        int minutes = seconds / 60;

        seconds = seconds % 60;

        int milli_seconds = (int) (this.user_milli_second_time % 1000);

        String current_time = String.format("%d:%02d:%03d", minutes,
                seconds, milli_seconds);

        return current_time;
    }
}
