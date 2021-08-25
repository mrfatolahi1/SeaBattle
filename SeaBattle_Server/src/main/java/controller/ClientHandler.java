package controller;

import events.clientevents.*;
import events.serverevents.*;
import models.Game;
import models.LogInResults;
import models.SignUpResults;
import models.User;
import models.board.Board;
import saversandloaders.Loader;
import saversandloaders.Saver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ClientHandler {
    private String authToken;
    private final MainController mainController;
    private User user;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final Socket socket;
    private int id;
    private boolean mustDie;
    private ArrayList<Integer> currentGameSpectatorsIds;
    private int currentOpponentId;
    private int currentBoardrRandomMapNumber;
    private boolean readyForGame;
    private boolean reConnected;

    public ClientHandler(MainController mainController, Socket socket) throws IOException {
        this.mainController = mainController;
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.currentGameSpectatorsIds = new ArrayList<>();
        readyForGame = false;
        this.reConnected = false;
        this.id = -1;
    }

    public void start() throws IOException, ClassNotFoundException {
        manageClientEvents();
    }

    private void manageClientEvents() throws IOException {
        while (!mustDie) {
            ClientEvent clientEvent;
            try {
                clientEvent = (ClientEvent) objectInputStream.readObject();
            }catch (Exception e){
                System.out.println(user.getUsername()+" disconnected :(");
                mainController.getClientById(currentOpponentId).declareOpponentDissconnect();

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException ignored) {}

                if (reConnected){
                    this.close();
                    continue;
                }

                resignCurrentGame();
                close();
                continue;
            }

            if (clientEvent.getClass() != LogInEventCE.class && clientEvent.getClass() != SignUpEventCE.class){
                if (!checkAuthToken(clientEvent)){
                    continue;
                }
            }

            if (clientEvent.getClass() == LogInEventCE.class) {
                logIn(clientEvent);
            } else
            if (clientEvent.getClass() == SignUpEventCE.class) {
                signUp(clientEvent);
            } else
            if (clientEvent.getClass() == RequestNewGameCE.class){
                mainController.addClientToWaitingForOpponentQueue(this);
            } else
            if (clientEvent.getClass() == SendHitedSquareCoordinatesCE.class){
                hitAnOpponentSquare((SendHitedSquareCoordinatesCE) clientEvent);
            } else
            if (clientEvent.getClass() == RequestLiveGamesInfoCE.class){
                objectOutputStream.writeObject(new SendLiveGamesInfoSE(mainController.getLiveGamesInfo()));
            } else
            if (clientEvent.getClass() == RequestLiveGameInfoCE.class){
                sendLiveGameInfo(((RequestLiveGameInfoCE) clientEvent).getPlayer1Username());
            } else
            if (clientEvent.getClass() == RequestNewRandomizeCE.class){
                sendRandomBoardInfo(true);
            } else
            if (clientEvent.getClass() == ReadyForStartGameCE.class){
                this.createNewGame();
            } else
            if (clientEvent.getClass() == RequestUserInfoCE.class){
                sendUserInfo();
            } else
            if (clientEvent.getClass() == RequestHighScoresTableInfoCE.class){
                sendHighScoresTableInfo();
            } else
            if (clientEvent.getClass() == ResignCurrentGameCE.class){
                resignCurrentGame();
            } else
            if (clientEvent.getClass() == DeclareClientExitCE.class){
                this.close();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public int getId() {
        return id;
    }

    public boolean isMustDie() {
        return mustDie;
    }

    public void setMustDie(boolean mustDie) {
        this.mustDie = mustDie;
    }

    public ArrayList<Integer> getCurrentGameSpectatorsIds() {
        return currentGameSpectatorsIds;
    }

    public int getCurrentOpponentId() {
        return currentOpponentId;
    }

    public void setCurrentOpponentId(int currentOpponentId) {
        this.currentOpponentId = currentOpponentId;
    }

    public int getCurrentBoardrRandomMapNumber() {
        return currentBoardrRandomMapNumber;
    }

    public void setCurrentBoardrRandomMapNumber(int currentBoardrRandomMapNumber) {
        this.currentBoardrRandomMapNumber = currentBoardrRandomMapNumber;
    }

    private void signUp(ClientEvent clientEvent) throws IOException {
        authToken = MainController.generateNewAuthToken();
        objectOutputStream.writeObject(new SendAuthTokenSE(authToken));
        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {}
        SignUpResults b = UserController.signUp(((SignUpEventCE) clientEvent).getUsername(), ((SignUpEventCE) clientEvent).getPassword());
        if (b == SignUpResults.SUCCESSFUL) {
            this.user = Loader.loadUser(((SignUpEventCE) clientEvent).getUsername());
            id = user.getId();
        }
        objectOutputStream.writeObject(new SignUpResultSE(b));
    }

    private void logIn(ClientEvent clientEvent) throws IOException {
        authToken = MainController.generateNewAuthToken();
        objectOutputStream.writeObject(new SendAuthTokenSE(authToken));
        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {}
        LogInResults a = UserController.logIn(((LogInEventCE) clientEvent).getUsername(), ((LogInEventCE) clientEvent).getPassword());
        if (a == LogInResults.SUCCESSFUL) {
            if (checkReConnectivity((LogInEventCE) clientEvent)){
                return;
            }
            this.user = Loader.loadUser(((LogInEventCE) clientEvent).getUsername());
            id = user.getId();
        }
        objectOutputStream.writeObject(new LogInResultSE(a));
    }

    public void createNewGame(){
        System.out.println("OOOOOOOOOOOOOOOO");
        this.readyForGame = true;
        if (mainController.getClientById(currentOpponentId).isReadyForGame()){
            return;
        }
        System.out.println(":::::::");
        while (!mainController.getClientById(currentOpponentId).isReadyForGame()){
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {}
//            System.out.println("AAAA");
        }
        System.out.println("AAAAAAAAAAAA");
        Game game1 = new Game(this.getId(), mainController.getClientById(currentOpponentId).getId(), this.getCurrentBoardrRandomMapNumber(), mainController.getClientById(currentOpponentId).getCurrentBoardrRandomMapNumber());
        Game game2 = new Game(mainController.getClientById(currentOpponentId).getId(), this.getId(), mainController.getClientById(currentOpponentId).getCurrentBoardrRandomMapNumber(), this.getCurrentBoardrRandomMapNumber());
        Random random = new Random();
        int rand = random.nextInt(2);
        if (rand == 0){
            game1.setCurrentPlayerId(this.getUser().getId());
            game2.setCurrentPlayerId(this.getUser().getId());
        }else {
            game1.setCurrentPlayerId(currentOpponentId);
            game2.setCurrentPlayerId(currentOpponentId);
        }
        this.startNewGame(game1);
        mainController.getClientById(currentOpponentId).startNewGame(game2);

    }

    public void startNewGame(Game game){
        UserController.startANewGame(user, game);
        BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                game.getPlayer1Board().getFullSquarsAsListOfLists (), game.getPlayer2Board().getFullSquarsAsListOfLists (),
                game.getPlayer1Board().getHitedSquarsAsListOfLists(), game.getPlayer2Board().getHitedSquarsAsListOfLists(), false
        );
        try {
            objectOutputStream.writeObject(new GameInfoSE(boardsInfo_se, Loader.loadUser(game.getPlayer1Id()).getUsername(),
                    Loader.loadUser(game.getPlayer2Id()).getUsername(), game.getPlayer1Score(), game.getPlayer2Score(), game.checkTurn(user.getId()), false));
        } catch (IOException ignored) {}
    }

    private void resignCurrentGame(){
        this.getUser().getCurrentGame().setFinished(true);
        this.getUser().getCurrentGame().setWinnerId(currentOpponentId);
        mainController.getClientById(currentOpponentId).getUser().getCurrentGame().setFinished(true);
        mainController.getClientById(currentOpponentId).getUser().getCurrentGame().setWinnerId(currentOpponentId);
        mainController.getClientById(currentOpponentId).finishCurrentGame(true);
        this.finishCurrentGame(false);
    }

    private void hitAnOpponentSquare(SendHitedSquareCoordinatesCE sendHitedSquareCoordinates_ce){
        GameController.hitASquare(user, user.getCurrentGame(), sendHitedSquareCoordinates_ce.getX(), sendHitedSquareCoordinates_ce.getY(), false);
        try {
            Saver.saveUser(user);
        } catch (IOException ignored) {}
        if (user.getCurrentGame().getPlayer1Id() == user.getId()){
            for (ClientHandler client : mainController.getConnectedClients()){
                if (client.getUser() == null){continue;}
                if (client.getUser().getId() == user.getCurrentGame().getPlayer2Id()){
                    client.hitAnOwnSquare(sendHitedSquareCoordinates_ce);
                }
            }
        } else {
            for (ClientHandler client : mainController.getConnectedClients()){
                if (client.getUser() == null){continue;}
                if (client.getUser().getId() == user.getCurrentGame().getPlayer1Id()){
                    client.hitAnOwnSquare(sendHitedSquareCoordinates_ce);
                }
            }
        }

        if (user.getCurrentGame().checkForEndGame()){
            finishCurrentGame(true);
            return;
        }
        user.getCurrentGame().changeCurrentPlayer();
        BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                user.getCurrentGame().getPlayer1Board().getFullSquarsAsListOfLists (), user.getCurrentGame().getPlayer2Board().getFullSquarsAsListOfLists (),
                user.getCurrentGame().getPlayer1Board().getHitedSquarsAsListOfLists(), user.getCurrentGame().getPlayer2Board().getHitedSquarsAsListOfLists(), true
        );
        try {
            objectOutputStream.writeObject(
                    new GameInfoSE(boardsInfo_se, Loader.loadUser(user.getCurrentGame().getPlayer1Id()).getUsername(),
                            Loader.loadUser(user.getCurrentGame().getPlayer2Id()).getUsername(), user.getCurrentGame().getPlayer1Score(),
                            user.getCurrentGame().getPlayer2Score(), user.getCurrentGame().checkTurn(user.getId()), true));
        } catch (IOException ignored) {}
        mainController.updateGameSpectators(user.getCurrentGame(), currentGameSpectatorsIds);
    }

    public void hitAnOwnSquare(SendHitedSquareCoordinatesCE sendHitedSquareCoordinates_ce){
        GameController.hitASquare(user, user.getCurrentGame(), sendHitedSquareCoordinates_ce.getX(), sendHitedSquareCoordinates_ce.getY(), true);
        try {
            Saver.saveUser(user);
        } catch (IOException ignored) {}

        if (user.getCurrentGame().checkForEndGame()){
            finishCurrentGame(false);
            return;
        }

        user.getCurrentGame().changeCurrentPlayer();
        BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                user.getCurrentGame().getPlayer1Board().getFullSquarsAsListOfLists (), user.getCurrentGame().getPlayer2Board().getFullSquarsAsListOfLists (),
                user.getCurrentGame().getPlayer1Board().getHitedSquarsAsListOfLists(), user.getCurrentGame().getPlayer2Board().getHitedSquarsAsListOfLists(), true
        );
        try {
            objectOutputStream.writeObject(
                    new GameInfoSE(boardsInfo_se, Loader.loadUser(user.getCurrentGame().getPlayer1Id()).getUsername(),
                    Loader.loadUser(user.getCurrentGame().getPlayer2Id()).getUsername(), user.getCurrentGame().getPlayer1Score(),
                            user.getCurrentGame().getPlayer2Score(), user.getCurrentGame().checkTurn(user.getId()), true));
        } catch (IOException ignored) {}
    }

    private void finishCurrentGame(boolean winner){
        readyForGame = false;
        if (winner){
            user.addVictory();
            user.getGames().add(user.getCurrentGame());
            user.setCurrentGame(null);
            for (int spectatorId : getCurrentGameSpectatorsIds()){
                try {
                    mainController.getClientById(spectatorId).getObjectOutputStream().writeObject(new DeclareLiveGameFinishSE());
                } catch (IOException ignored) {}
            }
            currentGameSpectatorsIds.clear();
            try {
                objectOutputStream.writeObject(new SendGameResultSE(true));
            } catch (IOException ignored) {}
            try {
                Saver.saveUser(user);
            } catch (IOException ignored) {}
        }else{
            user.addDefeat();
            user.getGames().add(user.getCurrentGame());
            user.setCurrentGame(null);
            try {
                objectOutputStream.writeObject(new SendGameResultSE(false));
            } catch (IOException ignored) {}
            currentGameSpectatorsIds.clear();
            try {
                Saver.saveUser(user);
            } catch (IOException ignored) {}
        }
    }

    private void sendLiveGameInfo(String oneOfPlayersUsername){
        Game game = mainController.getGame(user.getId(), oneOfPlayersUsername);
        BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                game.getPlayer1Board().getFullSquarsAsListOfLists (), game.getPlayer2Board().getFullSquarsAsListOfLists (),
                game.getPlayer1Board().getHitedSquarsAsListOfLists(), game.getPlayer2Board().getHitedSquarsAsListOfLists(), false
        );
        try {
            objectOutputStream.writeObject(
                    new LiveGameInfoSE(boardsInfo_se, Loader.loadUser(game.getPlayer1Id()).getUsername(),
                            Loader.loadUser(game.getPlayer2Id()).getUsername(), game.getPlayer1Score(),
                            game.getPlayer2Score(), game.checkTurn(user.getId()),
                            game.getPlayer1Board().getNumberOfRemainingShips(),
                            game.getPlayer1Board().getNumberOfDestroyedShips(),
                            game.getPlayer2Board().getNumberOfRemainingShips(),
                            game.getPlayer2Board().getNumberOfDestroyedShips(),false));
        } catch (IOException ignored) {}
    }

    public void updateLiveStream(Game liveGame){
        BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                liveGame.getPlayer1Board().getFullSquarsAsListOfLists (), liveGame.getPlayer2Board().getFullSquarsAsListOfLists (),
                liveGame.getPlayer1Board().getHitedSquarsAsListOfLists(), liveGame.getPlayer2Board().getHitedSquarsAsListOfLists(), true
        );
        try {
            objectOutputStream.writeObject(
                    new LiveGameInfoSE(boardsInfo_se, Loader.loadUser(liveGame.getPlayer1Id()).getUsername(),
                            Loader.loadUser(liveGame.getPlayer2Id()).getUsername(), liveGame.getPlayer1Score(),
                            liveGame.getPlayer2Score(), liveGame.checkTurn(user.getId()),
                            liveGame.getPlayer1Board().getNumberOfRemainingShips(),
                            liveGame.getPlayer1Board().getNumberOfDestroyedShips(),
                            liveGame.getPlayer2Board().getNumberOfRemainingShips(),
                            liveGame.getPlayer2Board().getNumberOfDestroyedShips(),
                            true));
        } catch (IOException ignored) {}
    }

    public void sendRandomBoardInfo(boolean update){
        this.readyForGame = false;
        Random random = new Random();
        int rand = random.nextInt(5);
        this.currentBoardrRandomMapNumber = rand;
        Board board = new Board(rand);
        BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                board.getFullSquarsAsListOfLists (),
                null,
                board.getHitedSquarsAsListOfLists(),
                null, update
        );

        try {
            objectOutputStream.writeObject(boardsInfo_se);
        } catch (IOException e) {}
    }

    private void sendUserInfo(){
        try {
            objectOutputStream.writeObject(new SendUserInfoSE(
                    this.getUser().getUsername(),
                    this.getUser().getPassword(),
                    this.getUser().getNumberOfWins(),
                    this.getUser().getNumberOfLosts(),
                    this.getUser().getTotalScore(),
                    false));
        } catch (IOException e) {}
    }

    private void sendHighScoresTableInfo(){
        ArrayList<SendUserInfoSE> usersInfo = new ArrayList<>();

        for (User user : Loader.loadAllUsers()){
            usersInfo.add(new SendUserInfoSE(
                    user.getUsername(),
                    null,
                    user.getNumberOfWins(),
                    user.getNumberOfLosts(),
                    user.getTotalScore(),
                    mainController.checkClientConnection(user.getId())));
        }

        Collections.sort(usersInfo, new Comparator<SendUserInfoSE>() {
            @Override
            public int compare(SendUserInfoSE o1, SendUserInfoSE o2) {
                return o2.getTotalScore() - o1.getTotalScore();
            }
        });

        try {
            objectOutputStream.writeObject(new HighScoresInfoSE(usersInfo));
        } catch (IOException ignored) {}
    }

    private void close(){
        System.out.println("mainController.getConnectedClients() = " + mainController.getConnectedClients());
        mainController.getConnectedClients().remove(this);
        try {
            socket.close();
        } catch (IOException ignored) {}
        System.out.println("mainController.getConnectedClients() = " + mainController.getConnectedClients());
        mustDie = true;
    }

    public void declareOpponentDissconnect() throws IOException {
        objectOutputStream.writeObject(new DeclareOpponentDisconnectSE());
    }

    private boolean checkAuthToken(ClientEvent clientEvent){
        if (!clientEvent.getAuthToken().equals(authToken)){
            try {
                throw new Exception("Wrong authTo458ken received from a connection, connection disconneted because of security risks.");
            } catch (Exception ignored) {}
            close();
            return false;
        }

        return true;
    }

    public boolean isReadyForGame() {
        return readyForGame;
    }

    public User getUser() {
        return user;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public boolean checkReConnectivity(LogInEventCE logInEventCE) throws IOException {
        for (int i = 0; i < mainController.getConnectedClients().size(); i++) {
            if (mainController.getConnectedClients().get(i).getId() == Loader.loadUser(logInEventCE.getUsername()).getId()){
                this.user = mainController.getConnectedClients().get(i).getUser();
                this.id = mainController.getConnectedClients().get(i).getId();
                this.currentGameSpectatorsIds = mainController.getConnectedClients().get(i).getCurrentGameSpectatorsIds();
                this.currentOpponentId = mainController.getConnectedClients().get(i).getCurrentOpponentId();
                this.currentBoardrRandomMapNumber = mainController.getConnectedClients().get(i).getCurrentBoardrRandomMapNumber();
                this.readyForGame = mainController.getConnectedClients().get(i).readyForGame;
                mainController.getConnectedClients().get(i).setReConnected(true);
                mainController.getConnectedClients().remove(i);
                BoardsInfoSE boardsInfo_se = new BoardsInfoSE(
                        user.getCurrentGame().getPlayer1Board().getFullSquarsAsListOfLists (), user.getCurrentGame().getPlayer2Board().getFullSquarsAsListOfLists (),
                        user.getCurrentGame().getPlayer1Board().getHitedSquarsAsListOfLists(), user.getCurrentGame().getPlayer2Board().getHitedSquarsAsListOfLists(), true
                );
                try {
                    objectOutputStream.writeObject(
                            new UnfinishedGameInfoSE(boardsInfo_se, Loader.loadUser(user.getCurrentGame().getPlayer1Id()).getUsername(),
                                    Loader.loadUser(user.getCurrentGame().getPlayer2Id()).getUsername(), user.getCurrentGame().getPlayer1Score(),
                                    user.getCurrentGame().getPlayer2Score(), user.getCurrentGame().checkTurn(user.getId()), true));
                } catch (IOException ignored) {}
                return true;
            }
        }
        return false;
    }

    public void setReConnected(boolean reConnected) {
        this.reConnected = reConnected;
    }
}
