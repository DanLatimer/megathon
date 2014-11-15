package ca.nakednate.game.models.events;


import ca.nakednate.game.models.PlayerInfo;
import ca.nakednate.game.p2p.ClientHandler;

public class NewPlayerEvent extends BaseEvent {

    private PlayerInfo mPlayerInfo;

    public NewPlayerEvent(ClientHandler messageOriginator, PlayerInfo playerInfo) {
        super(messageOriginator);

        mPlayerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return mPlayerInfo;
    }
}
