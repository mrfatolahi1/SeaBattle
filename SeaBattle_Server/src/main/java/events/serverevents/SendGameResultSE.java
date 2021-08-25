package events.serverevents;

public class SendGameResultSE extends ServerEvent{
    private final boolean winner;

    public SendGameResultSE(boolean winner) {
        this.winner = winner;
    }


    public boolean isWinner() {
        return winner;
    }
}
