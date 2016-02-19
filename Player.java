package othello;

import static othello.Utility.*;

public class Player {
    private Color color;
    private PlayerType playerType;
    private int score;

    Player(Color color, PlayerType playerType) {
        this.color = color;
        this.playerType = playerType;
        this.score = 0;
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

    void setScore(int score) {
        this.score = score;
    }

    int getScore() {
        return score;
    }
}