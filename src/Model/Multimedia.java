package Model;

import Controller.Processor;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Lenovo on 7/7/2015.
 */
public class Multimedia extends Transferable {


    public Multimedia(Socket socket, Processor processor) {
        super(socket,processor);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    String destination = null ;

    @Override
    public void receiver() {

        final int FILE_SIZE = 6022386;
        final int SOCKET_PORT = 13267;      // you may change this
        final String SERVER = "127.0.0.1";  // localhost
        final String FILE_TO_RECEIVED = destination;

        int bytesRead;
        int current = 0;

        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            System.out.println("Connecting...");
            // receive file
            byte[] ReceivedByteArray = new byte[FILE_SIZE];

            InputStream inputStream = socket.getInputStream();

            fileOutputStream = new FileOutputStream(FILE_TO_RECEIVED);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            bytesRead = inputStream.read(ReceivedByteArray, 0, ReceivedByteArray.length);
            current = bytesRead;

            do {
                bytesRead =
                        inputStream.read(ReceivedByteArray, current, (ReceivedByteArray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bufferedOutputStream.write(ReceivedByteArray, 0, current);
            bufferedOutputStream.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + current + " bytes read)");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sender(){

        final int SOCKET_PORT = 13267;  // you may change this
        final String FILE_TO_SEND = destination ;  // you may change this

        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;

        try {
            File myFile = new File(FILE_TO_SEND);
            byte[] SentByteArray = new byte[(int) myFile.length()];
            fileInputStream = new FileInputStream(myFile);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(SentByteArray, 0, SentByteArray.length);
            outputStream = socket.getOutputStream();
            outputStream.write(SentByteArray, 0, SentByteArray.length);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedInputStream != null) bufferedInputStream.close();
                if (outputStream != null) outputStream.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
