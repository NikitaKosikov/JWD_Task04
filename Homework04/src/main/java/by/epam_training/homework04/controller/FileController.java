package by.epam_training.homework04.controller;

import by.epam_training.homework04.entity.Text;
import by.epam_training.homework04.view.FileView;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileController {
    private static Socket client;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private final FileView view = new FileView();
    public static final Logger LOGGER = Logger.getLogger(FileController.class.getName());


    public void request(int taskNumber) {
        try {
            LogManager.getLogManager().readConfiguration(FileController.class.getResourceAsStream("/logging.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Could not setup logger configuration: ", exception);
        }
        try {
            client = new Socket("localhost", 4004);
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                OpenInputOutputStreams(client);
                out.writeInt(taskNumber);
                out.flush();
                Text text = null;
                while (text==null) {
                    Object obj = in.readObject();
                    if (obj instanceof Text) {
                        text = (Text) obj;
                        view.response("\nResult: " + text);
                    }else {
                        view.response(obj);
                        out.writeObject(reader.readLine());
                        out.flush();
                    }
                }
            }catch (IOException exception){
                LOGGER.log(Level.SEVERE, "I / O error", exception);
            }catch (ClassNotFoundException exception){
                LOGGER.log(Level.SEVERE, " Class of a serialized object cannot be found.", exception);
            }finally {
                try {
                    in.close();
                    out.close();
                } catch (IOException exception) {
                    LOGGER.log(Level.SEVERE, "Error closing streams input / output", exception);
                }
            }
        }catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "I/O error occurs when creating the socket.", exception);
        }finally {
            try {
                if (client!=null) {
                    client.close();
                }
            } catch (IOException exception) {
                LOGGER.log(Level.SEVERE, "Error closing client", exception);
            }
        }
    }

    private static void OpenInputOutputStreams(Socket client){
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "I / O error when opening an stream", exception);
        }
    }
}
