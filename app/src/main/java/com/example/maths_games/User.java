package com.example.maths_games;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;

public class User {
    Integer user_input;
    int user_score = 0;
    int user_goal = 10;
    String user_win_condition = "Lose";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String get_user_encoded(){
        String current_game_condition = String.format("gameCondition:%s", this.user_win_condition);
        String current_game_score = String.format("gameScore:%s", this.user_score);

        List<String> encoding_strings = Arrays.asList(current_game_condition, current_game_score);
        return String.join(",", encoding_strings);
    }
}
