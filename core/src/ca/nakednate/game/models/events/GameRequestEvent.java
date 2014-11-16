package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;

public class GameRequestEvent extends BaseEvent {

    private boolean accepted;

    public GameRequestEvent(ClientHandler messageOriginator, boolean accepted) {
        super(messageOriginator);
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
