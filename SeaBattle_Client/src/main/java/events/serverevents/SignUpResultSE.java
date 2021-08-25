package events.serverevents;

import models.SignUpResults;

public class SignUpResultSE extends ServerEvent{
    private final SignUpResults signUpResult;

    public SignUpResultSE(SignUpResults signUpResult) {
        this.signUpResult = signUpResult;
    }

    public SignUpResults getSignUpResult() {
        return signUpResult;
    }
}
