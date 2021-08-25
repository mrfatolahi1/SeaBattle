package models;

import java.io.File;
import java.util.ArrayList;

public class User {
    private int Id;
    private String username;
    private String password;
    private Game currentGame;
    private ArrayList<Game> games;
    private int totalScore;
    private int numberOfWins;
    private int numberOfLosts;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        File folder = new File("Database/Usernames");
        this.Id = folder.listFiles().length;
        this.currentGame = null;
        this.games = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public int getNumberOfLosts() {
        return numberOfLosts;
    }

    public void setNumberOfLosts(int numberOfLosts) {
        this.numberOfLosts = numberOfLosts;
    }

    public int getId() {
        return Id;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public void addVictory(){
        numberOfWins++;
        totalScore = numberOfWins - numberOfLosts;
    }

    public void addDefeat(){
        numberOfLosts++;
        totalScore = numberOfWins - numberOfLosts;
    }
}
