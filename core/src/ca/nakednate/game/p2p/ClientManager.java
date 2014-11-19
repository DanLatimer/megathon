package ca.nakednate.game.p2p;

import ca.nakednate.game.p2p.listeners.DiscoveryServiceListener;
import ca.nakednate.game.p2p.listeners.MainScreenListener;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClientManager {
    private static Map<Peer, ClientHandler> mClientHandlerList = new HashMap<Peer, ClientHandler>();
    private static MainScreenListener mMainScreenListener;
    private static DiscoveryServiceListener mDiscoveryServiceListener;

    public static void addClientHandler(ClientHandler clientHandler) {
        mClientHandlerList.put(clientHandler.getPeer(), clientHandler);
    }

    public static ClientHandler getOrCreateClientHandler(Peer peer) {

        ClientHandler clientHandler = mClientHandlerList.get(peer);
        if(clientHandler != null) {
            return clientHandler;
        }

        Socket clientSocket;
        try {
            clientSocket = new Socket(peer.getHost().getHostAddress(), peer.getPort());
            clientSocket.setSoTimeout(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        clientHandler = new ClientHandler(clientSocket);
        new Thread(clientHandler).start();

        addClientHandler(clientHandler);

        if (mMainScreenListener != null) {
            mMainScreenListener.onNewClientAdded();
        }

        return clientHandler;
    }

    public static Map<Peer, ClientHandler> getClientHandlerMap() {
        return mClientHandlerList;
    }

    public static synchronized Collection<ClientHandler> getClientHandlers() {
        return mClientHandlerList.values();
    }

    public static void setMainScreenListener(MainScreenListener mainScreenListener) {
        mMainScreenListener = mainScreenListener;
    }

    public static void setDiscoveryServiceListener(DiscoveryServiceListener discoveryServiceListener) {
        mDiscoveryServiceListener = discoveryServiceListener;
    }

    public static void removePeer(Peer peer) {
        mClientHandlerList.remove(peer);
    }

}
