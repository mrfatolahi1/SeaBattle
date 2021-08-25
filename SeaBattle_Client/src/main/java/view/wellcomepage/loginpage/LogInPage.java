package view.wellcomepage.loginpage;


import events.serverevents.LogInResultSE;
import events.serverevents.ServerEvent;
import events.clientevents.LogInEventCE;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.WellComePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LogInPage extends MyPanel {
    public LogInPage(MyPanel previousPage, MainPanel mainPanel) {
        super(previousPage, mainPanel);
        this.setBackground(Color.ORANGE);

        JLabel usernameLabel = new JLabel("Username:", SwingConstants.CENTER);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(340, 200, 200, 50);
        this.add(usernameLabel);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(540, 200, 200, 50);
        this.add(usernameTextField);

        JLabel passwordLabel = new JLabel("Password:", SwingConstants.CENTER);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(340, 270, 200, 50);
        this.add(passwordLabel);
        JTextField passwordTextField = new JTextField();
        passwordTextField.setBounds(540, 270, 200, 50);
        this.add(passwordTextField);

        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(Color.WHITE);
        loginButton.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                sendClientEvent(new LogInEventCE(usernameTextField.getText(), passwordTextField.getText()), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        loginButton.setBounds(470, 360, 200, 50);
        this.add(loginButton);


        this.repaint();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        switch (((LogInResultSE) serverEvent).getLogInResult()){
            case WRONG_PASSWORD -> JOptionPane.showMessageDialog(null, "Wrong Password.");
            case USERNAME_DONT_EXISTS -> JOptionPane.showMessageDialog(null, "There is no user with this username.");
            case SUCCESSFUL -> ((WellComePage) previousPage).openMainMenuPage();
        }
    }
}
