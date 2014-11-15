package ca.nakednate.game.p2p;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket mClient;
    private MessageHandler messageHandler;

    private BufferedReader mBufferedReader;
    private BufferedWriter mBufferedWriter;
    private Peer mPeer;

    public ClientHandler(Socket client) {
        mClient = client;
        messageHandler = new MessageHandler(this);

        try {
            mBufferedReader = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
            mBufferedWriter = new BufferedWriter(new OutputStreamWriter(mClient.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        messageHandler.handleMessages(mBufferedReader);
    }

    public void sendJson(Class clazz, String json) {
        try {
            mBufferedWriter.write(clazz.getCanonicalName());
            mBufferedWriter.newLine();

            mBufferedWriter.write(json);
            mBufferedWriter.newLine();
            mBufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a peer object from the client's info
     *
     * @return
     */
    public Peer getPeer() {
        if (mPeer == null) {
            mPeer = new Peer(mClient.getInetAddress(), mClient.getPort());
        }
        return mPeer;
    }

    @Override
    public String toString() {
        return mPeer.toString();
    }
}
