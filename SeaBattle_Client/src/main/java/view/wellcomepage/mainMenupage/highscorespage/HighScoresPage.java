package view.wellcomepage.mainMenupage.highscorespage;

import events.serverevents.HighScoresInfoSE;
import events.serverevents.SendUserInfoSE;
import view.MainPanel;
import view.MyPanel;

import javax.swing.*;
import java.awt.*;

public class HighScoresPage extends MyPanel {
    public HighScoresPage(MyPanel previousPage, MainPanel mainPanel, HighScoresInfoSE highScoresInfo_se) {
        super(previousPage, mainPanel);
        this.setBackground(Color.CYAN);

        Object [][] data = new Object[highScoresInfo_se.getUsersInfo().size()][6];
        int a = 0;
        for (SendUserInfoSE sendUserInfo_se : highScoresInfo_se.getUsersInfo()){
            int b = 0;
            data[a][b] = a+1;
            b++;
            data[a][b] = sendUserInfo_se.getUsername();
            b++;
            data[a][b] = sendUserInfo_se.getNumberOfWins();
            b++;
            data[a][b] = sendUserInfo_se.getNumberOfLosts();
            b++;
            data[a][b] = sendUserInfo_se.getTotalScore();
            b++;
            if (sendUserInfo_se.isOnline()){
                data[a][b] = "Online";
            }else {
                data[a][b] = "Offline";
            }
            a++;
        }

        String [] columnNames = {"Number","Username", "Wins", "Losts", "Total Score", "Connection"};

        JTable table = new JTable(data, columnNames);

        table.setPreferredScrollableViewportSize(new Dimension(800, 500));
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 80, 800, 500);
        this.add(scrollPane);
    }
}
