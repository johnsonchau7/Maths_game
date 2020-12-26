package com.example.maths_games;

import java.util.HashMap;

public class Parser {
    
    public Parser(){

    }

    public static HashMap<String, String> decoding_game_outcome(String game_outcome){
        HashMap<String, String> decoded_outcome = new HashMap<String, String>();
        String[] game_outcome_key_values = game_outcome.split(",");

        for (String key_value_pair : game_outcome_key_values){
            String[] key_value_split = key_value_pair.split(":");
            decoded_outcome.put(key_value_split[0], key_value_split[1]);
        }

        return decoded_outcome;
    }
}
