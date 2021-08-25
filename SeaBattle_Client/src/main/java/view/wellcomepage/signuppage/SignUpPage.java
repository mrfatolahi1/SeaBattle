package view.wellcomepage.signuppage;

import events.serverevents.ServerEvent;
import events.serverevents.SignUpResultSE;
import events.clientevents.SignUpEventCE;
import view.MainPanel;
import view.MyPanel;
import view.wellcomepage.WellComePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SignUpPage extends MyPanel {
    public SignUpPage(MyPanel previousPage, MainPanel mainPanel) {
        super(previousPage, mainPanel);
        this.setBackground(Color.MAGENTA);

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

        JButton signUpbutton = new JButton("Sign Up");
        signUpbutton.setBackground(Color.WHITE);
        signUpbutton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendClientEvent(new SignUpEventCE(usernameTextField.getText(), passwordTextField.getText()), true);
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        signUpbutton.setBounds(470, 360, 200, 50);
        this.add(signUpbutton);


        this.repaint();
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        switch (((SignUpResultSE) serverEvent).getSignUpResult()){
            case USERNAME_IS_TAKEN -> JOptionPane.showMessageDialog(null, "This username is already taken.");
            case SUCCESSFUL -> ((WellComePage) previousPage).openMainMenuPage();
        }
    }
}
