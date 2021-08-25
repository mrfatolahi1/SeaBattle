package events.serverevents;

public class LiveGameInfoSE extends ServerEvent{
    private final BoardsInfoSE boardsInfo_se;
    private final String player1Username;
    private final String player2Username;
    private final int player1Score;
    private final int player2Score;
    private final boolean yourTurn;
    private final int Player1NumberOfRemainingShips;
    private final int Player1NumberOfDestroyedShips;
    private final int Player2NumberOfRemainingShips;
    private final int Player2NumberOfDestroyedShips;
    private final boolean update;

    public LiveGameInfoSE(BoardsInfoSE boardsInfo_se, String player1Username, String player2Username, int player1Score, int player2Score, boolean yourTurn, int player1NumberOfRemainingShips, int player1NumberOfDestroyedShips, int player2NumberOfRemainingShips, int player2NumberOfDestroyedShips, boolean update) {
        this.boardsInfo_se = boardsInfo_se;
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.yourTurn = yourTurn;
        Player1NumberOfRemainingShips = player1NumberOfRemainingShips;
        Player1NumberOfDestroyedShips = player1NumberOfDestroyedShips;
        Player2NumberOfRemainingShips = player2NumberOfRemainingShips;
        Player2NumberOfDestroyedShips = player2NumberOfDestroyedShips;
        this.update = update;
    }

    public BoardsInfoSE getBoardsInfo_se() {
        return boardsInfo_se;
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public boolean isUpdate() {
        return update;
    }

    public int getPlayer1NumberOfRemainingShips() {
        return Player1NumberOfRemainingShips;
    }

    public int getPlayer1NumberOfDestroyedShips() {
        return Player1NumberOfDestroyedShips;
    }

    public int getPlayer2NumberOfRemainingShips() {
        return Player2NumberOfRemainingShips;
    }

    public int getPlayer2NumberOfDestroyedShips() {
        return Player2NumberOfDestroyedShips;
    }
}
