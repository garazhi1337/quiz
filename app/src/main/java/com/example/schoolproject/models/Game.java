package com.example.schoolproject.models;

import java.util.ArrayList;
import java.util.HashMap;

import kotlin.jvm.internal.BooleanSpreadBuilder;

public class Game {
    private String pin;
    private String title;
    private ArrayList<User> players;
    private HashMap<Question, Integer> questions;
    private Boolean isStarted;
}
