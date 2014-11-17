package ca.nakednate.game.p2p;

import ca.nakednate.game.models.GameState;
import ca.nakednate.game.models.events.VehiclePositionEvent;
import ca.nakednate.game.models.vehicle.Vehicle;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static final String LOG_TAG = ClientHandler.class.getSimpleName();

    private Socket mClient;
    private MessageHandler messageHandler;

    private BufferedReader mBufferedReader;
    private BufferedWriter mBufferedWriter;
    private Peer mPeer;

    public ClientHandler(Socket client) {
        mClient = client;
        messageHandler = new MessageHandler(this);

        if (mClient == null) {
            Gdx.app.error(LOG_TAG, "null socket provided!");
            return;
        }

        try {
            mBufferedReader = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
            mBufferedWriter = new BufferedWriter(new OutputStreamWriter(mClient.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            teardown();
            ClientManager.removePeer(getPeer());
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
        } catch (Exception e) {
            e.printStackTrace();
            attemptReconnect();
        }
    }

    public void attemptReconnect() {
        teardown();
        ClientManager.removePeer(getPeer());
        ClientManager.getOrCreateClientHandler(getPeer());
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

    public void teardown() {
        try {
            mClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientHandler that = (ClientHandler) o;

        return getPeer().equals(that.getPeer());
    }

    @Override
    public int hashCode() {
        return getPeer().hashCode();
    }

    private long mLastVehicleEvent = 0;
    public void sendMyPosition() {
        final Vehicle myVehicle = GameState.getInstance().getMyVehicle();

        if(System.currentTimeMillis() - mLastVehicleEvent > 200) {
            mLastVehicleEvent = System.currentTimeMillis();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VehiclePositionEvent myVehiclePosition = new VehiclePositionEvent(null, myVehicle);
                    sendJson(VehiclePositionEvent.class, myVehiclePosition.toJSON());
                }
            }).start();
        }
    }
}
