package ca.nakednate.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private P2PServer mApp;
    private Integer mPort = null;
    private ServerSocket mServerSocket;
    private SocketListener mSocketOpenListener = null;

    public SocketHandler(P2PServer app) {
        mApp = app;
    }

    public Integer getPort() {
        return mPort;
    }

    public void initializeServerSocket() {
        // Initialize a server socket on the next available mPort.
        try {
            mServerSocket = new ServerSocket(0);

            if (mSocketOpenListener != null) {
                mSocketOpenListener.socketOpenSuccess(mServerSocket.getLocalPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (mSocketOpenListener != null) {
                mSocketOpenListener.socketOpenFail();
            }
        }

        mPort = mServerSocket.getLocalPort();
    }

    @Override
    public void run() {
        initializeServerSocket();
        while (true) {
            try {
                Socket socket = mServerSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket, mApp);
                // TODO: hold onto runnables and cleanup when dead

                new Thread(clientHandler).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setSocketOpenListener(SocketListener socketOpenListener) {
        mSocketOpenListener = socketOpenListener;
    }
}
