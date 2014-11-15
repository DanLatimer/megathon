package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;

public class DummyEvent extends BaseEvent {

    public DummyEvent(ClientHandler messageOriginator) {
        super(messageOriginator);
    }
}
