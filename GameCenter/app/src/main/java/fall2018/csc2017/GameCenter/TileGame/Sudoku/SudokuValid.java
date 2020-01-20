package fall2018.csc2017.GameCenter.TileGame.Sudoku;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Check if the change to the sudoku is valid.
 */
public class SudokuValid implements Serializable {

    /**
     * The digit set from 0 to 9.
     */
    private final int[] DIGITS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Return whether all rows in sudoku board are valid
     *
     * @param board the board
     * @return true if all rows in sudoku board are valid, false else.
     */
    public boolean allRowsValid(int[][] board) {
        boolean result = true;
        for (int[] r : board) {
            if (!listValid(r)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Return whether a given list matches the contains all digits 0-9 with no duplicates.
     * Order does not matter
     *
     * @param list the list
     * @return true if given list matches the contains all digits 0-9 with no duplicates
     * and order does not matter, false else
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean listValid(int[] list) {
        Set<Integer> listSet = new HashSet<>();
        for (int l : list) {
            listSet.add(l);
        }
        Set<Integer> digitsSet = new HashSet<>();
        for (int d : DIGITS) {
            digitsSet.add(d);
        }
        return listSet.equals(digitsSet);
    }

    /**
     * Return whether all columns in sudoku board are valid
     *
     * @param board the board
     * @return true if all columns in sudoku board are valid, false else
     */
    public boolean allColumnsValid(int[][] board) {
        boolean result = true;
        for (int[] c : this.columns(board)) {
            if (!listValid(c)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Return an array of array contains all columns in sudoku board
     *
     * @param board the board
     * @return array of array contains all columns in sudoku board
     */
    public int[][] columns(int[][] board) {
        int[][] result = new int[9][9];
        int i;
        for (i = 0; i < board.length; i++) {
            result[i] = column(i, board);
        }
        return result;
    }

    /**
     * Return an array of the i th column in sudoku board
     *
     * @param i     index of subSquare
     * @param board the board
     * @return array of the i th column in sudoku board
     */
    public int[] column(int i, int[][] board) {
        int[] result = new int[9];
        int r;
        for (r = 0; r < board.length; r++) {
            result[r] = board[r][i];
        }
        return result;
    }

    /**
     * Return whether all subSquares in sudoku board are valid
     *
     * @param board the board
     * @return true if all subSquares in sudoku board are valid, false else
     */
    public boolean allSubsquaresValid(int[][] board) {
        boolean result = true;
        for (int[] c : this.subSquares(board)) {
            if (!listValid(c)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Return an array of array contains all subSquares in sudoku board
     *
     * @param board the board
     * @return array of array contains all subSquares in sudoku board
     */
    public int[][] subSquares(int[][] board) {
        int[][] result = new int[9][9];
        int i;
        for (i = 0; i < board.length; i++) {
            result[i] = subSquare(i, board);
        }
        return result;
    }

    /**
     * Return an array of the i th subSquare in sudoku board
     *
     * @param i     index of subSquare
     * @param board the board
     * @return array of the i th subSquare in sudoku board
     */
    public int[] subSquare(int i, int[][] board) {
        int r = (int) Math.pow(board.length, 0.5);
        int startRow = (int) Math.floor((i / r) * r);
        int startCol = (i % r) * r;
        int index = 0;
        int[] result = new int[9];
        for (int a = startRow; a < startRow + 3; a++) {
            for (int b = startCol; b < startCol + 3; b++) {
                result[index] = board[a][b];
                index++;
            }
        }
        return result;
    }
}