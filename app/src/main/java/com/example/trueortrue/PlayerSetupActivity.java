package com.example.trueortrue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerSetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        Button startGameBtn = findViewById(R.id.startGameBtn);

        startGameBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PlayerSetupActivity.this, GameActivity.class);
            startActivity(intent);
        });
    }
}