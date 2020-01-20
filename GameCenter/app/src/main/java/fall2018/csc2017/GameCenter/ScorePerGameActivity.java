package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.Hanoi.HanoiPerGameActivity;
import fall2018.csc2017.GameCenter.TileGame.SlidingTiles.SlidingTilesScoreboardActivity;
import fall2018.csc2017.GameCenter.TileGame.Sudoku.SudokuScoreBoardActivity;

/**
 * class Score per game activity
 */
public class ScorePerGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_per_game);

        addSlidingTilesButtonListener();
        addHanoiButtonListen();
        addSudokuButtonListen();
    }

    /**
     * Activate the SidingTiles Button.
     */
    private void addSlidingTilesButtonListener() {
        Button slidingTilesButton = findViewById(R.id.SlidingTilesButton);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScorePerGameActivity.this,
                        SlidingTilesScoreboardActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Hanoi Button.
     */
    private void addHanoiButtonListen() {
        Button hanoiButton = findViewById(R.id.HanoiButton);
        hanoiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScorePerGameActivity.this,
                        HanoiPerGameActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Sudoku Button.
     */
    private void addSudokuButtonListen() {
        Button sudokuButton = findViewById(R.id.SudokuButton);
        sudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScorePerGameActivity.this,
                        SudokuScoreBoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
