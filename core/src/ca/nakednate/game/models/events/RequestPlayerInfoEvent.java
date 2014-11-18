package ca.nakednate.game.models.events;

import ca.nakednate.game.p2p.ClientHandler;

public class RequestPlayerInfoEvent extends BaseEvent {

    public RequestPlayerInfoEvent(ClientHandler messageOriginator) {
        super(messageOriginator);
    }
}
