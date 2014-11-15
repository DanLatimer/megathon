package ca.nakednate.game.models.weapon;

public class Cannon extends Weapon {

    private static final float FIRE_RATE = 30.0f;
    private static final int AMMO = 4;
    private static final int PAYLOAD = 50;

    public Cannon() {
        super(FIRE_RATE, PAYLOAD, AMMO);
    }
}
