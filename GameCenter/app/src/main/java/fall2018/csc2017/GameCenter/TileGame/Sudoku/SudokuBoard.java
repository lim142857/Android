package fall2018.csc2017.GameCenter.TileGame.Sudoku;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

import fall2018.csc2017.GameCenter.TileGame.Tile;

/**
 * The Sudoku Board.
 */
public class SudokuBoard extends Observable implements Serializable {

    /**
     * Some seed sudoku boards, which is already solved. I got them from
     * https://my.oschina.net/wangmengjun/blog/781984
     */
    private final static int[][][] SEED_SUDOKU_ARRAYS = {
            {{1, 2, 3, 4, 5, 6, 7, 8, 9}, {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},
                    {2, 1, 4, 3, 6, 5, 8, 9, 7},
                    {3, 6, 5, 8, 9, 7, 2, 1, 4},
                    {8, 9, 7, 2, 1, 4, 3, 6, 5},
                    {5, 3, 1, 6, 4, 2, 9, 7, 8},
                    {6, 4, 2, 9, 7, 8, 5, 3, 1},
                    {9, 7, 8, 5, 3, 1, 6, 4, 2}},
            {{3, 9, 4, 5, 1, 7, 6, 2, 8}, {5, 1, 7, 6, 2, 8, 3, 9, 4},
                    {6, 2, 8, 3, 9, 4, 5, 1, 7},
                    {9, 3, 5, 4, 7, 1, 2, 8, 6},
                    {4, 7, 1, 2, 8, 6, 9, 3, 5},
                    {2, 8, 6, 9, 3, 5, 4, 7, 1},
                    {1, 4, 3, 7, 5, 9, 8, 6, 2},
                    {7, 5, 9, 8, 6, 2, 1, 4, 3},
                    {8, 6, 2, 1, 4, 3, 7, 5, 9}},
            {{7, 6, 1, 9, 8, 4, 2, 3, 5}, {9, 8, 4, 2, 3, 5, 7, 6, 1},
                    {2, 3, 5, 7, 6, 1, 9, 8, 4},
                    {6, 7, 9, 1, 4, 8, 3, 5, 2},
                    {1, 4, 8, 3, 5, 2, 6, 7, 9},
                    {3, 5, 2, 6, 7, 9, 1, 4, 8},
                    {8, 1, 7, 4, 9, 6, 5, 2, 3},
                    {4, 9, 6, 5, 2, 3, 8, 1, 7},
                    {5, 2, 3, 8, 1, 7, 4, 9, 6}},
            {{7, 1, 5, 4, 3, 6, 2, 9, 8}, {4, 3, 6, 2, 9, 8, 7, 1, 5},
                    {2, 9, 8, 7, 1, 5, 4, 3, 6},
                    {1, 7, 4, 5, 6, 3, 9, 8, 2},
                    {5, 6, 3, 9, 8, 2, 1, 7, 4},
                    {9, 8, 2, 1, 7, 4, 5, 6, 3},
                    {3, 5, 7, 6, 4, 1, 8, 2, 9},
                    {6, 4, 1, 8, 2, 9, 3, 5, 7},
                    {8, 2, 9, 3, 5, 7, 6, 4, 1}}};
    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[9][9];
    /**
     * The number-representation of the board in row-major order.
     */
    private int[][] sudokuBoard;

    /**
     * Generate a new Sudoku board.
     */
    public SudokuBoard() {
        Random random = new Random();
        int i = random.nextInt(4);
        sudokuBoard = makeDeepCopy(SEED_SUDOKU_ARRAYS[i]);
        changeMethod1();
        changeMethod2();
        erase();
        setSudokuTiles();
    }

    /**
     * Create a new Sudoku board from given array.
     * Only for convince of JUnit test
     *
     * @param board the given array representing a sudoku board.
     */
    public SudokuBoard(int[][] board) {
        this.sudokuBoard = board;
        setSudokuTiles();
    }

    /**
     * Set the sudoku tiles for the board sudoku
     */
    private void setSudokuTiles() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                tiles[row][col] = new Tile(sudokuBoard[row][col] - 1, 9);
                if (sudokuBoard[row][col] == 25) {
                    tiles[row][col].setChangeable(true);
                }
            }
        }
    }

    /**
     * Get the umber-representation of the board in row-major order.
     *
     * @return return the whole umber-representation of the board in row-major order.
     */
    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

    /**
     * Returns a deep copied array of the chosen seed sudoku board
     *
     * @param seedBoard the chosen seed sudoku board
     * @return a deep copied array of the chosen seed sudoku board
     */
    private int[][] makeDeepCopy(int[][] seedBoard) {
        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            result[i] = seedBoard[i].clone();
        }
        return result;
    }

    /**
     * Change the board by switching numbers.
     */
    public int[] changeMethod1() {
        Random random1 = new Random();
        int a = random1.nextInt(9) + 1;
        int b = random1.nextInt(9) + 1;
        while (a == b) {
            b = random1.nextInt(9);
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard[row][col] == a) {
                    sudokuBoard[row][col] = b;
                } else if (sudokuBoard[row][col] == b) {
                    sudokuBoard[row][col] = a;
                }
            }
        }
        return new int[]{a, b};
    }

    /**
     * Change the board by switching columns.
     */
    public int[] changeMethod2() {
        Random random1 = new Random();
        int a = random1.nextInt(3);
        int b = random1.nextInt(3) + a * 3;
        int c = random1.nextInt(3) + a * 3;
        while (c == b) {
            c = random1.nextInt(3) + a * 3;
        }
        for (int i = 0; i < 9; i++) {
            int temp = sudokuBoard[i][b];
            sudokuBoard[i][b] = sudokuBoard[i][c];
            sudokuBoard[i][c] = temp;
        }
        return new int[]{b, c};
    }

    /**
     * Erase some of the tiles, making the number to be blank
     */
    public void erase() {
        int i = 39;
        int[] a = new int[39];
        while (i > 0) {
            Random random = new Random();
            int y = random.nextInt(81);
            while (isInclude(a, y)) {
                y = random.nextInt(81);
            }
            a[i - 1] = y;
            int row = y / 9;
            int col = y % 9;

            sudokuBoard[row][col] = 25;
            i--;
        }
    }

    /**
     * A helper function that checks if a element belongs to an array.
     */
    private boolean isInclude(int[] array, int element) {
        for (int anArray : array) {
            if (anArray == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * Change the tiles at (row1, col1) and (row2, col2)
     *
     * @param row the first tile row
     * @param col the first tile col
     */
    public void changeTiles(int row, int col, int change) {
        boolean changeable = tiles[row][col].getChangeable();
        tiles[row][col] = new Tile(change - 1, 9);
        tiles[row][col].setChangeable(changeable);
        sudokuBoard[row][col] = change;
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
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
}