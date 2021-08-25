package events.serverevents;


import models.LogInResults;

public class LogInResultSE extends ServerEvent{
    private final LogInResults logInResult;

    public LogInResultSE(LogInResults logInResult) {
        this.logInResult = logInResult;
    }

    public LogInResults getLogInResult() {
        return logInResult;
    }
}
