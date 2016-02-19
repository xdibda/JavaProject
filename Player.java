package othello;

import static othello.Utility.*;

public class Player {
    private Color color;
    private PlayerType playerType;
    private int score;
    private String name;

    Player(Color color, PlayerType playerType) {
        this.color = color;
        this.playerType = playerType;
        this.score = 0;
    }

    Player(Color color, PlayerType playerType, int score) {
        this.color = color;
        this.playerType = playerType;
        this.score = score;
    }

    static Player[] getPlayersForConstructor(PlayerType playerType) {
        Player players[] = {
                new Player(Color.BLACK, PlayerType.HUMAN),
                new Player(Color.WHITE, playerType)
        };
        return players;
    }

    PlayerType getPlayerType() {
        return playerType;
    }

    Color getColor() {
        return color;
    }

    void resetScore() {
        this.score = 0;
    }

    void setScore(int score) {
        this.score = score;
    }

    int getScore() {
        return score;
    }
}