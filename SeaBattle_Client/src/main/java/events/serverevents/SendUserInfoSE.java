package events.serverevents;

public class SendUserInfoSE extends ServerEvent{
    private final String username;
    private final String password;
    private final int numberOfWins;
    private final int numberOfLosts;
    private final int totalScore;
    private final boolean online;

    public SendUserInfoSE(String username, String password, int numberOfWins, int numberOfLosts, int totalScore, boolean online) {
        this.username = username;
        this.password = password;
        this.numberOfWins = numberOfWins;
        this.numberOfLosts = numberOfLosts;
        this.totalScore = totalScore;
        this.online = online;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public int getNumberOfLosts() {
        return numberOfLosts;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public boolean isOnline() {
        return online;
    }
}
