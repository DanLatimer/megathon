package ca.nakednate.game.models.events;

import ca.nakednate.game.models.BaseModel;
import ca.nakednate.game.p2p.ClientHandler;

public class BaseEvent extends BaseModel {

    private ClientHandler mMessageOriginator;

    public BaseEvent(ClientHandler messageOriginator) {
        mMessageOriginator = messageOriginator;
    }

    public ClientHandler getMessageOriginator() {
        return mMessageOriginator;
    }

    public void setMessageOriginator(ClientHandler messageOriginator) {
        mMessageOriginator = messageOriginator;
    }
}
