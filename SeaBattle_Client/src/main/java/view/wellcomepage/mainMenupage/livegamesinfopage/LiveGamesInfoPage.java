package view.wellcomepage.mainMenupage.livegamesinfopage;

import events.EventInterpreter;
import events.clientevents.MakeClientNetworkManagerWaitingCE;
import events.clientevents.RequestLiveGameInfoCE;
import events.serverevents.*;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.mainMenupage.livegamesinfopage.watchlivegamepage.WatchLiveGamePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LiveGamesInfoPage extends MyPanel implements EventInterpreter {
    private WatchLiveGamePage watchLiveGamePage;
    public LiveGamesInfoPage(MyPanel previousPage, MainPanel mainPanel, SendLiveGamesInfoSE sendLiveGamesInfo_se) {
        super(previousPage, mainPanel);

        this.setBackground(Color.GREEN);

        Object [][] data = new Object[sendLiveGamesInfo_se.getGamesInfo().size()][10];
        int a = 0;
        for (ArrayList<Object> gameInfo : sendLiveGamesInfo_se.getGamesInfo()){
            System.out.println("gameInfo = " + gameInfo);
            int b = 0;
            for (Object info : gameInfo){
                data[a][b] = info;
                b++;
            }
            a++;
        }

        String [] columnNames = {
                "Player1", "Player2", "P1 shots", "P1 shots",
                "P1 hited shots", "P1 hited shots", "P1 remaining ships",
                "P1 destroyed ships", "P2 remaining ships", "P2 destroyed ships"
        };

        JTable table = new JTable(data, columnNames);

        table.setPreferredScrollableViewportSize(new Dimension(800, 50));
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setFont(new Font(null, Font.ITALIC, 10));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 880, 50);
        this.add(scrollPane);

        JButton watchButton = new JButton("Watch");
        watchButton.setBounds(930, 100, 120, 90);
        watchButton.setFont(new Font(null, Font.PLAIN, 25));
        watchButton.setBackground(Color.PINK);
        watchButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                try {
                    sendClientEvent(new RequestLiveGameInfoCE((String) table.getValueAt(table.getSelectedRow(), 1)), true);
                }catch (ArrayIndexOutOfBoundsException y){
                    JOptionPane.showMessageDialog(null, "there is no game to watch");
                }
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        this.add(watchButton);
        this.repaint();
        this.revalidate();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        interpretServerEvent(serverEvent);
    }

    @Override
    public <E extends ServerEvent> void interpretServerEvent(E serverEvent) {
        if (serverEvent.getClass() == LiveGameInfoSE.class){
            if (!((LiveGameInfoSE) serverEvent).isUpdate()){
                this.watchLiveGamePage = new WatchLiveGamePage(this, getMainPanel(), ((LiveGameInfoSE) serverEvent));
                this.watchLiveGamePage.mShow();
            } else {
                this.watchLiveGamePage.initialize(((LiveGameInfoSE) serverEvent));
                this.watchLiveGamePage.mShow();
            }
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!((LiveGameInfoSE) serverEvent).isYourTurn()){
                        sendClientEvent(new MakeClientNetworkManagerWaitingCE(), true);
                    }
                }
            })).start();
        }else
        if (serverEvent.getClass() == DeclareLiveGameFinishSE.class){
            watchLiveGamePage.receiveServerEvent(serverEvent);
        }
    }
}
