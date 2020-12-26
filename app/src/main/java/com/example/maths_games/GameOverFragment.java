package com.example.maths_games;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameOverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameOverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameOverFragment newInstance(String param1, String param2) {
        GameOverFragment fragment = new GameOverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_over, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        Button start_button = (Button) view.findViewById(R.id.home_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_gameOverFragment_to_startFragment);
            }
        });

        //2. get mode from user
        Bundle bundle = getArguments();
        String game_outcome = bundle.getString("game_to_gameover");
        set_game_outcome(view, game_outcome);
    }

    public void set_game_outcome(View view, String game_outcome){
        HashMap<String, String> decoded_outcome = Parser.decoding_game_outcome(game_outcome);

        set_game_condition_string(decoded_outcome.get("gameCondition"));

        set_game_score_string(decoded_outcome.get("gameScore"));

        set_game_time_string(decoded_outcome.get("gameTime"));
    }

    public String long_to_string_time_converter(String time_string){
        long user_milli_second_time = Long.parseLong(time_string);

        int seconds = (int) (user_milli_second_time / 1000);

        int minutes = seconds / 60;

        seconds = seconds % 60;

        int milli_seconds = (int) (user_milli_second_time % 1000);

        String current_time = String.format("%d:%02d:%03d", minutes,
                seconds, milli_seconds);

        return current_time;
    }

    public void set_game_condition_string(String game_condition){
        TextView current_game_outcome = (TextView) getView().findViewById(R.id.game_outcome_text);
        current_game_outcome.setText(game_condition);
    }

    public void set_game_score_string(String game_score){
        TextView current_game_score = (TextView) getView().findViewById(R.id.score_outcome_text);
        current_game_score.setText(String.format("Score: %s", game_score));
    }

    public void set_game_time_string(String game_time){
        TextView current_game_time = (TextView) getView().findViewById(R.id.time_outcome_text);
        String current_time = long_to_string_time_converter(game_time);
        current_game_time.setText(String.format("Time: %s", current_time));
    }
}