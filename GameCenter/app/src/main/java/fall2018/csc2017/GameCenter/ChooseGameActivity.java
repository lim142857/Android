package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.Hanoi.StartActivity;
import fall2018.csc2017.GameCenter.TileGame.SlidingTiles.SelectDifficultyActivity;
import fall2018.csc2017.GameCenter.TileGame.StartingActivity;

public class ChooseGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        addSlidingTilesButtonListener();
        addSudokuButtonListener();
        addScoreButtonListener();
        addSignOutButtonListener();
        addHanoiButtonListener();

        // display welcome words
        ArrayList<String> userName = getStrings();
        ListView lvSliding = findViewById(R.id.WelcomeUser);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ChooseGameActivity.this,
                android.R.layout.simple_list_item_1, userName);
        lvSliding.setAdapter(adapter);
    }

    @NonNull
    private ArrayList<String> getStrings() {
        ArrayList<String> userName = new ArrayList<>();
        User currentUser = UserManager.getInstance().getCurrentUser();
        userName.add("Welcome, " + currentUser.getName());
        userName.add("Please choose a game from the following game list:");
        return userName;
    }

    /**
     * Activate SidingTiles Button
     */
    private void addSlidingTilesButtonListener() {
        Button slidingTilesButton = findViewById(R.id.SlidingTilesButton);
        slidingTilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGameActivity.this,
                        SelectDifficultyActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
     * Activate Sudoku Button
     */
    private void addSudokuButtonListener() {
        Button sudokuButton = findViewById(R.id.SudokuButton);
        sudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager userManager = UserManager.getInstance();
                Intent intent = new Intent(ChooseGameActivity.this,
                        StartingActivity.class);
                startActivity(intent);
                userManager.setCurrentGameType(6);
            }
        });
    }


    /**
     * Activate the Scoreboard Button.
     */
    private void addScoreButtonListener() {
        Button scoreButton = findViewById(R.id.ScoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGameActivity.this,
                        ScorePerGameActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Sign Out Button.
     */
    private void addSignOutButtonListener() {
        Button signOutButton = findViewById(R.id.SignOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGameActivity.this,
                        SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Hanoi Button.
     */
    private void addHanoiButtonListener() {
        Button hanoiButton = findViewById(R.id.HanoiButton);
        hanoiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGameActivity.this,
                        StartActivity.class);
                startActivity(intent);

            }
        });
    }
}
