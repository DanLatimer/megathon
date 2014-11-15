package ca.nakednate.game.p2p;

import java.net.InetAddress;

public class Peer {

    private InetAddress mHost;
    private int mPort;

    public Peer(InetAddress host, int port) {
        mHost = host;
        mPort = port;
    }

    public InetAddress getHost() {
        return mHost;
    }

    public int getPort() {
        return mPort;
    }
}
