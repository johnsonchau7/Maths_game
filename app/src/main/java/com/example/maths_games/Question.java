package com.example.maths_games;

import java.util.Random;

public class Question {
    int n1;
    int n2;
    String mode;
    int answer;
    Random random = new Random();

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
}
