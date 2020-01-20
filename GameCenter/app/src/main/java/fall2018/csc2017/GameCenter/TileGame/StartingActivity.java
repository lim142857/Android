package fall2018.csc2017.GameCenter.TileGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import fall2018.csc2017.GameCenter.ChooseGameActivity;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.TileGame.SlidingTiles.SlidingTilesPerUserActivity;
import fall2018.csc2017.GameCenter.TileGame.SlidingTiles.SelectDifficultyActivity;
import fall2018.csc2017.GameCenter.TileGame.SlidingTiles.SlidingTilesGameActivity;
import fall2018.csc2017.GameCenter.TileGame.Sudoku.SudokuGameActivity;
import fall2018.csc2017.GameCenter.TileGame.Sudoku.SudokuPerUserActivity;
import fall2018.csc2017.GameCenter.User;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * The initial activity for tile games.
 */
public class StartingActivity extends AppCompatActivity {
    /**
     * A temporary save file.
     */
    public final static String TEMP_SAVE_FILENAME = "save_file_temp.ser";
    /**
     * The different size of board
     */
    private int size = UserManager.getInstance().getCurrentGameType() + 3;
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * The current user
     */
    private User user = UserManager.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addScoreButtonListener();
        addBackButtonListener();

        ArrayList<String> gameIntro = new ArrayList<>();
        setGameIntro(gameIntro);

        ListView lvScore = findViewById(R.id.GameIntroText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(StartingActivity.this,
                android.R.layout.simple_list_item_1, gameIntro);

        lvScore.setAdapter(adapter);
    }

    /**
     * Set the correct game introduction to gameIntro.
     *
     * @param gameIntro the game introduction.
     */
    private void setGameIntro(ArrayList<String> gameIntro) {
        if (size - 3 == 6) {
            gameIntro.add(0, "Welcome To Sudoku \n\nA Puzzle Game to fill a 9×9 " +
                    "grid with digits so that each column, each row, and each of the nine " +
                    "3×3 subgrids that compose the grid contains all of the digits from 1 to 9.");
        } else {
            gameIntro.add(0, "Welcome To Sliding Tiles \n\nA Puzzle Game where " +
                    "you must arrange the numbers in the correct order.");
        }
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gameType = size - 3;

                setBoardManager();
                user.setBoardTemp(gameType, boardManager);
                user.getScore().resetScoreTemp(gameType);
                user.resetSteps(gameType);
                switchToGame();
            }
        });
    }

    /**
     * since we put two games(slidingTiles and sudoku) in the same starting activity,
     * we have to distinguish which board manager should be used.
     */
    private void setBoardManager() {
        if (size == 9) {
            boardManager = new SudokuBoardManager();
        } else {
            SlidingTilesBoardManager slidingTilesBoardManager = new SlidingTilesBoardManager(size);
            // To make sure the sliding tile game is solvable.
            while (!slidingTilesBoardManager.solvable()) {
                slidingTilesBoardManager = new SlidingTilesBoardManager(size);
            }
            boardManager = slidingTilesBoardManager;
        }
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getBoardTemp()[size - 3] == null) {
                    Toast.makeText(getApplicationContext(), "No Previous State!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    saveToFile(TEMP_SAVE_FILENAME);
                    makeToastLoadedText();
                    switchToGame();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp;
        if (size == 9) {
            tmp = new Intent(this, SudokuGameActivity.class);
        } else {
            tmp = new Intent(this, SlidingTilesGameActivity.class);
        }
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Save the user list to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        Map<String, User> userList = UserManager.getInstance().getUserList();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Activate the Score board button.
     */
    private void addScoreButtonListener() {
        Button scoreButton = findViewById(R.id.ScoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size == 9) {
                    Intent intent = new Intent(StartingActivity.this,
                            SudokuPerUserActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(StartingActivity.this,
                            SlidingTilesPerUserActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Activate the Back Button.
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
                Intent intent;
                if (size == 9) {
                    intent = new Intent(StartingActivity.this,
                            ChooseGameActivity.class);
                } else {
                    intent = new Intent(StartingActivity.this,
                            SelectDifficultyActivity.class);
                }
                startActivity(intent);
            }
        });
    }
}
