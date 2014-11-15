package ca.nakednate.game.models.weapon;

public class Mine extends DeployableWeapon {

    private static final float FIRE_RATE = 1.0f;
    private static final int AMMO = 1;
    private static final int PAYLOAD = 50;
    private static final int PROXIMITY = 1;
    private static final int HP = 1;
    private static final int TIMEOUT = 30;

    public Mine() {
        super(FIRE_RATE, PAYLOAD, AMMO, PROXIMITY, HP, TIMEOUT);
    }
}
