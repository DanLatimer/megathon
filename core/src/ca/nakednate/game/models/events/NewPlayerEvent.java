package ca.nakednate.game.models.events;


import ca.nakednate.game.models.PlayerInfo;

public class NewPlayerEvent extends BaseEvent {

    private PlayerInfo mPlayerInfo;

    public NewPlayerEvent(PlayerInfo playerInfo) {
        mPlayerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return mPlayerInfo;
    }
}
