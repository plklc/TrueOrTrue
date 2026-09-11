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
    private final List<EditText> playerNameInputs = new ArrayList<>();

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

        playerNameInputs.add(playerName);

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
        int playerIndex = players.indexOf(player);

        players.remove(player);
        playerNameInputs.remove(playerIndex);
        playersContainer.removeView(playerRow);
    }

    private boolean arePlayerNamesValid() {
        boolean isValid = true;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            EditText playerNameInput = playerNameInputs.get(i);

            if (player.getName().trim().isEmpty()) {
                isValid = false;
            } else {
                playerNameInput.setError(null);
            }
        }

        return isValid;
    }

    private void normalizePlayerNames() {
        for (Player player : players) {
            player.normalizeName();
        }
    }

    private void startGame() {
        if (players.size() < MIN_PLAYERS) {
            return;
        }

        if (!arePlayerNamesValid()) {
            return;
        }

        normalizePlayerNames();

        Intent intent = new Intent(PlayerSetupActivity.this, GameActivity.class);
        intent.putParcelableArrayListExtra("players", new ArrayList<>(players));
        startActivity(intent);
    }
}