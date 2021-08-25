package view.wellcomepage.mainMenupage.usergame.gamepage;

import events.clientevents.MakeClientNetworkManagerWaitingCE;
import events.clientevents.ResignCurrentGameCE;
import events.clientevents.SendHitedSquareCoordinatesCE;
import events.serverevents.DeclareOpponentDisconnectSE;
import events.serverevents.GameInfoSE;
import events.serverevents.SendGameResultSE;
import events.serverevents.ServerEvent;
import view.MainPanel;
import view.MyPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GamePage extends MyPanel {
    private GraphicalBoard player1Board;
    private GraphicalBoard player2Board;
    private int remainingTime;
    private Thread remainingTimeReducer;
    private boolean a;

    public GamePage(MyPanel previousPage, MainPanel mainPanel, GameInfoSE gameInfo_se) {
        super(previousPage, mainPanel);
        initialize(gameInfo_se);
    }

    public void initialize(GameInfoSE gameInfo_se){
        a = true;
        this.remainingTime = 25;
        if (gameInfo_se.isYourTurn()){
            this.removeAll();
            this.player1Board = new GraphicalBoard(this, gameInfo_se.getBoardsInfo_se().getBoard1FilledSquarsCoordinates(),
                    gameInfo_se.getBoardsInfo_se().getBoard1HitedSquarsCoordinates(), false, false);
            this.player2Board = new GraphicalBoard(this, gameInfo_se.getBoardsInfo_se().getBoard2FilledSquarsCoordinates(),
                    gameInfo_se.getBoardsInfo_se().getBoard2HitedSquarsCoordinates(), gameInfo_se.isYourTurn(), true);

            player1Board.setBounds(100, 210, 300, 300);
            this.add(player1Board);
            player2Board.setBounds(690, 210, 300, 300);
            this.add(player2Board);
            Label turnLabel = new Label("Side to move");
            turnLabel.setForeground(Color.WHITE);
            turnLabel.setBackground(Color.BLACK);
            turnLabel.setBounds(150, 550, 200, 50);
            turnLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(turnLabel);
            Label remainingTimeLabel = new Label(String.valueOf(remainingTime));
            remainingTimeLabel.setBounds(450, 360, 50, 50);
            remainingTimeLabel.setFont(new Font(null, Font.BOLD, 20));
            this.add(remainingTimeLabel);
            Label player1NameLabel = new Label(gameInfo_se.getPlayer1Username(), Label.CENTER);
            player1NameLabel.setBounds(150, 80, 200, 50);
            player1NameLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player1NameLabel);
            Label player2NameLabel = new Label(gameInfo_se.getPlayer2Username(), Label.CENTER);
            player2NameLabel.setBounds(740, 80, 200, 50);
            player2NameLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player2NameLabel);
            Label player1ScoreLabel = new Label("Score: " + gameInfo_se.getPlayer1Score(), Label.CENTER);
            player1ScoreLabel.setBounds(150, 140, 200, 50);
            player1ScoreLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player1ScoreLabel);
            Label player2ScoreLabel = new Label("Score: " + gameInfo_se.getPlayer2Score(), Label.CENTER);
            player2ScoreLabel.setBounds(740, 140, 200, 50);
            player2ScoreLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player2ScoreLabel);
            Label centeralLine = new Label();
            centeralLine.setBackground(Color.BLACK);
            centeralLine.setBounds(538, 100, 4, 520);

            JButton resignButton = new JButton("Resign");
            resignButton.setBackground(Color.RED);
            resignButton.setForeground(Color.WHITE);
            resignButton.setBounds(490, 5, 100, 30);
            resignButton.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    sendClientEvent(new ResignCurrentGameCE(), true);
                }
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            });
            this.add(resignButton);
            this.add(centeralLine);
            this.repaint();
            this.revalidate();
            a = false;

            remainingTimeReducer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (remainingTime != 0){
                        if (a) {break;}
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                        setRemainingTime(getRemainingTime()-1);
                        if (getRemainingTime() == 0){
                            Random random = new Random();
                            sendClientEvent(new SendHitedSquareCoordinatesCE(random.nextInt(10), random.nextInt(10)), true);
                        }
                        remainingTimeLabel.setText(String.valueOf(remainingTime));
                        remainingTimeLabel.repaint();
                        GamePage.this.repaint();
                    }
                }
            });
            remainingTimeReducer.start();
        } else {
            this.removeAll();
            this.player1Board = new GraphicalBoard(this, gameInfo_se.getBoardsInfo_se().getBoard1FilledSquarsCoordinates(),
                    gameInfo_se.getBoardsInfo_se().getBoard1HitedSquarsCoordinates(), false, false);
            this.player2Board = new GraphicalBoard(this, gameInfo_se.getBoardsInfo_se().getBoard2FilledSquarsCoordinates(),
                    gameInfo_se.getBoardsInfo_se().getBoard2HitedSquarsCoordinates(), gameInfo_se.isYourTurn(), true);

            player1Board.setBounds(100, 210, 300, 300);
            this.add(player1Board);
            player2Board.setBounds(690, 210, 300, 300);
            this.add(player2Board);
            Label turnLabel = new Label("Side to move");
            turnLabel.setForeground(Color.WHITE);
            turnLabel.setBackground(Color.BLACK);
            turnLabel.setBounds(740, 550, 200, 50);
            turnLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(turnLabel);
            Label player1NameLabel = new Label(gameInfo_se.getPlayer1Username(), Label.CENTER);
            player1NameLabel.setBounds(150, 80, 200, 50);
            player1NameLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player1NameLabel);
            Label player2NameLabel = new Label(gameInfo_se.getPlayer2Username(), Label.CENTER);
            player2NameLabel.setBounds(740, 80, 200, 50);
            player2NameLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player2NameLabel);
            Label player1ScoreLabel = new Label("Score: " + gameInfo_se.getPlayer1Score(), Label.CENTER);
            player1ScoreLabel.setBounds(150, 140, 200, 50);
            player1ScoreLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player1ScoreLabel);
            Label player2ScoreLabel = new Label("Score: " + gameInfo_se.getPlayer2Score(), Label.CENTER);
            player2ScoreLabel.setBounds(740, 140, 200, 50);
            player2ScoreLabel.setFont(new Font(null, Font.BOLD, 25));
            this.add(player2ScoreLabel);
            Label centeralLine = new Label();
            centeralLine.setBackground(Color.BLACK);
            centeralLine.setBounds(538, 100, 4, 520);
            this.add(centeralLine);
            this.repaint();
            this.revalidate();
        }
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        if (serverEvent.getClass() == SendGameResultSE.class){
            if (remainingTimeReducer != null){
                try {
                    remainingTimeReducer.interrupt();
                }catch (Exception ignored){}
            }
            if (((SendGameResultSE) serverEvent).isWinner()){
                JOptionPane.showMessageDialog(null, "Victory");
            } else {
                JOptionPane.showMessageDialog(null, "Defeat");
            }

            getPreviousPage().getPreviousPage().mShow();
        }else
        if (serverEvent.getClass() == DeclareOpponentDisconnectSE.class){
            JOptionPane.showMessageDialog(null, "Your opponent is disconnected, you will win the game in 60 seconds.");
            sendClientEvent(new MakeClientNetworkManagerWaitingCE(), true);
        }
    }
}
