package ca.nakednate.game.models;

/**
 * Information about a player
 */
public class PlayerInfo extends BaseModel {
    private String mDisplayName;

    public PlayerInfo(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }
}
