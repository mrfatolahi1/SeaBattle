package controller;

import models.Game;
import models.LogInResults;
import models.SignUpResults;
import models.User;
import saversandloaders.Loader;
import saversandloaders.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class UserController {

    public static SignUpResults signUp(String username, String password){
        ArrayList<String> alreadySignedUpUsersUsernames = new ArrayList<>();
        for (User user : Loader.loadAllUsers()){
            alreadySignedUpUsersUsernames.add(user.getUsername());
        }

        if (alreadySignedUpUsersUsernames.contains(username)){
            return SignUpResults.USERNAME_IS_TAKEN;
        }
        User user = new User(username, password);
        try {
            Saver.saveUser(user);
        } catch (IOException ignored) {}
        return SignUpResults.SUCCESSFUL;
    }

    public static LogInResults logIn(String username, String password){
        try {
            try {
                User user = Loader.loadUser(username);
                if (!user.getPassword().equals(password)){
                    return LogInResults.WRONG_PASSWORD;
                }
                return LogInResults.SUCCESSFUL;
            }catch (FileNotFoundException e){
                return LogInResults.USERNAME_DONT_EXISTS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void startANewGame(User user, Game game){
        user.setCurrentGame(game);
        try {
            Saver.saveUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
