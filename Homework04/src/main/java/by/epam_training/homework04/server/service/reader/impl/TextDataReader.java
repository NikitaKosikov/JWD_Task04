package by.epam_training.homework04.server.service.reader.impl;

import by.epam_training.homework04.server.service.reader.FileDataReader;
import by.epam_training.homework04.server.Server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TextDataReader implements FileDataReader {

    private String path;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public TextDataReader(String path) {
        this.path = path;
    }

    static {
        try {
            LogManager.getLogManager().readConfiguration(Server.class.getResourceAsStream("/logging.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Could not setup logger configuration: ", exception);
        }
    }

    @Override
    public String read(){
        StringBuilder text = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(path)){
            int character;
            boolean isMoreThanOneSpaceInRow = false;
            while ((character = fileInputStream.read())!=-1){
                if ((char) character =='\t'){
                    character = ' ';
                    text.append((char)character);
                    isMoreThanOneSpaceInRow = true;
                } else if ((char) character == ' '){
                    if(!isMoreThanOneSpaceInRow){
                        text.append((char)character);
                        isMoreThanOneSpaceInRow = true;
                    }
                }else {
                    text.append((char)character);
                    isMoreThanOneSpaceInRow = false;
                }
            }
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Wrong file path", exception);
        }
        return text.toString();
    }
}
