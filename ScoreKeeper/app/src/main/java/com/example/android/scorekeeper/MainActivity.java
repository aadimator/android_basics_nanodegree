package com.example.android.scorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreA;
    int scoreB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreA = 0;
        scoreB = 0;

        updateScoreTextView();
        updateWinnerTextView();
    }

    private void updateWinnerTextView() {
        String winner = "";
        if (scoreA > scoreB) winner = getString(R.string.team_a);
        else if (scoreA < scoreB) winner = getString(R.string.team_b);
        else winner = getString(R.string.team_a) + " and " + getString(R.string.team_b);
        String message = String.format(getString(R.string.winningTeam), winner);

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        winnerTextView.setText(message);
    }

    private void updateScoreTextView() {
        TextView scoreATextView = (TextView) findViewById(R.id.teamAScoreValue);
        TextView scoreBTextView = (TextView) findViewById(R.id.teamBScoreValue);
        scoreATextView.setText(scoreA + "");
        scoreBTextView.setText(scoreB + "");
    }

    public void handleClick (View view) {
        switch (view.getId()) {
            case R.id.teamAScore1:
                addScoreA(1);
                break;
            case R.id.teamAScore2:
                addScoreA(2);
                break;
            case R.id.teamAScore3:
                addScoreA(3);
                break;
            case R.id.teamAScore4:
                addScoreA(4);
                break;
            case R.id.teamAScore5:
                addScoreA(5);
                break;
            case R.id.teamAScore6:
                addScoreA(6);
                break;
            case R.id.teamBScore1:
                addScoreB(1);
                break;
            case R.id.teamBScore2:
                addScoreB(2);
                break;
            case R.id.teamBScore3:
                addScoreB(3);
                break;
            case R.id.teamBScore4:
                addScoreB(4);
                break;
            case R.id.teamBScore5:
                addScoreB(5);
                break;
            case R.id.teamBScore6:
                addScoreB(6);
                break;
        }
    }

    private void addScoreA(int score) {
        scoreA += score;
        updateScoreTextView();
        updateWinnerTextView();

    }

    private void addScoreB(int score) {
        scoreB += score;
        updateScoreTextView();
        updateWinnerTextView();
    }

    public void resetScores(View view) {
        scoreA = 0;
        scoreB = 0;

        updateScoreTextView();
        updateWinnerTextView();
    }

}
