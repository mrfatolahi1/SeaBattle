package controller;

import models.Game;
import models.User;
import saversandloaders.Saver;

import java.io.IOException;

public class GameController {

    public static void hitASquare(User user, Game game, int x, int y, boolean hitOnOwnBoard){
        if (game == null){
            return;
        }
        if (!hitOnOwnBoard){
            if (user.getId() == game.getPlayer1Id()) {
                game.getPlayer2Board().getSquare(x, y).setHited(true);
                if (game.getPlayer2Board().getSquare(x, y).isFull()){
                    game.setPlayer1Score(game.getPlayer1Score() + 1);
                }
                game.getPlayer2Board().checkShipsDestruction();
            } else {
                game.getPlayer1Board().getSquare(x, y).setHited(true);
                if (game.getPlayer1Board().getSquare(x, y).isFull()){
                    game.setPlayer2Score(game.getPlayer2Score() + 1);
                }
                game.getPlayer1Board().checkShipsDestruction();
            }
        } else {
            if (user.getId() == game.getPlayer1Id()) {
                game.getPlayer1Board().getSquare(x, y).setHited(true);
                if (game.getPlayer1Board().getSquare(x, y).isFull()){
                    game.setPlayer2Score(game.getPlayer2Score() + 1);
                }
                game.getPlayer1Board().checkShipsDestruction();
            } else {
                game.getPlayer2Board().getSquare(x, y).setHited(true);
                if (game.getPlayer2Board().getSquare(x, y).isFull()){
                    game.setPlayer1Score(game.getPlayer1Score() + 1);
                }
            }
            game.getPlayer2Board().checkShipsDestruction();
        }

        try {
            Saver.saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
