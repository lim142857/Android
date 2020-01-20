package fall2018.csc2017.GameCenter.TileGame.Sudoku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Chronometer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.TileGame.BoardManager;
import fall2018.csc2017.GameCenter.TileGame.CustomAdapter;
import fall2018.csc2017.GameCenter.TileGame.GestureDetectGridView;
import fall2018.csc2017.GameCenter.TileGame.HelpActivity;
import fall2018.csc2017.GameCenter.TileGame.StartingActivity;
import fall2018.csc2017.GameCenter.TileGame.SudokuBoardManager;
import fall2018.csc2017.GameCenter.User;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * The game activity for sudoku.
 */
public class SudokuGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SudokuBoardManager boardManager;

    /**
     * The current user.
     */
    private User user = UserManager.getInstance().getCurrentUser();

    /**
     * The current temporary board managers
     */
    private BoardManager[] boardTemp = user.getBoardTemp();

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size.
     */
    private GestureDetectGridView gridView;

    /**
     * The width and height of columns
     */
    private static int columnWidth, columnHeight;

    /**
     * The chronometer used in Sudoku game.
     */
    private Chronometer chronometer;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int gameType = UserManager.getInstance().getCurrentGameType();
        int[] scoreTemp = user.getScore().getScoreTemp();
        int time = scoreTemp[gameType];
        boardManager = (SudokuBoardManager) boardTemp[gameType];
        createTileButtons(this);
        setContentView(R.layout.activity_sudoku_main);

        // chronometer, the timing tool
        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime() - time * 1000);
        chronometer.start();

        //the button of sudoku
        addUndoButtonListener();
        addHelpButtonListener();
        addOneButtonListener();
        addTwoButtonListener();
        addThreeButtonListener();
        addFourButtonListener();
        addFiveButtonListener();
        addSixButtonListener();
        addSevenButtonListener();
        addEightButtonListener();
        addNineButtonListener();
        addClearButtonListener();

        // Add View to activity
        addViewToActivity();
        // Observer sets up desired dimensions as well as calls our display function
        setUpDimensions();
    }

    /**
     * Set up dimensions.
     */
    private void setUpDimensions() {
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / 9;
                        columnHeight = displayHeight / 9;

                        display();
                    }
                });
    }

    /**
     * Add view to activity.
     */
    private void addViewToActivity() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(9);
        gridView.setBoardManager(boardManager);
        boardManager.getSudokuBoard().addObserver(this);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SudokuBoard board = boardManager.getSudokuBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != 9; row++) {
            for (int col = 0; col != 9; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SudokuBoard board = boardManager.getSudokuBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / 9;
            int col = nextPos % 9;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
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

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Activate the Undo Button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boardManager.undo()) {
                    Toast.makeText(getApplicationContext(), "No step to undo!",
                            Toast.LENGTH_SHORT).show();
                }
                saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
            }
        });
    }

    /**
     * Activate the Help Button.
     */
    private void addHelpButtonListener() {
        Button helpButton = findViewById(R.id.HelpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SudokuGameActivity.this,
                        HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Change the tile number after touching the empty tile and then choose a number.
     *
     * @param num the number that is given to empty tile.
     */
    private void moveTile(int num) {
        SudokuBoardManager currentBoardManager = (SudokuBoardManager) (boardTemp[6]);
        Score score = user.getScore();

        if (currentBoardManager.isValidTap(num)) {
            currentBoardManager.touchMove(num);
            int time = getChronometerSeconds(chronometer);
            score.setSudokuScoreTemp(6, time);
            if (currentBoardManager.puzzleSolved()) {
                Toast.makeText(getApplicationContext(), "YOU WIN!", Toast.LENGTH_SHORT).show();
                score.setScoreFinished(6);
                score.setScoreBoardPerGame(6, score.getScoreTemp()[6], user.getName());
            } else {
                user.setBoardTemp(6, boardManager);
            }
        } else {
            check(currentBoardManager);
        }
    }

    /**
     * This method is extracted from method moveTile to shorter the class.Display the warning text durto invalid tap
     *
     * @param boardManager The board manager we are dealing with.
     */
    private void check(SudokuBoardManager boardManager) {
        String text;

        if (!boardManager.getHasTileDisplay()) {
            text = "has not click tile";
        } else {
            int row = boardManager.getPositionDisplay()[0];
            int col = boardManager.getPositionDisplay()[1];
            if (!boardManager.getSudokuBoard().getTile(row, col).getChangeable()) {
                text = "tile unchangeable";
            } else {
                text = "already has this number";
            }
        }
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Get the time shown on the chronometer.
     *
     * @param cmt the chronometer used in sudoku game.
     * @return the number of seconds
     */
    private int getChronometerSeconds(Chronometer cmt) {
        int totalSecs;
        int hourInSec = 0, minInSec = 0, sec = 0;
        String string = cmt.getText().toString();
        String[] split = string.split(":");

        if (string.length() == 7) {
            hourInSec = Integer.parseInt(split[0]) * 3600;
            minInSec = Integer.parseInt(split[1]) * 60;
            sec = Integer.parseInt(split[2]);
        } else if (string.length() == 5) {
            minInSec = Integer.parseInt(split[0]) * 60;
            sec = Integer.parseInt(split[1]);
        }
        totalSecs = hourInSec + minInSec + sec;
        return totalSecs;
    }

    /**
     * Activate the One Button.
     */
    private void addOneButtonListener() {
        Button oneButton = findViewById(R.id.OneButton);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(1);

            }
        });
    }

    /**
     * Activate the Two Button.
     */
    private void addTwoButtonListener() {
        Button twoButton = findViewById(R.id.TwoButton);
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(2);

            }
        });
    }

    /**
     * Activate the Three Button.
     */
    private void addThreeButtonListener() {
        Button threeButton = findViewById(R.id.ThreeButton);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(3);

            }
        });
    }

    /**
     * Activate the Four Button.
     */
    private void addFourButtonListener() {
        Button fourButton = findViewById(R.id.FourButton);
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(4);

            }
        });
    }

    /**
     * Activate the Five Button.
     */
    private void addFiveButtonListener() {
        Button fiveButton = findViewById(R.id.FiveButton);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(5);

            }
        });
    }

    /**
     * Activate the Six Button.
     */
    private void addSixButtonListener() {
        Button sixButton = findViewById(R.id.SixButton);
        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(6);

            }
        });
    }

    /**
     * Activate the Seven Button.
     */
    private void addSevenButtonListener() {
        Button sevenButton = findViewById(R.id.SevenButton);
        sevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(7);
            }
        });
    }

    /**
     * Activate the Eight Button.
     */
    private void addEightButtonListener() {
        Button eightButton = findViewById(R.id.EightButton);
        eightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(8);
            }
        });
    }

    /**
     * Activate the Nine Button.
     */
    private void addNineButtonListener() {
        Button nineButton = findViewById(R.id.NineButton);
        nineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(9);
            }
        });
    }

    /**
     * Activate the Clear Button
     */
    private void addClearButtonListener() {
        Button oneButton = findViewById(R.id.ClearButton);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTile(25);
            }
        });
    }
}