package fall2018.csc2017.GameCenter.Hanoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * class Score per user activity
 */
public class HanoiScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserManager userManager = UserManager.getInstance();
        Score score = userManager.getCurrentUser().getScore();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        ArrayList<Object> scoreHanoi = new ArrayList<>();
        int[][] scoreBoard = score.getScoreFinished();
        for (int i = 3; i < 6; i++) {
            for (int j : scoreBoard[i]) {
                scoreHanoi.add(j);
            }
        }
        addScoreBoardTitle(scoreHanoi);
        ListView lvScore = findViewById(R.id.ScorePerUserText);
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(HanoiScore.this,
                android.R.layout.simple_list_item_1, scoreHanoi);

        lvScore.setAdapter(adapter);
    }

    /**
     * add Score Board Title
     *
     * @param scoreSlidingTiles Scores of users
     */
    private void addScoreBoardTitle(ArrayList<Object> scoreSlidingTiles) {
        scoreSlidingTiles.add(0, "Easy:");
        scoreSlidingTiles.add(4, "Medium:");
        scoreSlidingTiles.add(8, "Hard:");
    }
}