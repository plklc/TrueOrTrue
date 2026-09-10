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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        Button startGameBtn = findViewById(R.id.startGameBtn);
        Button addPlayerButton = findViewById(R.id.addPlayerBtn);
        playersContainer = findViewById(R.id.playersContainer);

        addPlayer();
        addPlayer();

        addPlayerButton.setOnClickListener(v -> addPlayer());
        startGameBtn.setOnClickListener(v -> startGame());
    }

    private void addPlayer() {
        //todo
        /*
        вынести в отдельный метод удаление игроков
        заблокировать возможность удалить 2 изнчальных игроков
        */

        if (players.size() >= MAX_PLAYERS) {
            return;
        }

        LinearLayout playerRow = new LinearLayout(this);

        EditText playerName = new EditText(this);
        playerName.setHint("Имя игрока");
        Player player = new Player("");
        players.add(player);

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

        Button removeBtn = new Button(this);
        removeBtn.setText("✕");

        LinearLayout.LayoutParams nameParams =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        LinearLayout.LayoutParams btnParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        playerRow.addView(playerName, nameParams);
        playerRow.addView(removeBtn, btnParams);

        removeBtn.setOnClickListener(v -> {
            playersContainer.removeView(playerRow);
            players.remove(player);
        });

        playersContainer.addView(playerRow);
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