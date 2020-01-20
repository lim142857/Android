package fall2018.csc2017.GameCenter.TileGame;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.GameCenter.UserManager;


class MovementController {
    /**
     * The board manager.
     */
    private BoardManager boardManager = null;

    /**
     * An empty constructor given by professor in the original slidingTiles code.
     */
    MovementController() {
    }

    /**
     * Set initial value to the board manager.
     *
     * @param boardManager The board manager we are dealing with.
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process movement after touching a tile depending on the type of the game
     *
     * @param context  the context of xml file
     * @param position represent the row and col of the touched tile
     */
    void processTapMovement(Context context, int position) {
        int gameType = UserManager.getInstance().getCurrentGameType();
        if (gameType == 6) {
            int row = position / 9;
            int column = position % 9;
            int[] positionList = {row, column};
            ((SudokuBoardManager) this.boardManager).setPositionDisplay(positionList);
            ((SudokuBoardManager) this.boardManager).setHasTileDisplay();
        } else {
            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
                if (boardManager.puzzleSolved()) {
                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
