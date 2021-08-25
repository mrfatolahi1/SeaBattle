package events.serverevents;

import java.util.ArrayList;

public class SendLiveGamesInfoSE extends ServerEvent{
    private final ArrayList<ArrayList<Object>> gamesInfo;

    public SendLiveGamesInfoSE(ArrayList<ArrayList<Object>> gamesInfo) {
        this.gamesInfo = gamesInfo;
    }

    public ArrayList<ArrayList<Object>> getGamesInfo() {
        return gamesInfo;
    }
}
