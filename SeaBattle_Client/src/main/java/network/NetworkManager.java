package network;

import controller.MainController;
import events.EventManager;
import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class NetworkManager implements EventManager {
    private final MainController mainController;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;

    public NetworkManager(MainController mainController) throws IOException {
        this.mainController = mainController;
        start();
    }

    public void start() throws IOException {
        try {
            Properties properties = new Properties();
            InputStream inputStream = new FileInputStream("conncetion.properties");
            properties.load(inputStream);

            this.socket = new Socket(properties.getProperty("address"), Integer.parseInt(properties.getProperty("port")));
        } catch (Exception e) {
            this.socket = new Socket("localhost", 8000);
        }
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        mainController.receiveServerEvent(serverEvent);
    }

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        try {
            objectOutputStream.writeObject(clientEvent);
        } catch (IOException ignored) {}
    }

    public void waitForServerAnswer() throws IOException, ClassNotFoundException {
        try {
            Object object = objectInputStream.readObject();
            receiveServerEvent((ServerEvent) object);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "you must reconnect in 60 seconds or you will lose the game.", "Connection lost", JOptionPane.WARNING_MESSAGE);
        }
    }
}
