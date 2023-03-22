package com.example.schoolproject.models;

import java.util.ArrayList;
import java.util.HashMap;

import kotlin.jvm.internal.BooleanSpreadBuilder;

public class Game {
    private String pin;
    private String title;
    //private ArrayList<User> players;
    private HashMap<String, Question> questions;
    private Boolean isStarted;
    private String author;

    public HashMap<String, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, Question> questions) {
        this.questions = questions;
    }

    public Game(String pin, String title, HashMap<String, Question> questions, Boolean isStarted, String author) {
        this.pin = pin;
        this.title = title;
        this.questions = questions;
        this.isStarted = isStarted;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public Game() {
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }
}
