package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import fall2018.csc2017.GameCenter.TileGame.BoardManager;

/**
 * The user, with name and password to be given.
 */
public class User implements Serializable {

    /**
     * The name of the user
     */
    private String name;

    /**
     * The password of the user
     */
    private String password;

    /**
     * The steps the user has done for each game.
     */
    private ArrayList<ArrayList<int[]>> steps = new ArrayList<>();

    /**
     * The current slidingTilesBoard manager the user has, for each game.
     */
    private BoardManager[] boardTemp = new BoardManager[7];

    /**
     * The scoreboard for each game which will display the top 3 score with the name of the user who play it
     */
    private Score score;

    /**
     * A new user.
     *
     * @param name     the name of the user
     * @param password the password of the user
     */
    User(String name, String password) {
        this.name = name;
        this.password = password;
        this.score = new Score();
        Map<String, User> userList = UserManager.getInstance().getUserList();

        // set the previous score per game to the new user
        if (!userList.isEmpty()) {
            User user = (User) userList.values().toArray()[0];
            score.setScoreBoardPerGameName(user.score.getScoreBoardPerGameName());
            score.getScoreBoardPerGameScore(user.score.getScoreBoardPerGameScore());
        }
        for (int i = 0; i < 7; i++) {
            this.steps.add(new ArrayList<int[]>());
        }
    }

    /**
     * return the score
     *
     * @return the score for each game
     */
    public Score getScore() {
        return score;
    }

    /**
     * Return the name of the user.
     *
     * @return the name of the user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the password of the user.
     *
     * @return the password of the user.
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Return the steps the user has done.
     *
     * @return the steps the user has done.
     */
    public ArrayList<ArrayList<int[]>> getSteps() {
        return this.steps;
    }

    /**
     * Return the temporary slidingTilesBoard manager the user has for each game.
     *
     * @return the temporary board manager the user has for each game.
     */
    public BoardManager[] getBoardTemp() {
        return this.boardTemp;
    }

    /**
     * Reset steps of the game.
     *
     * @param game The current game.
     */
    public void resetSteps(int game) {
        this.steps.get(game).clear();
    }

    /**
     * Add a step done by the user to the correct position of steps according to game.
     *
     * @param game      the game where the switch is made.
     * @param stepsDone a step done by the user.
     */
    public void addSteps(int game, int[] stepsDone) {
        this.steps.get(game).add(stepsDone);
    }

    /**
     * Set board manager to the correct position of boardTemp according to game.
     *
     * @param game         the game the board manager belongs to.
     * @param boardManager the temporary board manager the user has for the game.
     */
    public void setBoardTemp(int game, BoardManager boardManager) {
        this.boardTemp[game] = boardManager;
    }
}