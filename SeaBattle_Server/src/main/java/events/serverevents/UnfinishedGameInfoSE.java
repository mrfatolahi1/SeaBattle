package events.serverevents;

public class UnfinishedGameInfoSE extends ServerEvent{
    private final BoardsInfoSE boardsInfo_se;
    private final String player1Username;
    private final String player2Username;
    private final int player1Score;
    private final int player2Score;
    private final boolean yourTurn;
    private final boolean update;

    public UnfinishedGameInfoSE(BoardsInfoSE boardsInfo_se, String player1Username, String player2Username, int player1Score, int player2Score, boolean yourTurn, boolean update) {
        this.boardsInfo_se = boardsInfo_se;
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.yourTurn = yourTurn;
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
}
