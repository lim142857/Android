package fall2018.csc2017.GameCenter.TileGame.SlidingTiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.TileGame.BoardManager;
import fall2018.csc2017.GameCenter.TileGame.CustomAdapter;
import fall2018.csc2017.GameCenter.TileGame.GestureDetectGridView;
import fall2018.csc2017.GameCenter.TileGame.HelpActivity;
import fall2018.csc2017.GameCenter.TileGame.SlidingTilesBoardManager;
import fall2018.csc2017.GameCenter.TileGame.StartingActivity;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {


    /**
     * The width and height of columns
     */
    private static int columnWidth, columnHeight;

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager boardManager;

    /**
     * The current user manager
     */
    private UserManager userManager = UserManager.getInstance();

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The number of rows of the board
     */
    private int numRows = UserManager.getInstance().getCurrentGameType() + 3;

    /**
     * The number of columns of the board
     */
    private int numCols = numRows;

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
        BoardManager[] boardTemp = userManager.getCurrentUser().getBoardTemp();
        int gameType = UserManager.getInstance().getCurrentGameType();
        boardManager = (SlidingTilesBoardManager) boardTemp[gameType];

        createTileButtons(this);
        setContentView(R.layout.activity_main);

        addUndoButtonListener();
        addHelpButtonListener();

        // Add View to activity
        addViewToActivity();
        // Observer sets up desired dimensions as well as calls our display function
        setUpDimensions();
    }

    /**
     * Set up dimensions
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

                        columnWidth = displayWidth / numCols;
                        columnHeight = displayHeight / numRows;

                        display();
                    }
                });
    }

    /**
     * Add view to activity.
     */
    private void addViewToActivity() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(numCols);
        gridView.setBoardManager(boardManager);
        boardManager.getSlidingTilesBoard().addObserver(this);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
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
        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / numRows;
            int col = nextPos % numCols;
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
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userManager.getUserList());
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
                Intent intent = new Intent(SlidingTilesGameActivity.this,
                        HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}