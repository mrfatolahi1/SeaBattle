package view.wellcomepage.mainMenupage.usergame;

import events.EventInterpreter;
import events.clientevents.MakeClientNetworkManagerWaitingCE;
import events.clientevents.ReadyForStartGameCE;
import events.clientevents.RequestNewRandomizeCE;
import events.serverevents.*;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.mainMenupage.usergame.gamepage.GamePage;
import view.wellcomepage.mainMenupage.usergame.gamepage.GraphicalBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WaitingPage extends MyPanel implements EventInterpreter {
    private GamePage gamePage;
    private int remainingTime;
    private int remainingRequests;
    private final Object object;
    private BoardsInfoSE boardsInfo_se;
    private boolean ready;

    public WaitingPage(MyPanel previousPage, MainPanel mainPanel, BoardsInfoSE boardsInfo_se, boolean forUnfinishedGame) {
        super(previousPage, mainPanel);
        this.object = new Object();
        if (forUnfinishedGame){
            return;
        }
        this.remainingRequests = 3;
        this.remainingTime = 30;
        this.boardsInfo_se = boardsInfo_se;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (remainingTime != 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setRemainingTime(getRemainingTime()-1);
                    initialize();
                }
                if (!ready){
                    sendClientEvent(new ReadyForStartGameCE(), true);
                }
            }
        }).start();
        initialize();
    }

    public void initialize(){
        this.removeAll();
        GraphicalBoard board = new GraphicalBoard(this, boardsInfo_se.getBoard1FilledSquarsCoordinates(),
                boardsInfo_se.getBoard1HitedSquarsCoordinates(), false, false);

        board.setBounds(200, 150, 300, 300);
        this.add(board);
        JLabel remainingTimeLabel = new JLabel("Remaining Time: "+remainingTime);
        remainingTimeLabel.setFont(new Font(null, Font.PLAIN, 20));
        remainingTimeLabel.setBounds(600, 150, 400, 50);
        this.add(remainingTimeLabel);

        JLabel remainingRequestsLabel = new JLabel("Remaining Requests: "+remainingRequests);
        remainingRequestsLabel.setFont(new Font(null, Font.PLAIN, 20));
        remainingRequestsLabel.setBounds(600, 200, 400, 50);
        this.add(remainingRequestsLabel);

        if (remainingRequests > 0){
            JButton randomizeButton = new JButton("Randomize");
            randomizeButton.setFont(new Font(null, Font.PLAIN, 25));
            randomizeButton.setBounds(600, 270, 250, 80);
            randomizeButton.setBackground(Color.BLACK);
            randomizeButton.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    sendClientEvent(new RequestNewRandomizeCE(), true);
                }
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            });
            randomizeButton.setForeground(Color.WHITE);
            this.add(randomizeButton);
        }

        JButton readyButton = new JButton("Ready!");
        readyButton.setFont(new Font(null, Font.PLAIN, 25));
        readyButton.setBounds(600, 370, 250, 80);
        readyButton.setBackground(Color.BLACK);
        readyButton.setForeground(Color.WHITE);
        readyButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                ready = true;
                sendClientEvent(new ReadyForStartGameCE(), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.add(readyButton);

        JTextArea noteTextArea = new JTextArea("Note: If you pressed ready button and it color changed\nto blue, it maeans that you should wait util your rival\npress his/her ready button.");
        noteTextArea.setFont(new Font(null, Font.PLAIN, 10));
        noteTextArea.setBounds(600, 455, 265, 50);
        noteTextArea.setOpaque(true);
        noteTextArea.setEditable(false);
        this.add(noteTextArea);
        this.repaint();
        this.revalidate();
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        synchronized (object){
            this.remainingTime = remainingTime;
        }
    }

    public void setBoardsInfo_se(BoardsInfoSE boardsInfo_se) {
        this.boardsInfo_se = boardsInfo_se;
    }

    public int getRemainingRequests() {
        return remainingRequests;
    }

    public void setRemainingRequests(int remainingRequests) {
        this.remainingRequests = remainingRequests;
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        interpretServerEvent(serverEvent);
    }


    @Override
    public <E extends ServerEvent> void interpretServerEvent(E serverEvent) {
        if (serverEvent.getClass() == GameInfoSE.class){
            if (!((GameInfoSE) serverEvent).isUpdate()){
                this.gamePage = new GamePage(this, getMainPanel(), ((GameInfoSE) serverEvent));
                this.gamePage.mShow();
            } else
            if (((GameInfoSE) serverEvent).isUpdate()){
                this.gamePage.initialize(((GameInfoSE) serverEvent));
            }



            (new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!((GameInfoSE) serverEvent).isYourTurn()){
                        sendClientEvent(new MakeClientNetworkManagerWaitingCE(), true);
                    }
                }
            })).start();
        }else
        if (serverEvent.getClass() == SendGameResultSE.class || serverEvent.getClass() == DeclareOpponentDisconnectSE.class){
            gamePage.receiveServerEvent(serverEvent);
        }
    }

    public void openUnfinishedGamePage(GameInfoSE gameInfoSE){
        this.gamePage = new GamePage(this, getMainPanel(), gameInfoSE);
        this.gamePage.mShow();
    }
}
