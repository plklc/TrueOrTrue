package com.example.trueortrue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerSetupActivity extends AppCompatActivity {

    private LinearLayout playersContainer;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 16;

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

        if (playersContainer.getChildCount() >= MAX_PLAYERS) {
            return;
        }

        LinearLayout playerRow = new LinearLayout(this);

        EditText playerName = new EditText(this);
        playerName.setHint("Имя игрока");

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

        removeBtn.setOnClickListener(v -> playersContainer.removeView(playerRow));

        playersContainer.addView(playerRow);
    }

    private void startGame() {

        if (playersContainer.getChildCount() < MIN_PLAYERS) {
            return;
        }

        Intent intent = new Intent(PlayerSetupActivity.this, GameActivity.class);
        startActivity(intent);
    }
}