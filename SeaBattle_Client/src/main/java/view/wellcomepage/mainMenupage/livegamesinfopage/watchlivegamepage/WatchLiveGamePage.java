package view.wellcomepage.mainMenupage.livegamesinfopage.watchlivegamepage;

import events.serverevents.BoardsInfoSE;
import events.serverevents.DeclareLiveGameFinishSE;
import events.serverevents.LiveGameInfoSE;
import events.serverevents.ServerEvent;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.mainMenupage.usergame.gamepage.GraphicalBoard;

import javax.swing.*;
import java.awt.*;

public class WatchLiveGamePage extends MyPanel{
    private GraphicalBoard player1Board;
    private GraphicalBoard player2Board;
    private final String player1Username;

    public WatchLiveGamePage(MyPanel previousPage, MainPanel mainPanel, LiveGameInfoSE liveGameInfo_SE) {
        super(previousPage, mainPanel);
        this.player1Username = liveGameInfo_SE.getPlayer1Username();
        initialize(liveGameInfo_SE);
    }

    public void initialize(LiveGameInfoSE liveGameInfo_SE){
        if (!liveGameInfo_SE.getPlayer1Username().equals(player1Username)){
            BoardsInfoSE boardsInfo_se1 = new BoardsInfoSE(
                    liveGameInfo_SE.getBoardsInfo_se().getBoard2FilledSquarsCoordinates(),
                    liveGameInfo_SE.getBoardsInfo_se().getBoard1FilledSquarsCoordinates(),
                    liveGameInfo_SE.getBoardsInfo_se().getBoard2HitedSquarsCoordinates(),
                    liveGameInfo_SE.getBoardsInfo_se().getBoard1HitedSquarsCoordinates(), false);

            liveGameInfo_SE = new LiveGameInfoSE(
                    boardsInfo_se1,
                    liveGameInfo_SE.getPlayer2Username(), liveGameInfo_SE.getPlayer1Username(),
                    liveGameInfo_SE.getPlayer2Score(), liveGameInfo_SE.getPlayer1Score(),
                    liveGameInfo_SE.isYourTurn(),
                    liveGameInfo_SE.getPlayer2NumberOfRemainingShips(),
                    liveGameInfo_SE.getPlayer2NumberOfDestroyedShips(),
                    liveGameInfo_SE.getPlayer1NumberOfRemainingShips(),
                    liveGameInfo_SE.getPlayer1NumberOfDestroyedShips(),
                    liveGameInfo_SE.isUpdate());
        }
        this.removeAll();
        this.player1Board = new GraphicalBoard(this, liveGameInfo_SE.getBoardsInfo_se().getBoard1FilledSquarsCoordinates(),
                liveGameInfo_SE.getBoardsInfo_se().getBoard1HitedSquarsCoordinates(), false, false);
        this.player2Board = new GraphicalBoard(this, liveGameInfo_SE.getBoardsInfo_se().getBoard2FilledSquarsCoordinates(),
                liveGameInfo_SE.getBoardsInfo_se().getBoard2HitedSquarsCoordinates(), false, false);


        player1Board.setBounds(100, 210, 300, 300);
        this.add(player1Board);
        player2Board.setBounds(690, 210, 300, 300);
        this.add(player2Board);
        Label player1NameLabel = new Label(liveGameInfo_SE.getPlayer1Username(), Label.CENTER);
        player1NameLabel.setBounds(150, 80, 200, 50);
        player1NameLabel.setFont(new Font("NATS", Font.BOLD, 25));
        this.add(player1NameLabel);
        Label player2NameLabel = new Label(liveGameInfo_SE.getPlayer2Username(), Label.CENTER);
        player2NameLabel.setBounds(740, 80, 200, 50);
        player2NameLabel.setFont(new Font("NATS", Font.BOLD, 25));
        this.add(player2NameLabel);
        Label player1ScoreLabel = new Label("Score: "+liveGameInfo_SE.getPlayer1Score(), Label.CENTER);
        player1ScoreLabel.setBounds(150, 140, 200, 50);
        player1ScoreLabel.setFont(new Font("NATS", Font.BOLD, 25));
        this.add(player1ScoreLabel);
        Label player2ScoreLabel = new Label("Score: "+liveGameInfo_SE.getPlayer2Score(), Label.CENTER);
        player2ScoreLabel.setBounds(740, 140, 200, 50);
        player2ScoreLabel.setFont(new Font("NATS", Font.BOLD, 25));
        this.add(player2ScoreLabel);
        Label centeralLine = new Label();
        centeralLine.setBackground(Color.BLACK);
        centeralLine.setBounds(538, 100, 4, 520);
        this.add(centeralLine);
        this.repaint();
        this.revalidate();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        if (serverEvent.getClass() == DeclareLiveGameFinishSE.class){
            JOptionPane.showMessageDialog(null, "Game Finished");
            getPreviousPage().getPreviousPage().mShow();
        }
    }
}
