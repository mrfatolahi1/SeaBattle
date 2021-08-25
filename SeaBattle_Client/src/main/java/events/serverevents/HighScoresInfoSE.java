package events.serverevents;

import java.util.ArrayList;

public class HighScoresInfoSE extends ServerEvent{
    private final ArrayList<SendUserInfoSE> usersInfo;

    public HighScoresInfoSE(ArrayList<SendUserInfoSE> usersInfo) {
        this.usersInfo = usersInfo;
    }

    public ArrayList<SendUserInfoSE> getUsersInfo() {
        return usersInfo;
    }
}
