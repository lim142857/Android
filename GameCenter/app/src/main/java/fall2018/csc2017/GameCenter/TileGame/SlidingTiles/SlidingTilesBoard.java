package fall2018.csc2017.GameCenter.TileGame.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

import fall2018.csc2017.GameCenter.TileGame.Tile;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * The sliding tiles board.
 */
public class SlidingTilesBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int numRows = UserManager.getInstance().getCurrentGameType() + 3;

    /**
     * The number of columns.
     */
    private int numCols = numRows;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[numRows][numCols];

    /**
     * A new sliding tiles board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the sliding tiles slidingTilesBoard
     */
    public SlidingTilesBoard(List<Tile> tiles) {
        Iterator<Tile> itr = tiles.iterator();

        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = itr.next();
            }
        }
    }

    /**
     * Return the number of tiles on the slidingTilesBoard.
     *
     * @return the number of tiles on the slidingTilesBoard
     */
    public int numTiles() {

        return numRows * numCols;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {

        Tile tempTile = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = tempTile;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Iterate over tiles on the slidingTilesBoard
     *
     * @return a new SlidingTilesBoardIterator
     */
    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new SlidingTilesBoardIterator();
    }

    /**
     * Class SlidingTilesBoard iterator
     */
    private class SlidingTilesBoardIterator implements Iterator<Tile> {

        /**
         * The index of tiles on the slidingTilesBoard.
         */
        int nextIndex = 0;

        /**
         * Check whether there is a next tile on the slidingTilesBoard
         *
         * @return whether there is a next tile on the slidingTilesBoard
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numRows * numCols;
        }

        /**
         * Get the next tile on the slidingTilesBoard
         *
         * @return the next tile on the slidingTilesBoard
         */
        @Override
        public Tile next() {
            if (!hasNext()) {
                throw new NoSuchElementException(
                        "Run Out Of Tiles"
                );
            }

            int row = nextIndex / numCols;
            int col = nextIndex % numCols;
            Tile result = tiles[row][col];
            nextIndex++;
            return result;
        }
    }
}
