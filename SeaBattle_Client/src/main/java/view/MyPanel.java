package view;

import events.EventManager;
import events.clientevents.DeclareClientExitCE;
import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyPanel extends JPanel implements EventManager {
    protected final MainPanel mainPanel;
    protected final MyPanel previousPage;

    public MyPanel(MyPanel previousPage, MainPanel mainPanel) {
        this.previousPage = previousPage;
        this.mainPanel = mainPanel;
        this.setLayout(null);
        this.setBounds(0, 0, 1080, 720);

        JButton backButton = new JButton("Back");
        backButton.setBounds(1000, 600, 80, 30);
        backButton.setBackground(Color.WHITE);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                back();
            }
            public void mousePressed(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(Color.CYAN);
            }
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(Color.WHITE);
            }
        });
        this.add(backButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(1000, 640, 80, 30);
        exitButton.setBackground(Color.WHITE);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exit();
            }
            public void mousePressed(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.RED);
            }
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.WHITE);
            }
        });
        this.add(exitButton);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public MyPanel getPreviousPage() {
        return previousPage;
    }

    public void mShow(){
        mainPanel.removeAll();
        mainPanel.add(this);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void back(){
        mainPanel.removeAll();
        mainPanel.add(previousPage);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void exit(){
        sendClientEvent(new DeclareClientExitCE(), false);
        System.exit(0);
    }



    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {

    }

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        previousPage.sendClientEvent(clientEvent, hasAnswer);
    }
}
