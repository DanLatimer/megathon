package ca.nakednate.p2p;

import android.app.Activity;
import android.widget.Toast;
import ca.nakednate.game.p2p.ClientHandler;
import ca.nakednate.game.p2p.ClientManager;

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

    public void teardown() {
        try {
            mServerSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initializeServerSocket();
        while (!mServerSocket.isClosed()) {
            try {
                Socket socket = mServerSocket.accept();
                socket.setSoTimeout(0);

                ClientHandler clientHandler = new ClientHandler(socket);

                final Activity activity = mApp.getActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Client Found!", Toast.LENGTH_LONG);
                    }
                });

                new Thread(clientHandler).start();
                ClientManager.addClientHandler(clientHandler);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void setSocketOpenListener(SocketListener socketOpenListener) {
        mSocketOpenListener = socketOpenListener;
    }
}
