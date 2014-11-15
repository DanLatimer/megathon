package ca.nakednate.game.models.weapon;

public class Turret extends DeployableWeapon {

    private static final float FIRE_RATE = 60.0f;
    private static final int AMMO = 10;
    private static final int PAYLOAD = 20;
    private static final int PROXIMITY = 10;
    private static final int HP = 1;
    private static final int TIMEOUT = 30;

    public Turret() {
        super(FIRE_RATE, PAYLOAD, AMMO, PROXIMITY, HP, TIMEOUT);
    }
}
