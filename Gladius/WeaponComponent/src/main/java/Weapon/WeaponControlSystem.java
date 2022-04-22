package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponUserService;
import CommonWeapon.Weapon;
import CommonWeapon.IWeaponService;
import Common.data.entityparts.AnimationPart;
import com.badlogic.gdx.math.Polygon;

public class WeaponControlSystem implements IEntityProcessingService, IWeaponService {
    private int counter = 0;
    private float rotationDegrees = 0.0f;
    // rotationDuration is used for the number of frames it should rotate. The game runs with 30 frames so it will swing for half a second if rotationDuration is 15.
    private final int rotationDuration = 15;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity weapon: world.getEntities(Weapon.class)) {
            if(weapon.getTexturePath() != null) {
                counter++;
                Entity attacker = ((Weapon) weapon).getOwner();
                AnimationPart attackerAnimationPart = attacker.getPart(AnimationPart.class);
                Polygon weaponBoundry = weapon.getPolygonBoundaries();
                if (attackerAnimationPart == null) {
                    continue;
                }
                if (!attackerAnimationPart.isLeft()) {
                    float xPos = attacker.getX();
                    float yPos = attacker.getY();
                    weapon.setX(xPos + ((Weapon) weapon).getPositionAdjustX());
                    weapon.setY(yPos + ((Weapon) weapon).getPositionAdjustY());

                    if (counter < rotationDuration) {
                        weapon.setAngle(rotationDegrees);
                        weaponBoundry.setRotation(rotationDegrees);
                        weapon.getBoundingRectangle();
                        rotationDegrees = weapon.getAngle() - ((Weapon) weapon).getAngleAdjustment();
                    }

                } else {
                    if (counter == 1) {
                        rotationDegrees = 0.0f;
                    }
                    float xPos = attacker.getX();
                    float yPos = attacker.getY();
                    weapon.setX(xPos + 9);
                    weapon.setY(yPos + ((Weapon) weapon).getPositionAdjustY() - 7);

                    if (counter < rotationDuration) {
                        weapon.setAngle(rotationDegrees);
                        weaponBoundry.setRotation(rotationDegrees);
                        weapon.getBoundingRectangle();
                        rotationDegrees = weapon.getAngle() + ((Weapon) weapon).getAngleAdjustment();
                    }
                }


                if (counter >= rotationDuration) {
                    ((Weapon) weapon).resetHitEntityList();
                    world.removeEntity(weapon);
                    rotationDegrees = 20.0f;
                    counter = 0;
                }
            }
        }
    }

    @Override
    public void attack(Entity attacker, GameData gameData, World world) {
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        IWeaponUserService weaponUser = (IWeaponUserService) attacker;
        Weapon weaponEntity = weaponUser.getWeapon();
        rotationDegrees = weaponEntity.getAngle();
        weaponEntity.setX(xPos + weaponEntity.getPositionAdjustX());
        weaponEntity.setY(yPos + weaponEntity.getPositionAdjustY());
        weaponEntity.setWeaponTexture();
        world.addEntity(weaponEntity);
    }


}
