package ca.nakednate.game.models.vehicle;

import ca.nakednate.game.models.weapon.MachineGun;
import ca.nakednate.game.models.weapon.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Jeep extends Vehicle {

    private static final int HP = 100;
    private static final int INITIAL_DEPLOYABLE_AMMO = 3;
    private static final int SPEED = 500;

    public Jeep() {
        setTexture(new Texture(Gdx.files.internal("skin/sprites/vehicle_jeep_blue.png")));
        super.init();
        setHitPoint(HP);
        setWeapon(new MachineGun());
        addDeployableWeapon(INITIAL_DEPLOYABLE_AMMO);
        setSpeed(SPEED);
    }

    public void addDeployableWeapon(int quantity) {
        for (int i = 0; i < quantity; i++) {
            addDeployableWeapon(new Turret());
        }
    }


}
