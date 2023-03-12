package com.example.schoolproject.models;

import java.util.HashMap;

import kotlin.jvm.internal.BooleanSpreadBuilder;

public class Game {
    private String pin;
    private Integer seconds;
    private HashMap<String, Boolean> answerOne;
    private HashMap<String, Boolean> answerTwo;
    private HashMap<String, Boolean> answerThree;
    private HashMap<String, Boolean> answerFour;
    private String questionText;
}
