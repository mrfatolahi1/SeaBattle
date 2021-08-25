package controller;

import models.Game;
import network.NetworkManager;
import saversandloaders.Loader;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MainController {
    private final NetworkManager networkManager;
    private final ArrayList<ClientHandler> connectedClients;
    private final ArrayList<ClientHandler> waitingForOpponentQueue;

    public MainController() throws IOException {
        this.networkManager = new NetworkManager(this);
        this.connectedClients = new ArrayList<>();
        this.waitingForOpponentQueue = new ArrayList<>();
    }

    public void start() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                manageWaitingForOpponentQueue();
            }
        })).start();
        while (true){
            Socket socket = networkManager.waitForACliet();
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ClientHandler client = new ClientHandler(MainController.this, socket);
                        connectedClients.add(client);
                        client.start();
                    } catch (IOException | ClassNotFoundException ignored) {}
                }
            })).start();
        }
    }

    public void addClientToWaitingForOpponentQueue(ClientHandler clientHandler){
        waitingForOpponentQueue.add(clientHandler);
        synchronized (waitingForOpponentQueue){
            waitingForOpponentQueue.notifyAll();
        }
    }

    private void manageWaitingForOpponentQueue(){
        while (true){
            synchronized (waitingForOpponentQueue){
                try {
                    waitingForOpponentQueue.wait();
                } catch (InterruptedException ignored) {}

                if (waitingForOpponentQueue.size() == 2){
                    waitingForOpponentQueue.get(0).setCurrentOpponentId(waitingForOpponentQueue.get(1).getId());
                    waitingForOpponentQueue.get(1).setCurrentOpponentId(waitingForOpponentQueue.get(0).getId());
                    waitingForOpponentQueue.get(0).sendRandomBoardInfo(false);
                    waitingForOpponentQueue.get(1).sendRandomBoardInfo(false);
                    waitingForOpponentQueue.clear();
                }
            }
        }
    }

    public ArrayList<ClientHandler> getConnectedClients() {
        return connectedClients;
    }

    public ArrayList<ArrayList<Object>> getLiveGamesInfo() throws IOException {
        ArrayList<ArrayList<Object>> gamesInfo = new ArrayList<>();
        ArrayList<Integer> addedIds = new ArrayList<>();

        for (ClientHandler client : connectedClients){
            if (client.getUser().getCurrentGame() == null){continue;}
            if (!addedIds.contains(client.getUser().getCurrentGame().getPlayer1Id()) && !addedIds.contains(client.getUser().getCurrentGame().getPlayer2Id())){
                ArrayList<Object> a = new ArrayList<>();
                a.add(Loader.loadUser(client.getUser().getCurrentGame().getPlayer1Id()).getUsername());
                a.add(Loader.loadUser(client.getUser().getCurrentGame().getPlayer2Id()).getUsername());
                a.add(client.getUser().getCurrentGame().getPlayer2Board().getHitedSquarsAsListOfLists().size());
                a.add(client.getUser().getCurrentGame().getPlayer1Board().getHitedSquarsAsListOfLists().size());
                a.add(client.getUser().getCurrentGame().getPlayer1Score());
                a.add(client.getUser().getCurrentGame().getPlayer2Score());
                a.add(client.getUser().getCurrentGame().getPlayer1Board().getNumberOfRemainingShips());
                a.add(client.getUser().getCurrentGame().getPlayer1Board().getNumberOfDestroyedShips());
                a.add(client.getUser().getCurrentGame().getPlayer2Board().getNumberOfRemainingShips());
                a.add(client.getUser().getCurrentGame().getPlayer2Board().getNumberOfDestroyedShips());

                gamesInfo.add(a);
                addedIds.add(client.getUser().getCurrentGame().getPlayer1Id());
                addedIds.add(client.getUser().getCurrentGame().getPlayer2Id());
            }
        }
        return gamesInfo;
    }

    public Game getGame(int spectatorId, String oneOfPlayersUsername) {
        for (ClientHandler client : connectedClients){
            if (client.getUser().getCurrentGame() != null){
                try {
                    if (Loader.loadUser(client.getUser().getCurrentGame().getPlayer1Id()).getUsername().equals(oneOfPlayersUsername) ||
                            Loader.loadUser(client.getUser().getCurrentGame().getPlayer2Id()).getUsername().equals(oneOfPlayersUsername)){
                        getClientById(client.getUser().getCurrentGame().getPlayer1Id()).getCurrentGameSpectatorsIds().add(spectatorId);
                        getClientById(client.getUser().getCurrentGame().getPlayer2Id()).getCurrentGameSpectatorsIds().add(spectatorId);
                        return client.getUser().getCurrentGame();
                    }
                } catch (IOException ignored) {}
            }
        }
        return null;
    }

    public void updateGameSpectators(Game liveGame, ArrayList<Integer> spectatorsIds){
        for (int spectatorId : spectatorsIds){
            for (ClientHandler client : connectedClients){
                if (client.getUser() == null){continue;}
                if (client.getUser().getId() == spectatorId){
                    client.updateLiveStream(liveGame);
                }
            }
        }
    }

    public ClientHandler getClientById(int id){
        for (ClientHandler clientHandler : connectedClients){
            if (clientHandler.getUser() == null){continue;}
            if (clientHandler.getUser().getId() == id){
                return clientHandler;
            }
        }
        return null;
    }

    public final boolean checkClientConnection(int id){
        for (ClientHandler clientHandler : connectedClients){
            if (clientHandler.getUser().getId() == id){
                return true;
            }
        }

        return false;
    }

    public static String generateNewAuthToken(){
        String chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+=}{:;?/|>.<,";
        Random random = new Random();

        String authToken = "";

        for (int i = 0; i < 100; i++) {
            authToken = authToken + chars.charAt(random.nextInt(chars.length()));
        }

        return authToken;
    }
}
