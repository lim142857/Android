package fall2018.csc2017.GameCenter.TileGame.SlidingTiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.UserManager;

public class SlidingTilesScoreboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tiles_per_game_score);
        ArrayList<String> slidingTile = new ArrayList<>();
        ListView lvSliding = findViewById(R.id.SlidingTilesScoreText);
        // add title to the score board
        slidingTile.add("Sliding Tile Score Board:");
        addUserScore(slidingTile);

        addScoreBoardTitle(slidingTile);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SlidingTilesScoreboardActivity.this,
                android.R.layout.simple_list_item_1, slidingTile);

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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slidingTile.add(score.getScoreBoardPerGameName()[i][j] + "   " +
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
        slidingTile.add(1, "3x3:");
        slidingTile.add(5, "4x4:");
        slidingTile.add(9, "5x5:");
    }
}
