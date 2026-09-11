package com.example.trueortrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final ArrayList<Player> players;
    private final List<Question> questions;
    private final List<Question> usedQuestions = new ArrayList<>();

    private final Random random = new Random();

    private int currentPlayerIndex = 0;

    public Game(ArrayList<Player> players, List<Question> questions) {
        this.players = players;
        this.questions = questions;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex++;

        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }

        getCurrentPlayer();
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }

        if (usedQuestions.size() >= questions.size()) {
            usedQuestions.clear();
        }

        List<Question> availableQuestions = new ArrayList<>(questions);
        availableQuestions.removeAll(usedQuestions);

        int randomIndex = random.nextInt(availableQuestions.size());
        Question question = availableQuestions.get(randomIndex);

        usedQuestions.add(question);

        return question;
    }
}
