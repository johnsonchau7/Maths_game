package com.example.maths_games;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Random;
import java.util.*;

import android.view.View;
import android.widget.*;
import java.lang.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment{

    //Stores information about user
    User user1;

    //Stores information about question
    Question qs;
    Random random;
    HashMap<Integer, String> question_type;
    int number_of_question_types;

    //Stores information about mode
    HashMap<String, Integer> difficulty_levels;
    static String current_difficulty;

    //Stores information about mode
    HashMap<String, Integer> rounds_levels;

    //Stores interface variables
    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, button_delete, button_enter;

    String[] game_conditions = {"Win", "Lose"};

    TextView timer;

    Handler handler;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GameFragment() {

        //1. initialise user
        user1 = new User();

        //2. initialise question
        qs = new Question();
        random = new Random();
        question_type = new HashMap<Integer, String>();

        question_type.put(0, "+");
        question_type.put(1, "-");
        question_type.put(2, "×");
        question_type.put(3, "÷");

        number_of_question_types = 4;

        //3. initialise mode
        difficulty_levels = new HashMap<String, Integer>();
        String[] all_difficulties = ModeFragment.get_all_difficulty();

        //Key: mode, Value: bound for integer choice
        difficulty_levels.put(all_difficulties[0], 1);
        difficulty_levels.put(all_difficulties[1], 3);
        difficulty_levels.put(all_difficulties[2], 4);

        current_difficulty = all_difficulties[0];

        rounds_levels = new HashMap<String, Integer>();
        String[] all_rounds = ModeFragment.get_all_rounds();

        rounds_levels.put(all_rounds[0], 5);
        rounds_levels.put(all_rounds[1], 10);
        rounds_levels.put(all_rounds[2], 15);

        handler = new Handler();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
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

        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //1. set up navigation control for finish game
        Button finish_button = (Button) view.findViewById(R.id.finish_button);

        finish_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                stop_game(view);
            }
        });

        //2. get mode from user
        Bundle bundle = getArguments();
        String mode_strings = bundle.getString("mode_to_game");
        HashMap<String, String> mode_hash_map = Parser.decoding_game_outcome(mode_strings);
        String input_difficulty = mode_hash_map.get("gameDifficulty");
        String input_rounds = mode_hash_map.get("gameRounds");

        set_current_difficulty(input_difficulty);
        set_current_rounds(rounds_levels.get(input_rounds));

        //3. set up user interface
        write_user_input(view);
        start_timer(view);

        //4. start game
        set_score(view);
        new_question(view);
    }

    public void new_question(View view){
        //1. create question
        generate_question();

        //2. show question on screen
        set_question(view);

        //3. get answers from user
        get_user_input(view);
    }

    public void generate_question(){

        //1. get question type
        Integer current_difficulty_int = difficulty_levels.get(current_difficulty);
        int question_type_number = random.nextInt(current_difficulty_int);;
        String current_question_type = question_type.get(question_type_number);

        //2. create question
        if (question_type_number == 0){
            qs.create_addition_question(current_question_type);
        }
        else if (question_type_number == 1){
            qs.create_subtraction_question(current_question_type);
        }
        else if (question_type_number == 2){
            qs.create_multiplication_question(current_question_type);
        }
        else if (question_type_number == 3){
            qs.create_division_question(current_question_type);
        }
    }

    public void set_question(View view){
        TextView number1 = (TextView) view.findViewById(R.id.number1);
        TextView number2 = (TextView) view.findViewById(R.id.number2);
        TextView current_mode = (TextView) getView().findViewById(R.id.mode);
        TextView question_difficulty = (TextView) view.findViewById(R.id.question_difficulty_text);

        //1. show question
        number1.setText(String.valueOf(qs.n1));
        current_mode.setText(qs.mode);
        number2.setText(String.valueOf(qs.n2));
        question_difficulty.setText(String.format("Mode: %s", current_difficulty));

    }

    public void get_user_input(View view) {
        TextView user_input = (TextView) view.findViewById(R.id.user_input);
        Button submit_button = (Button) view.findViewById(R.id.enter);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //1. get user input
                String user_input_string = user_input.getText().toString();
                if (user_input_string.isEmpty()){
                    user1.user_input = null;
                }
                else {
                    user1.user_input = Integer.parseInt(user_input_string);
                }

                //2. check answer
                check_answers(view);

                //3. reset input
                user_input.setText("");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void check_answers(View view){
        TextView outcome = (TextView) view.findViewById(R.id.outcome);

        user1.increase_attempts();

        //1. check if answer is correct
        if (user1.user_input!= null && user1.user_input == qs.answer){
            outcome.setText("Correct!");

            //2. add score to user
            user1.user_score += 1;

            //3. check win condition
            check_win_condition(view);

            //4. continue to next question
            set_score(view);
            new_question(view);
        }
        else{
            //continue looping current question if wrong
            outcome.setText("Wrong!");
        }
    }

    public void set_score(View view){
        TextView score = (TextView) view.findViewById(R.id.user_score);
        String score_string = String.format("Score: %d/%d", user1.user_score, user1.user_goal);
        score.setText(score_string);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void check_win_condition(View view){
        if (user1.user_score >= user1.user_goal){
            user1.user_win_condition = game_conditions[0];
        }

        if (user1.user_win_condition == game_conditions[0]){
            stop_game(view);
        }
    }

    public void write_digit(TextView user_input, String s){
        String current_string = user_input.getText().toString();

        //1. write user input
        if (current_string.length() < 4) {
            user_input.setText(user_input.getText() + s);
        }
    }

    public void write_user_input(View view){
        button0 = (Button) view.findViewById(R.id.zero);
        button1 = (Button) view.findViewById(R.id.one);
        button2 = (Button) view.findViewById(R.id.two);
        button3 = (Button) view.findViewById(R.id.three);
        button4 = (Button) view.findViewById(R.id.four);
        button5 = (Button) view.findViewById(R.id.five);
        button6 = (Button) view.findViewById(R.id.six);
        button7 = (Button) view.findViewById(R.id.seven);
        button8 = (Button) view.findViewById(R.id.eight);
        button9 = (Button) view.findViewById(R.id.nine);
        button_delete = (Button) view.findViewById(R.id.delete);
        button_enter = (Button) view.findViewById(R.id.enter);
        TextView user_input = (TextView) view.findViewById(R.id.user_input);

        //1. set up user interface
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { write_digit(user_input,"1"); }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { write_digit(user_input,"2"); }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_digit(user_input,"0");
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_string = user_input.getText().toString();
                String new_string = delete_string(current_string);

                user_input.setText(new_string);
            }
        });
    }

    public String delete_string(String str) {

        //1. delete user input
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static void set_current_difficulty(String difficulty){
        current_difficulty = difficulty;
    }

    public void set_current_rounds(Integer rounds){
        user1.user_goal = rounds;
    }

    public void start_timer(View view){
        user1.user_start_time = SystemClock.uptimeMillis();

        timer = (TextView) view.findViewById(R.id.timer);
        handler.postDelayed(runnable, 0);
    }

    public void end_timer(){
        user1.user_milli_second_time = SystemClock.uptimeMillis() - user1.user_start_time;
        handler.removeCallbacks(runnable);
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            user1.user_milli_second_time = SystemClock.uptimeMillis() - user1.user_start_time;

            String current_time = user1.get_time_string();

            timer.setText(current_time);

            handler.postDelayed(this, 0);
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void stop_game(View view){
        end_timer();

        NavController navController = Navigation.findNavController(view);
        Bundle bundle = new Bundle();

        user1.set_up_statistics();
        String encoding = user1.get_user_encoded();

        bundle.putString("game_to_gameover", encoding);

        navController.navigate(R.id.action_gameFragment_to_gameOverFragment, bundle);
    }
}