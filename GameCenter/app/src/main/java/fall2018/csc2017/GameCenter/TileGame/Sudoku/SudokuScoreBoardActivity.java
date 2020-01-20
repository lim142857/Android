package fall2018.csc2017.GameCenter.TileGame.Sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.UserManager;

public class SudokuScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_score_board);
        ArrayList<String> sudoku = new ArrayList<>();
        ListView lvSliding = findViewById(R.id.SudokuScoreText);

        sudoku.add("Sudoku Game Score Board:");
        addUserScore(sudoku);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SudokuScoreBoardActivity.this,
                android.R.layout.simple_list_item_1, sudoku);

        lvSliding.setAdapter(adapter);
    }

    /**
     * Add score to Scoreboard for slidingTiles.
     *
     * @param slidingTile The List stores the highest scores for this game.
     */
    private void addUserScore(ArrayList<String> slidingTile) {
        UserManager userManager = UserManager.getInstance();
        Score score = userManager.getCurrentUser().getScore();

        for (int j = 0; j < 3; j++) {
            slidingTile.add(score.getScoreBoardPerGameName()[6][j] + "   " +
                    String.valueOf(score.getScoreBoardPerGameScore()[6][j]));
        }
    }
}
