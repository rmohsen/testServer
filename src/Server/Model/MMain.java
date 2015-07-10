package Server.Model;

import Server.Controller.Processor;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Lenovo on 7/9/2015.
 */
public class MMain extends Chat {
    Socket socket;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    String command;
    public MMain(Socket socket, Processor processor) {
        super(socket, processor);
        this.socket = socket;
        this.processor = processor;
    }


    @Override
    public void sender() {
        super.sender();
    }

    @Override
    public void receiver() {
        try {
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                command = scanner.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
