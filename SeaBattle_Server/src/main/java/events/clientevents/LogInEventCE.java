package events.clientevents;

public class LogInEventCE extends ClientEvent{
    private final String username;
    private final String password;

    public LogInEventCE(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
