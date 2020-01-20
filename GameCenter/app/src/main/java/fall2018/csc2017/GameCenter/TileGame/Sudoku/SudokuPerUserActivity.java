package fall2018.csc2017.GameCenter.TileGame.Sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.User;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * class Score per user activity
 */
public class SudokuPerUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        // Initialize an arrayList
        ArrayList<Object> scoreSudoku = new ArrayList<>();
        User user = UserManager.getInstance().getCurrentUser();
        int[][] scoreBoard = user.getScore().getScoreFinished();
        // add things to the list
        for (int j : scoreBoard[6]) {
            scoreSudoku.add(j);
        }
        addScoreBoardTitle(scoreSudoku);
        ListView lvScore = findViewById(R.id.ScorePerUserText);
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(SudokuPerUserActivity.this,
                android.R.layout.simple_list_item_1, scoreSudoku);

        lvScore.setAdapter(adapter);
    }

    /**
     * add Score Board Title
     *
     * @param scoreSlidingTiles Scores of users
     */
    private void addScoreBoardTitle(ArrayList<Object> scoreSlidingTiles) {
        scoreSlidingTiles.add(0, "Sudoku Game Score (in seconds):");
    }
}
