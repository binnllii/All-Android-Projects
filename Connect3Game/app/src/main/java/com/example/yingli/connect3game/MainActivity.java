package com.example.yingli.connect3game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //

    int activePlayer = 0;

    int[] gameState= {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    boolean gameActive = true;

    public void dropIn(View view){

        ImageView counter = (ImageView) view;



        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameActive) {


            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else if (activePlayer == 1) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).setDuration(300);

            for (int[] winningPositions : winningPositions) {
                if (gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                        gameState[winningPositions[1]] == gameState[winningPositions[2]] &&
                        gameState[winningPositions[0]] != 2) {

                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "yellow";

                    } else {
                        winner = "red";
                    }

                    Toast.makeText(this, "the winner is " + winner, Toast.LENGTH_SHORT).show();

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    winnerTextView.setText("the winner is " + winner);

                    playAgainButton.setVisibility(View.VISIBLE);

                    winnerTextView.setVisibility(View.VISIBLE);

                }

            }
        }

    }

    public void playAgain(View view){


        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);

        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i < gridLayout.getChildCount(); i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for(int i = 0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        activePlayer = 0;


        gameActive = true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
