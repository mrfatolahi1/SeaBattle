package events.clientevents;

import java.io.Serializable;

public abstract class ClientEvent implements Serializable {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
