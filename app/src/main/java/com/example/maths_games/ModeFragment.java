package com.example.maths_games;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //stores difficulty levels
    public Spinner spinner_difficulty;
    public Spinner spinner_rounds;

    public static String[] difficulty_level_strings = {"Easy", "Medium", "Hard"};
    public static String[] rounds_strings = {"5 questions", "10 questions", "15 questions"};

    public ModeFragment() {
    }

    public static String[] get_all_difficulty(){
        return difficulty_level_strings;
    }
    public static String[] get_all_rounds(){
        return rounds_strings;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModeFragment newInstance(String param1, String param2) {
        ModeFragment fragment = new ModeFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //1. set up mode selection
        set_up_difficulty_spinner(view);
        set_up_rounds_spinner(view);

        //2. set up navigation controls
        NavController navController = Navigation.findNavController(view);
        Button start_button = (Button) view.findViewById(R.id.ok_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Sound.get_vibration_effect(v);

                //3. send mode selection by user
                Bundle bundle = new Bundle();
                bundle.putString("mode_to_game", get_mode_encoded());

                //4. go to mode selection menu
                navController.navigate(R.id.action_modeFragment_to_gameFragment, bundle);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mode, container, false);
    }

    public void set_up_difficulty_spinner(View view){
        spinner_difficulty = (Spinner) view.findViewById(R.id.difficulty_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, difficulty_level_strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_difficulty.setAdapter(adapter);

    }

    public void set_up_rounds_spinner(View view){
        spinner_rounds = (Spinner) view.findViewById(R.id.rounds_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, rounds_strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rounds.setAdapter(adapter);
    }

    public String get_difficulty_spinner_string(){
        return spinner_difficulty.getSelectedItem().toString();
    }

    public String get_rounds_spinner_string(){
        return spinner_rounds.getSelectedItem().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String get_mode_encoded(){
        String current_difficulty = String.format("gameDifficulty:%s",
                get_difficulty_spinner_string());

        String current_rounds = String.format("gameRounds:%s",
                get_rounds_spinner_string());

        List<String> encoding_strings = Arrays.asList(
                current_difficulty,
                current_rounds);

        return String.join(",", encoding_strings);
    }
}