package events.clientevents;

public class RequestLiveGameInfoCE extends ClientEvent{
    private final String player1Username;

    public RequestLiveGameInfoCE(String player1Username) {
        this.player1Username = player1Username;
    }

    public String getPlayer1Username() {
        return player1Username;
    }
}
