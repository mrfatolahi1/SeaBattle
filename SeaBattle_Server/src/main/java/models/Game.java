package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.board.Board;
import models.squars.Square;

import java.util.ArrayList;


public class Game {
    private int player1Id;
    private int player2Id;
    private int player1Score;
    private int player2Score;
    private Board player1Board;
    private Board player2Board;
    private boolean finished;
    private int winnerId;
    private int currentPlayerId;

    public Game() {
    }

    public Game(int player1Id, int player2Id, int randomMapNumber1, int randomMapNumber2) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Score = 0;
        this.player2Score = 0;
        this.player1Board = new Board(randomMapNumber1);
        this.player2Board = new Board(randomMapNumber2);
        this.finished = false;
        this.winnerId = -1;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public Board getPlayer1Board() {
        return player1Board;
    }

    public Board getPlayer2Board() {
        return player2Board;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public boolean checkTurn(int id){
        return id == currentPlayerId;
    }

    public void changeCurrentPlayer(){
        if (currentPlayerId == player1Id){
            currentPlayerId = player2Id;
        } else {
            currentPlayerId = player1Id;
        }
    }

    public boolean checkForEndGame(){
        if (finished){
            return true;
        }
        boolean a = true;
        A:for (ArrayList<Square> shipSquars : player1Board.getShipsSquars()){
            for (Square square : shipSquars){
                if (!square.isHited()){
                    a = false;
                    break A;
                }
            }
        }

        if (a){
            finished = true;
            winnerId = player1Id;
            return true;
        }

        boolean b = true;
        A:for (ArrayList<Square> shipSquars : player2Board.getShipsSquars()){
            for (Square square : shipSquars){
                if (!square.isHited()){
                    b = false;
                    break A;
                }
            }
        }

        if (b){
            finished = true;
            winnerId = player2Id;
            return true;
        }

        return false;
    }

    @JsonIgnore
    public int getNumberOfDestroyedShips(){
        int number = 0;
        for (ArrayList<Square> shipSquars : player1Board.getShipsSquars()){
            boolean a = true;
            for (Square square : shipSquars){
                if (!square.isHited()) {
                    a = false;
                    break;
                }
            }

            if (a){
                number++;
            }
        }

        for (ArrayList<Square> shipSquars : player2Board.getShipsSquars()){
            boolean a = true;
            for (Square square : shipSquars){
                if (!square.isHited()) {
                    a = false;
                    break;
                }
            }

            if (a){
                number++;
            }
        }


        return number;
    }
}
