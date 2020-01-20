package fall2018.csc2017.GameCenter.TileGame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * class Help activity
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int gameType = UserManager.getInstance().getCurrentGameType();
        if (gameType == 6) {
            setContentView(R.layout.activity_sudoku_help);
        } else {
            setContentView(R.layout.activity_slidingtiles_help);
        }

    }
}
