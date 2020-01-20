package fall2018.csc2017.GameCenter.TileGame;

import java.io.Serializable;

/**
 * Manage a board.
 */
public abstract class BoardManager implements Serializable {

    /**
     * Return whether the puzzle is solved.
     *
     * @return whether the puzzle is solved.
     */
    abstract boolean puzzleSolved();

    /**
     * Return whether the tap is valid.
     *
     * @param position the tile to check
     * @return whether the tap is valid.
     */
    abstract boolean isValidTap(int position);

    /**
     * Process a touch at position in the board.
     *
     * @param position the position
     */
    abstract void touchMove(int position);
}