package fall2018.csc2017.GameCenter.Hanoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.UserManager;

public class HanoiPerGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tiles_per_game_score);
        ArrayList<String> slidingTile = new ArrayList<>();
        ListView lvSliding = findViewById(R.id.SlidingTilesScoreText);

        // Added the title for the score board
        slidingTile.add("Hanoi Tower Score Board:");
        addUserScore(slidingTile);

        addScoreBoardTitle(slidingTile);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(HanoiPerGameActivity.this,
                android.R.layout.simple_list_item_1, slidingTile);

        // Display the score by connecting it to the adapter
        lvSliding.setAdapter(adapter);
    }

    /**
     * Add score to Scoreboard for slidingTiles.
     *
     * @param hanoi The List stores the highest scores for this game.
     */
    private void addUserScore(ArrayList<String> hanoi) {
        UserManager userManager = UserManager.getInstance();
        Score score = userManager.getCurrentUser().getScore();
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                hanoi.add(score.getScoreBoardPerGameName()[i][j] + "   " +
                        String.valueOf(score.getScoreBoardPerGameScore()[i][j]));
            }
        }
    }

    /**
     * Extract method in order to make it shorter
     *
     * @param slidingTile The list stores the highest scores for this game.
     */
    private void addScoreBoardTitle(ArrayList<String> slidingTile) {
        slidingTile.add(1, "EASY:");
        slidingTile.add(5, "MEDIUM:");
        slidingTile.add(9, "HARD:");
    }
}

