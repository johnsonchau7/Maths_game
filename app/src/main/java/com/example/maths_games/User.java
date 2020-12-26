package com.example.maths_games;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;

public class User {
    Integer user_input;
    int user_score = 0;
    int user_goal = 10;
    int user_attempts = 0;
    float user_avg_time = 0;
    float user_accuracy = 0;

    long user_start_time = 0;
    long user_milli_second_time = 0L;

    String user_win_condition = "Lose";

    double user_game_duration = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String get_user_encoded(){
        String current_game_condition = String.format("gameCondition:%s", this.user_win_condition);
        String current_game_score = String.format("gameScore:%s", this.user_score);
        String current_game_time = String.format("gameTime:%d", this.user_milli_second_time);
        String current_attempts = String.format("gameAttempts:%d", this.user_attempts);
        String current_avg_time = String.format("gameAvgTime:%.2f", this.user_avg_time);
        String current_user_accuracy = String.format("gameAccuracy:%.2f", this.user_accuracy);

        List<String> encoding_strings = Arrays.asList(
                current_game_condition,
                current_game_score,
                current_game_time,
                current_attempts,
                current_avg_time,
                current_user_accuracy);

//        System.out.printf("%s\n", String.join(",", encoding_strings));
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

    public void increase_attempts(){
        this.user_attempts += 1;
    }

    public void set_up_statistics(){
        if (this.user_score == 0){
            this.user_avg_time = 0;
        }
        else if (this.user_score != 0){
            this.user_avg_time = ((float) (this.user_milli_second_time / this.user_score)) / 1000;
        }

        if (this.user_attempts == 0){
            this.user_accuracy = 0;
        }
        else if (this.user_attempts != 0){
            this.user_accuracy = ((float) this.user_score / (float) this.user_attempts) * 100;
        }
    }
}
