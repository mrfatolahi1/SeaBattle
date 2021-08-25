package view.wellcomepage.mainMenupage;

import events.EventInterpreter;
import events.clientevents.RequestHighScoresTableInfoCE;
import events.clientevents.RequestLiveGamesInfoCE;
import events.clientevents.RequestNewGameCE;
import events.clientevents.RequestUserInfoCE;
import events.serverevents.*;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.mainMenupage.highscorespage.HighScoresPage;
import view.wellcomepage.mainMenupage.livegamesinfopage.LiveGamesInfoPage;
import view.wellcomepage.mainMenupage.usergame.WaitingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuPage extends MyPanel implements EventInterpreter {
    private WaitingPage waitingPage;
    private LiveGamesInfoPage liveGamesInfoPage;
    private HighScoresPage highScoresPage;
    public MainMenuPage(MyPanel previousPage, MainPanel mainPanel) {
        super(previousPage, mainPanel);

        this.setBackground(Color.YELLOW);

        JLabel pairingLabel = new JLabel();
        pairingLabel.setBackground(Color.YELLOW);
        pairingLabel.setForeground(Color.BLUE);
        pairingLabel.setOpaque(true);
        pairingLabel.setBounds(490, 100, 100, 50);
        MainMenuPage.this.add(pairingLabel);
        MainMenuPage.this.repaint();

        JButton newGameButton = new JButton("New Game");
        newGameButton.setBackground(Color.WHITE);
        newGameButton.setBounds(390, 200, 300, 50);
        newGameButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                sendClientEvent(new RequestNewGameCE(), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.add(newGameButton);

        JButton watchLiveGamesButton = new JButton("Watch Live Games");
        watchLiveGamesButton.setBackground(Color.WHITE);
        watchLiveGamesButton.setBounds(390, 270, 300, 50);
        watchLiveGamesButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                sendClientEvent(new RequestLiveGamesInfoCE(), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.add(watchLiveGamesButton);

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setBackground(Color.WHITE);
        highScoresButton.setBounds(390, 340, 300, 50);
        highScoresButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                sendClientEvent(new RequestHighScoresTableInfoCE(), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.add(highScoresButton);

        JButton yourInfoButton = new JButton("Your Info");
        yourInfoButton.setBackground(Color.WHITE);
        yourInfoButton.setBounds(390, 410, 300, 50);
        yourInfoButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                sendClientEvent(new RequestUserInfoCE(), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.add(yourInfoButton);

        JTextArea noteTextArea = new JTextArea("Note: If you pressed New Game button and it color changed\nto blue, it maeans that you should wait util your rival press\nhis/her ready button.");
        noteTextArea.setFont(new Font(null, Font.PLAIN, 10));
        noteTextArea.setBackground(Color.YELLOW);
        noteTextArea.setBounds(390, 465, 300, 50);
        noteTextArea.setOpaque(true);
        noteTextArea.setEditable(false);
        this.add(noteTextArea);

        this.repaint();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        interpretServerEvent(serverEvent);
    }

    @Override
    public <E extends ServerEvent> void interpretServerEvent(E serverEvent) {
        if (serverEvent.getClass() == BoardsInfoSE.class){
            if (!((BoardsInfoSE) serverEvent).isUpdate()) {
                this.waitingPage = new WaitingPage(this, getMainPanel(), (BoardsInfoSE) serverEvent, false);
                this.waitingPage.mShow();
            } else {
                this.waitingPage.setRemainingTime(this.waitingPage.getRemainingTime()+10);
                this.waitingPage.setRemainingRequests(this.waitingPage.getRemainingRequests()-1);
                this.waitingPage.setBoardsInfo_se((BoardsInfoSE) serverEvent);
                this.waitingPage.initialize();
            }
        }else
        if (serverEvent.getClass() == GameInfoSE.class || serverEvent.getClass() == DeclareOpponentDisconnectSE.class){
            this.waitingPage.receiveServerEvent(serverEvent);
        }
        if (serverEvent.getClass() == SendLiveGamesInfoSE.class){
            this.liveGamesInfoPage = new LiveGamesInfoPage(this, getMainPanel(), (SendLiveGamesInfoSE) serverEvent);
            this.liveGamesInfoPage.mShow();
        }else
        if (serverEvent.getClass() == LiveGameInfoSE.class){
            liveGamesInfoPage.receiveServerEvent(serverEvent);
        }else
        if (serverEvent.getClass() == SendGameResultSE.class){
            waitingPage.receiveServerEvent(serverEvent);
        } else
        if (serverEvent.getClass() == DeclareLiveGameFinishSE.class){
            liveGamesInfoPage.receiveServerEvent(serverEvent);
        }else
        if (serverEvent.getClass() == SendUserInfoSE.class){
            JOptionPane.showMessageDialog(
                    this,
                    "Username: " + ((SendUserInfoSE) serverEvent).getUsername() +
                            "\nPassword: " + ((SendUserInfoSE) serverEvent).getPassword() +
                            "\nNumber Of Wins: " + ((SendUserInfoSE) serverEvent).getNumberOfWins() +
                            "\nNumber Of Losts: " + ((SendUserInfoSE) serverEvent).getNumberOfLosts() +
                            "\nTotal Score: " + ((SendUserInfoSE) serverEvent).getTotalScore(),
                    "Your Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else
        if (serverEvent.getClass() == HighScoresInfoSE.class){
            this.highScoresPage = new HighScoresPage(this, getMainPanel(), ((HighScoresInfoSE) serverEvent));
            this.highScoresPage.mShow();
        }
    }

    public void openUnfinishedGamePage(GameInfoSE gameInfoSE){
        this.waitingPage = new WaitingPage(this, getMainPanel(), null, true);
        this.waitingPage.openUnfinishedGamePage(gameInfoSE);
    }

    @Override
    public void back() {
        this.exit();
    }
}
