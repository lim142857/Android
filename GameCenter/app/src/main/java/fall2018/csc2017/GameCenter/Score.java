package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * The score board which contains the top 3 score with the name of the user who play this game for each game
 */
public class Score implements Serializable {
    /**
     * The scoreboard for each game, with scores.
     */
    private int[][] scoreBoardPerGameScore;

    /**
     * The scoreboard for each game, with users'names.
     */
    private String[][] scoreBoardPerGameName;

    /**
     * The top 3 scores the user has earned for finished games.
     */
    private int[][] scoreFinished;

    /**
     * The temporary scores the user has earned for unfinished games.
     */
    private int[] scoreTemp;

    /**
     * a new Score which has all zero or empty string value since no one has played before
     */
    Score() {
        scoreBoardPerGameScore = new int[7][3];
        scoreBoardPerGameName = new String[7][3];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                scoreBoardPerGameName[i][j] = "";
            }
        }
        scoreFinished = new int[7][3];
        scoreTemp = new int[7];
    }

    /**
     * Return the top 3 scores the user has earned for finished games.
     *
     * @return the top 3 scores the user has earned for finished games.
     */
    public int[][] getScoreFinished() {
        return this.scoreFinished;
    }

    /**
     * Set the temp score to scoreFinished according to the finishedGame this score earned from,
     * if score is in the top 3 scores of the game.
     * Precondition: 0 <= scoreFinished < scoreFinished.length
     *
     * @param finishedGame the finished game the score earned from.
     */
    public void setScoreFinished(int finishedGame) {
        ArrayList<Integer> available_scores = new ArrayList<>();
        // add all non-zero scores from scoreFinished[finishedGame] to available_scores
        for (int i : scoreFinished[finishedGame]) {
            if (i > 0) {
                available_scores.add(i);
            }
        }
        // add the temporary score to available_scores
        available_scores.add(scoreTemp[finishedGame]);
        Collections.sort(available_scores);
        int finished_score_size = available_scores.size();
        int new_top3[] = new int[3];
        // add the smallest 3 scores from available_scores to new_top3, in order
        for (int i = 0; i < finished_score_size; i++) {
            if (i < 3) {
                new_top3[i] = available_scores.remove(0);
            }
        }
        scoreFinished[finishedGame] = new_top3;
    }

    /**
     * Return the temporary score the user has earned for unfinished games.
     *
     * @return the temporary score the user has earned for unfinished games.
     */
    public int[] getScoreTemp() {
        return this.scoreTemp;
    }

    /**
     * Set the unfinished game score.
     * Precondition: 0 <= unfinishedGame < scoreTemp.length
     *
     * @param unfinishedGame the unfinished game the score earned from.
     */
    public void setScoreTemp(int unfinishedGame) {
        this.scoreTemp[unfinishedGame]++;
    }

    /**
     * Set the unfinished Sudoku game score.
     * Precondition: 0 <= unfinishedGame < scoreTemp.length
     *
     * @param unfinishedGame the unfinished game the score earned from.
     * @param time           the time(actually the score) of a sudoku game.
     */
    public void setSudokuScoreTemp(int unfinishedGame, int time) {
        this.scoreTemp[unfinishedGame] = time;
    }

    /**
     * reset the game score beacuse of a new game beginning
     *
     * @param newGame the unfinished game the score earned from.
     */
    public void resetScoreTemp(int newGame) {
        this.scoreTemp[newGame] = 0;
    }

    /**
     * Return the scoreboard score for each game
     */
    public int[][] getScoreBoardPerGameScore() {
        return this.scoreBoardPerGameScore;
    }

    /**
     * Return the scoreboard name for each game
     */
    public String[][] getScoreBoardPerGameName() {
        return this.scoreBoardPerGameName;
    }

    /**
     * set the scoreboard name for each game
     *
     * @param scoreBoardPerGameName the scoreBoardPerGameName that was copied
     */
    void setScoreBoardPerGameName(String[][] scoreBoardPerGameName) {
        this.scoreBoardPerGameName = scoreBoardPerGameName;
    }

    /**
     * set the scoreboard score for each game
     *
     * @param scoreBoardPerGameScore the scoreBoardPerGameScore that was copied
     */
    void getScoreBoardPerGameScore(int[][] scoreBoardPerGameScore) {
        this.scoreBoardPerGameScore = scoreBoardPerGameScore;
    }

    /**
     * refresh the scoreboard for every game after each time the game is over
     *
     * @param finishedGame the game that is finished.
     * @param score        the score of the finished game.
     * @param name         the name of the user of the finished game.
     */
    public void setScoreBoardPerGame(int finishedGame, int score, String name) {
        Map<String, User> userList = UserManager.getInstance().getUserList();
        String[] scoreName = this.scoreBoardPerGameName[finishedGame];
        int[] scoreScore = this.scoreBoardPerGameScore[finishedGame];

        // Set the score board per game.
        updateScoreFinished(score, name, scoreName, scoreScore);
        for (User q : userList.values()) {
            Score userScore = q.getScore();
            userScore.setScoreBoardPerGameName(scoreBoardPerGameName);
            userScore.getScoreBoardPerGameScore(scoreBoardPerGameScore);
        }
    }

    /**
     * add the name and the score to the correct position of scoreName and scoreScore,
     * if score is one of the top 3 scores of the game
     *
     * @param score      the score the current user got
     * @param name       the name of the current user
     * @param scoreName  the list of names of people who got the top 3 scores
     * @param scoreScore the top 3 scores
     */
    private void updateScoreFinished(int score, String name, String[] scoreName, int[] scoreScore) {
        int i = 2;
        while (i >= 0 && scoreScore[i] == 0) {
            i -= 1;
        }
        while (i >= 0 && scoreScore[i] > score) {
            i -= 1;
        }
        if (i < 2) {
            int j = 2;
            while (j > i + 1) {
                scoreScore[j] = scoreScore[j - 1];
                scoreName[j] = scoreName[j - 1];
                j--;
            }
            scoreScore[j] = score;
            scoreName[j] = name;
        }
    }
}