package ca.nakednate.game.models.weapon;

public class MachineGun extends Weapon {

    private static final float FIRE_RATE = 120.0f;
    private static final int AMMO = 14;
    private static final int PAYLOAD = 10;

    public MachineGun() {
        super(FIRE_RATE, PAYLOAD, AMMO);
    }
}
