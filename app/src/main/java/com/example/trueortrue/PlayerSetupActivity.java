package com.example.trueortrue;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerSetupActivity extends AppCompatActivity {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 16;

    private final List<Player> players = new ArrayList<>();

    private LinearLayout playersContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        Button startGameButton = findViewById(R.id.startGameButton);
        Button addPlayerButton = findViewById(R.id.addPlayerButton);

        playersContainer = findViewById(R.id.playersContainer);

        addPlayer();
        addPlayer();

        addPlayerButton.setOnClickListener(v -> addPlayer());
        startGameButton.setOnClickListener(v -> startGame());
    }

    private void addPlayer() {
        if (players.size() >= MAX_PLAYERS) {
            return;
        }

        Player player = new Player("");
        players.add(player);

        LinearLayout playerRow = createPlayerRow(player);

        playersContainer.addView(playerRow);
    }

    private LinearLayout createPlayerRow(Player player) {
        LinearLayout playerRow = new LinearLayout(this);

        EditText playerName = createPlayerNameInput(player);
        Button removeButton = createRemoveButton(player, playerRow);

        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
                );

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
                );

        playerRow.addView(playerName, nameParams);
        playerRow.addView(removeButton, buttonParams);

        return playerRow;
    }

    private EditText createPlayerNameInput(Player player) {
        EditText playerName = new EditText(this);

        playerName.setHint("Имя игрока");
        playerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return playerName;
    }

    private Button createRemoveButton(Player player, LinearLayout playerRow) {
        Button removeButton = new Button(this);
        removeButton.setText("✕");
        removeButton.setOnClickListener(v -> removePlayer(player, playerRow));

        return removeButton;
    }

    private void removePlayer(Player player, LinearLayout playerRow) {
        players.remove(player);
        playersContainer.removeView(playerRow);
    }

    private void startGame() {
        if (players.size() < MIN_PLAYERS) {
            return;
        }

        Intent intent = new Intent(PlayerSetupActivity.this, GameActivity.class);
        intent.putParcelableArrayListExtra("players", new ArrayList<>(players));
        startActivity(intent);
    }
}