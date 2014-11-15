package ca.nakednate.game.p2p.listeners;

import ca.nakednate.p2p.Peer;

import java.util.List;

public interface PeerDiscoveryListener {

    public void onPeersDiscovered(List<Peer> peers);

}
