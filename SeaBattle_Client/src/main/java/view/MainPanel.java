package view;


import events.EventManager;
import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;
import view.wellcomepage.WellComePage;

import javax.swing.*;

public class MainPanel extends JPanel implements EventManager {
    private final MainFrame mainFrame;
    private final WellComePage wellComePage;

    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null);
        this.wellComePage = new WellComePage(null, this);
        this.add(wellComePage);
        this.setBounds(0, 0, 1080, 720);
        this.repaint();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        wellComePage.receiveServerEvent(serverEvent);
    }

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        mainFrame.sendClientEvent(clientEvent, hasAnswer);
    }
}
