package com.example.maths_games;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.LinearInterpolator;
import android.widget.*;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();
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
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //1. set up navigation control for start menu
        NavController navController = Navigation.findNavController(view);
        Button start_button = (Button) view.findViewById(R.id.start_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Sound.get_vibration_effect(v);

                //2. go to mode selection menu
                navController.navigate(R.id.action_startFragment_to_modeFragment);
            }
        });

        animate_falling_text(view);
    }

    public void animate_falling_text(View view){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        int min_x = 0 + 50;
        int max_x = width - 350;

        TextView falling_text = (TextView) view.findViewById(R.id.falling_text);

        falling_text.setX(100f);
        falling_text.setY(-100f);

        ObjectAnimator animation = ObjectAnimator.ofFloat(falling_text,
                "translationY", height + 200f);

        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(animation.INFINITE);
        animation.setRepeatMode(animation.RESTART);

        animation.setDuration(5000);
        animation.start();

        Question qs = new Question();

        animation.addListener(new Animator.AnimatorListener() {
            Random random = new Random();

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                int i = random.nextInt(max_x - min_x) + min_x;
                falling_text.setX((float) i);
                falling_text.setY(-100f);

                qs.generate_random_question(4);
                qs.set_question_text_animation(falling_text);
            }
        });
    }
}