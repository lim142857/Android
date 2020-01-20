package fall2018.csc2017.GameCenter.TileGame.SlidingTiles;

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
public class SlidingTilesPerUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Object> scoreSlidingTiles = new ArrayList<>();
        User currentUser = UserManager.getInstance().getCurrentUser();
        int[][] scoreBoard = currentUser.getScore().getScoreFinished();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        for (int i = 0; i < 3; i++) {
            for (int j : scoreBoard[i]) {
                scoreSlidingTiles.add(j);
            }
        }
        addScoreBoardTitle(scoreSlidingTiles);
        ListView lvScore = findViewById(R.id.ScorePerUserText);
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(SlidingTilesPerUserActivity.this,
                android.R.layout.simple_list_item_1, scoreSlidingTiles);

        lvScore.setAdapter(adapter);
    }

    /**
     * add Score Board Title
     *
     * @param scoreSlidingTiles Scores of users
     */
    private void addScoreBoardTitle(ArrayList<Object> scoreSlidingTiles) {
        scoreSlidingTiles.add(0, "3x3:");
        scoreSlidingTiles.add(4, "4x4:");
        scoreSlidingTiles.add(8, "5x5:");
    }
}
