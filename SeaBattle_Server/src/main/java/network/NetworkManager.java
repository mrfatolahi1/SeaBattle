package network;

import controller.MainController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;


public class NetworkManager {
    private final MainController mainController;
    private ServerSocket serverSocket;

    public NetworkManager(MainController mainController) throws IOException {
        this.mainController = mainController;
        try {
            Properties properties = new Properties();
            InputStream inputStream = new FileInputStream("conncetion.properties");
            properties.load(inputStream);
            this.serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port")));
        }catch (Exception e){
            this.serverSocket = new ServerSocket(8000);
        }
    }

    public Socket waitForACliet(){
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            return null;
        }
    }
}
