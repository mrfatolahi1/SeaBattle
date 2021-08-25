package view.wellcomepage.mainMenupage.usergame.gamepage;

import events.EventManager;
import events.clientevents.ClientEvent;
import events.clientevents.SendHitedSquareCoordinatesCE;
import events.serverevents.ServerEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GraphicalBoard extends JPanel implements EventManager{
    private final EventManager eventManager;

    public GraphicalBoard(EventManager eventManager, ArrayList<ArrayList<Integer>> filledSquarsCoordinates,
                          ArrayList<ArrayList<Integer>> hitedSquarsCoordinates,
                          boolean hasMouseListener, boolean isOpponent) {
        this.eventManager = eventManager;
        this.setLayout(null);
        this.setMaximumSize(new Dimension(300, 300));
        for (int i = 0; i < 300; i+=30) {
            for (int j = 0; j < 300; j+=30) {
                JLabel label = new JLabel();
                label.setBounds(i+1, j+1, 28, 28);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                if (!isOpponent){
                    for (ArrayList<Integer> a : filledSquarsCoordinates) {
                        if (a.get(0) == i / 30 && a.get(1) == j / 30) {
                            label.setBackground(Color.RED);
                        }
                    }
                }

                for (ArrayList<Integer> a: hitedSquarsCoordinates){
                    if (a.get(0) == i/30 && a.get(1) == j/30){
                        ArrayList<Integer> p = new ArrayList<>();
                        p.add(i/30); p.add(j/30);
                        if (filledSquarsCoordinates.contains(p)){
                            label.setBackground(Color.DARK_GRAY);
                        } else {
                            label.setBackground(Color.GRAY);
                        }
                    }
                }
                label.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
                if (hasMouseListener){
                    label.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            sendClientEvent(new SendHitedSquareCoordinatesCE(label.getX()/30, label.getY()/30), true);
                        }
                        public void mousePressed(MouseEvent e) {}
                        public void mouseReleased(MouseEvent e) {}
                        public void mouseEntered(MouseEvent e) {
                            label.setBorder(BorderFactory.createLineBorder(new Color(0, 155, 0), 4));
                            label.repaint();
                            label.revalidate();
                        }
                        public void mouseExited(MouseEvent e) {
                            label.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
                            label.repaint();
                            label.revalidate();
                        }
                    });
                }
                label.repaint();
                label.revalidate();
                this.add(label);
                this.repaint();
                this.revalidate();
            }
        }
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {}

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        eventManager.sendClientEvent(clientEvent, hasAnswer);
    }
}
