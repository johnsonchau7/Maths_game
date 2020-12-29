package com.example.maths_games;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

public class Question {
    int n1;
    int n2;
    String mode;
    int answer;

    HashMap<Integer, String> question_type;
    int number_of_question_types;

    Random random = new Random();

    public Question(){
        question_type = new HashMap<Integer, String>();

        question_type.put(0, "+");
        question_type.put(1, "-");
        question_type.put(2, "×");
        question_type.put(3, "÷");

        number_of_question_types = 4;
    }

    public void create_addition_question(String mode){
        this.n1 = random.nextInt(11);
        this.n2 = random.nextInt(11);
        this.mode = mode;
        this.answer = this.n1 + this.n2;
    }

    public void create_subtraction_question(String mode){
        this.n1 = random.nextInt(10) + 1;
        this.n2 = random.nextInt(this.n1);
        this.mode = mode;
        this.answer = this.n1 - this.n2;
    }

    public void create_multiplication_question(String mode){
        this.n1 = random.nextInt(11);
        this.n2 = random.nextInt(11);
        this.mode = mode;
        this.answer = this.n1 * this.n2;
    }

    public void create_division_question(String mode){
        int number_1 = random.nextInt(10) + 1;
        int number_2 = random.nextInt(10) + 1;
        this.n1 = number_1 * number_2;
        this.n2 = number_1;
        this.mode = mode;
        this.answer = this.n1 / this.n2;
    }

    public void generate_random_question(int current_difficulty_int){
        int question_type_number = random.nextInt(current_difficulty_int);;
        String current_question_type = question_type.get(question_type_number);

        if (question_type_number == 0){
            this.create_addition_question(current_question_type);
        }
        else if (question_type_number == 1){
            this.create_subtraction_question(current_question_type);
        }
        else if (question_type_number == 2){
            this.create_multiplication_question(current_question_type);
        }
        else if (question_type_number == 3){
            this.create_division_question(current_question_type);
        }
    }

    public void set_question_text_animation(TextView qs_text){
        String text = String.format("%d %s %d", this.n1, this.mode, this.n2);
        qs_text.setText(text);
    }
}
