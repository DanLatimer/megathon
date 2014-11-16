package ca.nakednate.game.p2p.listeners;

import ca.nakednate.game.models.events.OpponentInitialChoicesEvent;

public interface GameStateListener {

    public void onOpponentInitialChoicesEvent(OpponentInitialChoicesEvent opponentInitialChoicesEvent);

}
