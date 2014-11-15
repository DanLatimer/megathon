package ca.nakednate.game.models;

/**
 * Information about a player
 */
public class PlayerInfo extends BaseModel {
    private String mDisplayName;

    public PlayerInfo(String displayName) {
        mDisplayName = displayName;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }
}
