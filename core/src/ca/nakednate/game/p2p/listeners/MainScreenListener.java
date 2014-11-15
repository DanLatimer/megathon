package ca.nakednate.game.p2p.listeners;

import ca.nakednate.game.models.GameInfo;
import ca.nakednate.game.models.PlayerInfo;

/**
 * A listener that reports P2P message relevant to the Main Screen
 */
public interface MainScreenListener {

    public void onGameInfoRecieved(GameInfo gameInfo);

    public void onPlayerInfoRecieved(PlayerInfo playerInfo);

}
