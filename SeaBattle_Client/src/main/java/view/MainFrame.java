package view;

import controller.MainController;
import events.EventManager;
import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements EventManager {
    private final MainController mainController;
    private final MainPanel mainPanel;

    public MainFrame(MainController mainController) throws HeadlessException {
        this.mainController = mainController;
        this.setSize(1080, 720);
        this.setResizable(false);
        this.setLayout(null);
        this.mainPanel = new MainPanel(this);
        this.add(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.repaint();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        mainPanel.receiveServerEvent(serverEvent);
    }

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        mainController.sendClientEvent(clientEvent, hasAnswer);
    }
}
