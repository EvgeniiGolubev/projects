package com.company;

/**
 * Implementation of a random smart move.
 * Obviously, a good move should ultimately bring us closer to victory, namely to obtaining the 2048 tile.
 * Consider this option for comparing the effectiveness of a move:
 *  1) The first move is better than the second if after it is made there are more empty tiles on the field
 *  than as a result of the second move.
 *  2) The first move is better than the second if the total score after it is greater
 *  than the score obtained as a result of the second move.
 */
public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles, score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        if (numberOfEmptyTiles > o.numberOfEmptyTiles) {
            return 1;
        } else if (numberOfEmptyTiles < o.numberOfEmptyTiles) {
            return -1;
        } else {
            if (score > o.score) {
                return 1;
            } else if (score < o.score) {
                return -1;
            }
        }
        return 0;
    }
}
