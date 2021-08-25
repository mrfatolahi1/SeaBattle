package events.clientevents;

public class SignUpEventCE extends ClientEvent{
    private final String username;
    private final String password;

    public SignUpEventCE(String username, String password) {
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
