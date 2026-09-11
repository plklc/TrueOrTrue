package com.example.trueortrue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private TextView currentPlayerName;
    private TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ArrayList<Player> players = getIntent().getParcelableArrayListExtra("players");

        currentPlayerName = findViewById(R.id.currentPlayerName);
        questionText = findViewById(R.id.questionText);

        Button nextPlayerButton = findViewById(R.id.nextPlayerButton);
        Button nextQuestionButton = findViewById(R.id.nextQuestionButton);

        QuestionRepository questionRepository = new QuestionRepository(this);

        List<Question> questions = questionRepository.getQuestions();

        game = new Game(players, questions);

        updatePlayer();
        showNewQuestion();

        nextPlayerButton.setOnClickListener(v -> {
            game.nextPlayer();

            updatePlayer();
            showNewQuestion();
        });

        nextQuestionButton.setOnClickListener(v -> showNewQuestion());
    }

    private void updatePlayer() {
        currentPlayerName.setText(game.getCurrentPlayer().getName());
    }

    private void showNewQuestion() {
        Question question = game.getRandomQuestion();

        if (question != null) {
            questionText.setText(question.getText());
        }
    }
}
