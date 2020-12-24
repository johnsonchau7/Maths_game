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

    public HashMap<String, String> decoding_game_outcome(String game_outcome){
        HashMap<String, String> decoded_outcome = new HashMap<String, String>();
        String[] game_outcome_key_values = game_outcome.split(",");

        for (String key_value_pair : game_outcome_key_values){
            String[] key_value_split = key_value_pair.split(":");
            decoded_outcome.put(key_value_split[0], key_value_split[1]);
        }

        return decoded_outcome;
    }

    public void set_game_outcome(View view, String game_outcome){
        HashMap<String, String> decoded_outcome = decoding_game_outcome(game_outcome);

        TextView current_game_outcome = (TextView) view.findViewById(R.id.game_outcome_text);
        current_game_outcome.setText(decoded_outcome.get("gameCondition"));

        TextView current_game_score = (TextView) view.findViewById(R.id.score_outcome_text);
        current_game_score.setText(decoded_outcome.get("gameScore"));
    }
}