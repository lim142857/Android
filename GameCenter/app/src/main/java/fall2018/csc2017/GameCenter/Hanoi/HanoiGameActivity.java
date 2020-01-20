package fall2018.csc2017.GameCenter.Hanoi;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.TileGame.StartingActivity;
import fall2018.csc2017.GameCenter.UserManager;

@SuppressLint("Registered")
public class HanoiGameActivity extends Activity {
    /**
     * The number of total moves and the minimum possible moves in this game.
     */
    private int totalMoves, minPossibleMoves;

    /**
     * The current user manager
     */
    private UserManager userManager = UserManager.getInstance();


    public void onCreate(Bundle b) {
        super.onCreate(b);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        // Get the devices' width and height
        if (getIntent().getExtras() != null) {
            setContentView(new Draw(this, displaymetrics.widthPixels,
                    displaymetrics.heightPixels, getIntent().getExtras().getInt("numofdisks")));
        }

        // Possible min moves (2^n - 1); n number of disks
        minPossibleMoves = new BigDecimal(2).pow(
                getIntent().getExtras().getInt("numofdisks")).intValue() - 1;
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

    /**
     * Indicate the player that the game is already over
     *
     * @param moves User's total moves to finish the game
     */
    public void gameOver(int moves) {
        totalMoves = moves;
        Score score = userManager.getCurrentUser().getScore();
        int gameType = UserManager.getInstance().getCurrentGameType();

        // Set the score board per user
        score.setScoreFinished(gameType);

        // Set the score board for Hanoi Tower.
        score.setScoreBoardPerGame(gameType,
                score.getScoreTemp()[gameType],
                userManager.getCurrentUser().getName());

        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        showDialog();
    }

    /**
     * Show the message when the game is over.
     */
    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("You win!");

        if (totalMoves > minPossibleMoves)
            alert.setMessage("Least possible moves are " + minPossibleMoves
                    + ", you made " + totalMoves + ".");
        else
            alert.setMessage("Congratulations! You finished the game with least possible moves: "
                    + minPossibleMoves);

        alert.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.create().show();
    }
}