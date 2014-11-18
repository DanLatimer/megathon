package ca.nakednate.game.p2p;

import java.net.InetAddress;

public class Peer {

    private InetAddress mHost;
    private int mPort;
    private String mDisplayName;

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

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    @Override
    public String toString() {
        if (mDisplayName != null) {
            return mDisplayName;
        }

        return mHost.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peer peer = (Peer) o;

        if (mHost != null ? !mHost.equals(peer.mHost) : peer.mHost != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mHost != null ? mHost.hashCode() : 0;
    }
}
