package ca.nakednate.game.p2p.listeners;

import ca.nakednate.game.models.events.GameRequestEvent;
import ca.nakednate.game.models.events.NewPlayerEvent;

/**
 * A listener that reports P2P message relevant to the Main Screen
 */
public interface MainScreenListener {

    public void onNewPlayerRecieved(NewPlayerEvent playerInfo);

    public void onNewClientAdded();

    public void onGameRequestEvent(GameRequestEvent gameRequestEvent);

}
