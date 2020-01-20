package fall2018.csc2017.GameCenter.TileGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCenter.Score;
import fall2018.csc2017.GameCenter.TileGame.SlidingTiles.SlidingTilesBoard;
import fall2018.csc2017.GameCenter.User;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * Manage a sliding tiles board, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingTilesBoardManager extends BoardManager implements Serializable {

    /**
     * The sliding tiles board being managed.
     */
    private SlidingTilesBoard slidingTilesBoard;

    /**
     * The current game type.
     */
    private int gameType = UserManager.getInstance().getCurrentGameType();

    /**
     * The number of rows.
     */
    private int numRows = gameType + 3;

    /**
     * The number of columns.
     */
    private int numCols = numRows;

    /**
     * The current user.
     */
    private User user = UserManager.getInstance().getCurrentUser();

    /**
     * Manage a sliding tiles board that has been pre-populated.
     *
     * @param slidingTilesBoard the sliding tiles board
     */
    public SlidingTilesBoardManager(SlidingTilesBoard slidingTilesBoard) {
        this.slidingTilesBoard = slidingTilesBoard;
    }

    /**
     * Manage a new shuffled board.
     */
    SlidingTilesBoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = numRows * numCols;

        // create new tiles
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, size));
        }
        Collections.shuffle(tiles);
        this.slidingTilesBoard = new SlidingTilesBoard(tiles);
    }

    /**
     * Return the current sliding tiles board.
     */
    public SlidingTilesBoard getSlidingTilesBoard() {
        return this.slidingTilesBoard;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    public boolean puzzleSolved() {
        boolean solved = true;
        Score score = user.getScore();
        Iterator<Tile> boardIterator = slidingTilesBoard.iterator();
        // check if the tiles are in row-major order
        for (int i = 1; i != slidingTilesBoard.numTiles() + 1; i++) {
            Tile tempTile = boardIterator.next();
            if (i != tempTile.getId()) {
                solved = false;
            }
        }
        if (solved) {
            score.setScoreFinished(gameType);
            score.setScoreBoardPerGame(gameType, score.getScoreTemp()[gameType], user.getName());
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int row = position / numCols;
        int col = position % numCols;
        int blankId = slidingTilesBoard.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = (row == 0) ? null : slidingTilesBoard.getTile(row - 1, col);
        Tile below = (row == numRows - 1) ? null : slidingTilesBoard.getTile(row + 1, col);
        Tile left = (col == 0) ? null : slidingTilesBoard.getTile(row, col - 1);
        Tile right = (col == numCols - 1) ? null : slidingTilesBoard.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {

        int row = position / numRows;
        int col = position % numCols;
        int blankId = slidingTilesBoard.numTiles();

        // tiles is the blank tile, swap by calling SlidingTilesBoard's swap method.
        Iterator<Tile> boardIterator = slidingTilesBoard.iterator();
        Tile tempTile = boardIterator.next();
        int i = 0;
        if (isValidTap(position)) {
            while (tempTile.getId() != blankId) {
                i++;
                tempTile = boardIterator.next();
            }
        }
        // get the position of touched tile
        int tempRow = i / numRows;
        int tempCol = i % numCols;
        slidingTilesBoard.swapTiles(tempRow, tempCol, row, col);
        int[] swapped_tiles = {tempRow, tempCol, row, col};
        // send data to user.
        user.addSteps(gameType, swapped_tiles);
        user.setBoardTemp(gameType, this);
        user.getScore().setScoreTemp(gameType);
    }

    /**
     * Undo the last step and return true.
     * If no step has done, return false.
     *
     * @return return false if no step has done, otherwise return true.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean undo() {
        ArrayList<int[]> switchesList = user.getSteps().get(gameType);
        int removeLastSteps = switchesList.size() - 1;
        if (removeLastSteps < 0) {
            return false;
        }
        int[] tiles_to_swap = switchesList.remove(removeLastSteps);
        // tile 1 row: tile_to_change[0], tile 1 column: tile_to_change[1],
        // tile 2 row: tile_to_change[2], tile 2 column: tile_to_change[3]
        slidingTilesBoard.swapTiles(tiles_to_swap[0], tiles_to_swap[1], tiles_to_swap[2], tiles_to_swap[3]);
        user.setBoardTemp(gameType, this);
        return true;
    }

    /**
     * Check whether the board is solvable.
     *
     * @return a boolean to tell if the puzzle is solvable.
     */
    public boolean solvable() {
        boolean solved = false;
        int size = numRows;
        int inversionNum = this.getInversionNum();
        if (size % 2 == 1) {
            if (inversionNum % 2 == 0) {
                solved = true;
            }
        } else {
            int position = this.getBlankPosition();
            int row = size - position / numRows;
            if (row % 2 == 1 && inversionNum % 2 == 0 || row % 2 == 0 && inversionNum % 2 == 1) {
                solved = true;
            }
        }
        return solved;
    }

    /**
     * get the inversion number of the board
     *
     * @return an integer: inversion number.
     */
    public int getInversionNum() {
        int inversionNum = 0;
        ArrayList<Tile> Tiles = new ArrayList<>();
        Iterator<Tile> boardIterator = slidingTilesBoard.iterator();
        for (int i = 1; i != slidingTilesBoard.numTiles() + 1; i++) {
            Tile currentTiles = boardIterator.next();
            if (currentTiles.getId() != slidingTilesBoard.numTiles()) {
                Tiles.add(currentTiles);
            }
        }
        for (int j = 0; j < Tiles.size(); j++) {
            Tile tileCurrent = Tiles.get(j);
            for (int k = j + 1; k < Tiles.size(); k++) {
                Tile tileCheck = Tiles.get(k);
                if (tileCurrent.getId() > tileCheck.getId()) {
                    inversionNum++;
                }
            }
        }
        return inversionNum;
    }

    /**
     * get the blank tile position.
     *
     * @return and int representing the blank tile position.
     */
    public int getBlankPosition() {
        int position = 0;
        Iterator<Tile> boardIterator = slidingTilesBoard.iterator();
        for (int i = 0; i < slidingTilesBoard.numTiles(); i++) {
            Tile currentTile = boardIterator.next();
            if (currentTile.getId() == slidingTilesBoard.numTiles()) {
                break;
            }
            position++;
        }
        return position;
    }
}