package com.example.trueortrue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Player> players;
    private int currentPlayerIndex = 0;
    private List<Question> questions;
    private final List<Question> usedQuestions = new ArrayList<>();
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        players = getIntent().getParcelableArrayListExtra("players");

        TextView currentPlayerName = findViewById(R.id.currentPlayerName);
        Button nextPlayerButton = findViewById(R.id.nextPlayerButton);
        Button nextQuestionButton = findViewById(R.id.nextQuestionButton);
        TextView questionText = findViewById(R.id.questionText);

        QuestionRepository questionRepository = new QuestionRepository(this);

        questions = questionRepository.getQuestions();

        if (players != null && !players.isEmpty()) {
            currentPlayerName.setText(players.get(currentPlayerIndex).getName());
        }

        showRandomQuestion(questionText);

        nextPlayerButton.setOnClickListener(v -> {
            currentPlayerIndex++;

            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }

            currentPlayerName.setText(players.get(currentPlayerIndex).getName());

            showRandomQuestion(questionText);
        });

        nextQuestionButton.setOnClickListener(v -> showRandomQuestion(questionText));
    }

    private void showRandomQuestion(TextView questionText) {

        if (questions.isEmpty()) {
            return;
        }

        if (usedQuestions.size() >= questions.size()) {
            usedQuestions.clear();
        }

        List<Question> availableQuestions = new ArrayList<>(questions);
        availableQuestions.removeAll(usedQuestions);

        int randomIndex = random.nextInt(availableQuestions.size());
        Question question = availableQuestions.get(randomIndex);

        usedQuestions.add(question);

        questionText.setText(question.getText());
    }
}
