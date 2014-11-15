package ca.nakednate.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private P2PServer mApp;
    private Socket mClient;
    private MessageHandler messageParser;

    public ClientHandler(Socket client, P2PServer app) {
        mApp = app;
        mClient = client;
        messageParser = new MessageHandler();
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
            messageParser.handleMessages(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
