package fall2018.csc2017.GameCenter.TileGame;

import java.io.Serializable;
import java.util.ArrayList;

import fall2018.csc2017.GameCenter.TileGame.Sudoku.SudokuBoard;
import fall2018.csc2017.GameCenter.TileGame.Sudoku.SudokuValid;
import fall2018.csc2017.GameCenter.User;
import fall2018.csc2017.GameCenter.UserManager;

/**
 * Manage a sudoku board.
 */
public class SudokuBoardManager extends BoardManager implements Serializable {

    /**
     * The sudoku board being managed.
     */
    private SudokuBoard sudokuBoard;

    /**
     * To check if the change is valid.
     */
    private SudokuValid sudokuValid = new SudokuValid();

    /**
     * whether the tile has been selected before click on the number
     */
    private boolean hasTileDisplay;

    /**
     * the position of the tile that been selected before.
     */
    private int[] positionDisplay;

    /**
     * Manage a sudoku board that has been pre-populated.
     *
     * @param sudokuBoard the sudoku board.
     */
    public SudokuBoardManager(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.hasTileDisplay = false;
    }

    /**
     * Manage a new sudoku board.
     */
    SudokuBoardManager() {
        this.sudokuBoard = new SudokuBoard();
        this.hasTileDisplay = false;
    }

    /**
     * get sudokuValid
     *
     * @return whether the change to the sudoku is valid
     */
    public SudokuValid getSudokuValid() {
        return this.sudokuValid;
    }

    /**
     * get hasTileDisplay
     *
     * @return whether the user has click the tile before selecting the number
     */
    public boolean getHasTileDisplay() {
        return this.hasTileDisplay;
    }

    /**
     * set hasTileDisplay
     */
    public void setHasTileDisplay() {
        this.hasTileDisplay = true;
    }

    /**
     * get positionDisplay
     *
     * @return the position which the user click
     */
    public int[] getPositionDisplay() {
        return this.positionDisplay;
    }

    /**
     * set positionDisplay
     *
     * @param position the position which the user click
     */
    public void setPositionDisplay(int[] position) {
        this.positionDisplay = position;
    }

    /**
     * Return the current sudoku board.
     *
     * @return return the current sudoku board.
     */
    public SudokuBoard getSudokuBoard() {
        return this.sudokuBoard;
    }

    /**
     * Return whether the user has clicked a tile button before choose the number to change on it,
     * or whether the tile is changeable.(if the number tile is provided by the the original board
     * then it is not changeable).
     * What's more, if there is already a same number in the row or column or the square where the selected
     * tile belongs to, the tap is also invalid.
     *
     * @param number that the user selected to change.
     * @return whether the tile is ok to be changed
     */
    @Override
    public boolean isValidTap(int number) {
        if (!(hasTileDisplay && sudokuBoard.getTile(
                positionDisplay[0], positionDisplay[1]).getChangeable())) {
            return false;
        }
        // if the touched tile is empty, then it is valid
        if (number == 25) {
            return true;
        }
        int[] row = sudokuBoard.getSudokuBoard()[positionDisplay[0]];
        int[] col = sudokuValid.column(positionDisplay[1], sudokuBoard.getSudokuBoard());
        int[] square = sudokuValid.subSquare((((positionDisplay[0]) / 3) * 3 +
                positionDisplay[1] / 3), sudokuBoard.getSudokuBoard());
        return numAlreadyExisted(number, row, col, square);
    }

    /**
     * Return whether the number is already existed in the row or column or the square where
     * the number belongs to.
     *
     * @param number the number chose by user
     * @param row    the row the number belongs to
     * @param col    the column the number belongs to
     * @param square the square the number belongs to
     * @return whether the number is already existed in the row or column or the square where
     * the number belongs to.
     */
    public boolean numAlreadyExisted(int number, int[] row, int[] col, int[] square) {
        for (int i : row) {
            if (i == number) {
                return false;
            }
        }
        for (int i : col) {
            if (i == number) {
                return false;
            }
        }
        for (int i : square) {
            if (i == number) {
                return false;
            }
        }
        return true;
    }

    /**
     * after process a touch at position in the board, select the number and change the
     * touched position as appropriate.
     *
     * @param number that the user selected to change
     */
    @Override
    public void touchMove(int number) {
        int gameType = UserManager.getInstance().getCurrentGameType();
        User user = UserManager.getInstance().getCurrentUser();
        int row = positionDisplay[0];
        int col = positionDisplay[1];
        int numToChange = sudokuBoard.getSudokuBoard()[row][col];
        int[] swapped_tiles = {row, col, numToChange};

        // add the step done to user.Steps
        user.addSteps(gameType, swapped_tiles);
        sudokuBoard.changeTiles(row, col, number);
        hasTileDisplay = false;
    }

    /**
     * Undo the last step and return true.
     * If no step has done, return false.
     *
     * @return return false if no step has done, otherwise return true.
     */
    public boolean undo() {
        User user = UserManager.getInstance().getCurrentUser();
        ArrayList<int[]> switchesList = user.getSteps().get(6);
        // get the index of the last item in switchesList
        int removeLastSteps = switchesList.size() - 1;
        if (removeLastSteps < 0) {
            return false;
        }
        int[] tile_to_change = switchesList.remove(removeLastSteps);
        // row: tile_to_change[0], column: tile_to_change[1], number: tile_to_change[2]
        sudokuBoard.changeTiles(tile_to_change[0], tile_to_change[1], tile_to_change[2]);
        user.setBoardTemp(6, this);
        return true;
    }

    /**
     * Return whether the sudoku puzzle is solved
     *
     * @return true if the sudoku puzzle is solved, false else
     */
    @Override
    public boolean puzzleSolved() {
        int[][] board = sudokuBoard.getSudokuBoard();
        return (sudokuValid.allRowsValid(board) &&
                sudokuValid.allColumnsValid(board) &&
                sudokuValid.allSubsquaresValid(board));
    }
}