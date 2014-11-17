package ca.nakednate.game.p2p;

import com.badlogic.gdx.Gdx;

import java.net.InetAddress;
import java.net.Socket;

/**
 * DummyClientHandler
 */
public class DummyClientHandler extends ClientHandler {

    private static final String LOG_TAG = DummyClientHandler.class.getSimpleName();

    public DummyClientHandler() {
        super(null);
    }

    @Override
    public void sendJson(Class clazz, String json) {

    }

    @Override
    public void attemptReconnect() {

    }

    @Override
    public void sendMyPosition() {

    }

    @Override
    public void teardown() {

    }

    @Override
    public Peer getPeer() {
        try {
            return new Peer(InetAddress.getLocalHost(), 0);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Could not get InetAddress.");
            return null;
        }
    }
}
