package com.example.tronine_pc.fliyingfishgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActicity extends AppCompatActivity {

    private Button StarGameAgain;
    private TextView DisplayScore;
    private String score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_acticity);


        score = getIntent().getExtras().get("score").toString();


        StarGameAgain = (Button) findViewById(R.id.play_again_btn);
        DisplayScore = (TextView) findViewById(R.id.displayScore);

        StarGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(GameOverActicity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        DisplayScore.setText("Score : " + score);
    }
}
