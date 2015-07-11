package Server.Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by Lenovo on 7/7/2015.
 */
public class Chat extends Transferable {

    public Chat(Socket socket, Processor processor) {
        super(socket, processor);
    }

    public String getString() {
        return string;
    }

    //TODO: insert user ID to chat string

    public void setString(String string) {
        this.string = string;
    }

    String string;

    @Override
    public void sender() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            Formatter formatter = new Formatter(outputStream);
            formatter.format(string);
            formatter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receiver() {
        try {
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                // TODO : print to appropriate place
                processor.getChatArray().add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

