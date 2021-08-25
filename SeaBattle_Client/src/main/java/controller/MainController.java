package controller;

import events.EventInterpreter;
import events.EventManager;
import events.clientevents.LogInEventCE;
import events.clientevents.MakeClientNetworkManagerWaitingCE;
import events.clientevents.SignUpEventCE;
import events.serverevents.SendAuthTokenSE;
import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;
import network.NetworkManager;
import view.MainFrame;

import java.io.IOException;


public class MainController implements EventManager, EventInterpreter {
    private final NetworkManager networkManager;
    private final MainFrame mainFrame;
    private String authToken;

    public MainController() throws IOException {
        this.networkManager = new NetworkManager(this);
        this.mainFrame = new MainFrame(this);
    }

    @Override
    public <E extends ServerEvent> void receiveServerEvent(E serverEvent) {
        interpretServerEvent(serverEvent);
    }

    @Override
    public <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer) {
        if (clientEvent.getClass() != LogInEventCE.class && clientEvent.getClass() != SignUpEventCE.class){
            clientEvent.setAuthToken(authToken);
        }
        if (clientEvent.getClass() == MakeClientNetworkManagerWaitingCE.class){
            try {
                networkManager.waitForServerAnswer();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }

        networkManager.sendClientEvent(clientEvent, hasAnswer);

        if (hasAnswer){
            try {
                networkManager.waitForServerAnswer();
            } catch (IOException | ClassNotFoundException ignored) {}
        }
    }

    @Override
    public <E extends ServerEvent> void interpretServerEvent(E serverEvent) {
        if (serverEvent.getClass() == SendAuthTokenSE.class){
            this.authToken = ((SendAuthTokenSE) serverEvent).getAuthToken();
            try {
                networkManager.waitForServerAnswer();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }
        mainFrame.receiveServerEvent(serverEvent);
    }
}
