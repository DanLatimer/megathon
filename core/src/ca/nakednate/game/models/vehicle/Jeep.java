package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.weapon.MachineGun;
import ca.nakednate.game.models.weapon.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Jeep extends Vehicle {

    private static final int HP = 100;
    private static final int INITIAL_DEPLOYABLE_AMMO = 3;
    private static final int SPEED = 500;

    private static final String FRIENDLY = "skin/sprites/vehicle_jeep_blue.png";
    private static final String ENEMY = "skin/sprites/vehicle_jeep_red.png";

    public Jeep(boolean friendly) {
        setTexture(new Texture(Gdx.files.internal(friendly ? FRIENDLY : ENEMY)));

        setHitPoint(HP);
        setWeapon(new MachineGun());
        addDeployableWeapon(INITIAL_DEPLOYABLE_AMMO);
        setSpeed(SPEED);
        super.init();
    }

    public void addDeployableWeapon(int quantity) {
        for (int i = 0; i < quantity; i++) {
            addDeployableWeapon(new Turret());
        }
    }

}
