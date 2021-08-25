package saversandloaders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {
    public static void saveUser(User user) throws IOException {
        File folder = new File("Database/Users/"+user.getId()+".json");
        if(!folder.exists()){folder.createNewFile();}

        ObjectMapper objectMapper = new ObjectMapper();
        FileWriter fileWriter = new FileWriter(folder);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.writeValue(fileWriter, user);
        saveUsername(user);
    }

    private static void saveUsername(User user) throws IOException {
        File folder = new File("Database/Usernames/"+user.getUsername()+".json");
        if(!folder.exists()){folder.createNewFile();}

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        FileWriter fileWriter = new FileWriter(folder);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(fileWriter, user.getId());
    }
}
