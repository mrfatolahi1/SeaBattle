package view.wellcomepage;


import events.EventInterpreter;
import events.serverevents.*;
import events.clientevents.ClientEvent;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.loginpage.LogInPage;
import view.wellcomepage.mainMenupage.MainMenuPage;
import view.wellcomepage.mainMenupage.usergame.gamepage.GamePage;
import view.wellcomepage.signuppage.SignUpPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WellComePage extends MyPanel implements EventInterpreter {
    private SignUpPage signUpPage;
    private LogInPage logInPage;
    private MainMenuPage mainMenuPage;

    public WellComePage(MyPanel previousPage, MainPanel mainPanel) {
        super(previousPage, mainPanel);
        this.setBackground(Color.BLUE);

        JButton logInButton = new JButton("Log In");
        logInButton.setBounds(440, 200, 200, 50);
        logInButton.setBackground(Color.WHITE);
        logInButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                setLogInPage(new LogInPage(WellComePage.this, getMainPanel()));
                logInPage.mShow();
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {
                logInButton.setBackground(Color.ORANGE);
                logInButton.repaint();
            }
            public void mouseExited(MouseEvent e) {
                logInButton.setBackground(Color.WHITE);
                logInButton.repaint();
            }
        });
        this.add(logInButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(440, 300, 200, 50);
        signUpButton.setBackground(Color.WHITE);
        signUpButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                setSignUpPage(new SignUpPage(WellComePage.this, getMainPanel()));
                signUpPage.mShow();
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {
                signUpButton.setBackground(Color.MAGENTA);
                signUpButton.repaint();
            }
            public void mouseExited(MouseEvent e) {
                signUpButton.setBackground(Color.WHITE);
                signUpButton.repaint();
            }
        });
        this.add(signUpButton);

        this.repaint();
    }

    public SignUpPage getSignUpPage() {
        return signUpPage;
    }

    public void setSignUpPage(SignUpPage signUpPage) {
        this.signUpPage = signUpPage;
    }

    public LogInPage getLogInPage() {
        return logInPage;
    }

    public void setLogInPage(LogInPage logInPage) {
        this.logInPage = logInPage;
    }

    public MainMenuPage getMainMenuPage() {
        return mainMenuPage;
    }

    public void setMainMenuPage(MainMenuPage mainMenuPage) {
        this.mainMenuPage = mainMenuPage;
    }

    public void openMainMenuPage(){
        setMainMenuPage(new MainMenuPage(this, getMainPanel()));
        mainMenuPage.mShow();
    }

    @Override
    public void back() {
        this.exit();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        interpretServerEvent(serverEvent);
    }

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        mainPanel.sendClientEvent(clientEvent, hasAnswer);
    }

    @Override
    public <E extends ServerEvent> void interpretServerEvent(E serverEvent) {
        if (serverEvent.getClass() == SignUpResultSE.class){
            signUpPage.receiveServerEvent(serverEvent);
        }else
        if (serverEvent.getClass() == LogInResultSE.class){
            logInPage.receiveServerEvent(serverEvent);
        } else
        if (
                serverEvent.getClass() == GameInfoSE.class ||
                serverEvent.getClass() == SendLiveGamesInfoSE.class ||
                serverEvent.getClass() == BoardsInfoSE.class ||
                serverEvent.getClass() == SendGameResultSE.class ||
                serverEvent.getClass() == DeclareLiveGameFinishSE.class||
                serverEvent.getClass() == SendUserInfoSE.class ||
                serverEvent.getClass() == HighScoresInfoSE.class ||
                serverEvent.getClass() == DeclareOpponentDisconnectSE.class
        ){
            mainMenuPage.receiveServerEvent(serverEvent);
        }else
        if (serverEvent.getClass() == LiveGameInfoSE.class){
            mainMenuPage.receiveServerEvent(serverEvent);
        }else
        if (serverEvent.getClass() == UnfinishedGameInfoSE.class){
            this.mainMenuPage = new MainMenuPage(this, getMainPanel());
            GameInfoSE gameInfoSE = new GameInfoSE(
                    ((UnfinishedGameInfoSE) serverEvent).getBoardsInfo_se(),
                    ((UnfinishedGameInfoSE) serverEvent).getPlayer1Username(),
                    ((UnfinishedGameInfoSE) serverEvent).getPlayer2Username(),
                    ((UnfinishedGameInfoSE) serverEvent).getPlayer1Score(),
                    ((UnfinishedGameInfoSE) serverEvent).getPlayer2Score(),
                    ((UnfinishedGameInfoSE) serverEvent).isYourTurn(),
                    ((UnfinishedGameInfoSE) serverEvent).isUpdate()
            );

            this.mainMenuPage.openUnfinishedGamePage(gameInfoSE);
        }
    }
}
