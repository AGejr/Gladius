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

    @Override
    public void process(GameData gameData, World world) {
        for (Entity weapon: world.getEntities(Weapon.class)) {
            ((Weapon) weapon).incrementCounter();
            Entity attacker = ((Weapon) weapon).getOwner();
            float xPos = attacker.getX();
            float yPos = attacker.getY();
            AnimationPart attackerAnimationPart = attacker.getPart(AnimationPart.class);
            Polygon weaponBoundry = weapon.getPolygonBoundaries();
            /*The animation only works if there is an animation part.*/
            if (attackerAnimationPart == null) {
                continue;
            }
            /*The animationpart is used to figure out which way the entity is pointing, so the weapon spawns the right place*/
            if (!attackerAnimationPart.isLeft()) {
                if (((Weapon) weapon).getCounter() == 1) {
                    ((Weapon) weapon).setRotationDegrees(0.0f);
                }

                weapon.setX(xPos + ((Weapon) weapon).getPositionAdjustRightX());
                weapon.setY(yPos + ((Weapon) weapon).getPositionAdjustY());

                /*While the counter is less than the duration of the rotation, the rotation continues*/
                if (((Weapon) weapon).getCounter() < ((Weapon) weapon).getRotationDuration()) {
                    weapon.setAngle(((Weapon) weapon).getRotationDegrees());
                    weaponBoundry.setRotation(((Weapon) weapon).getRotationDegrees());
                    weapon.getBoundingRectangle();
                    ((Weapon) weapon).setRotationDegrees(weapon.getAngle() - ((Weapon) weapon).getAngleAdjustment());
                }

            } else {
                if (((Weapon) weapon).getCounter() == 1) {
                    ((Weapon) weapon).setRotationDegrees(0.0f);
                }
                weapon.setX(xPos + ((Weapon) weapon).getPositionAdjustLeftX());
                weapon.setY(yPos + ((Weapon) weapon).getPositionAdjustY() - 7);

                /*While the counter is less than the duration of the rotation, the rotation continues*/
                if (((Weapon) weapon).getCounter() < ((Weapon) weapon).getRotationDuration()) {
                    weapon.setAngle(((Weapon) weapon).getRotationDegrees());
                    weaponBoundry.setRotation(((Weapon) weapon).getRotationDegrees());
                    // getBoundingRectangle updates the rectangle's position
                    weapon.getBoundingRectangle();
                    ((Weapon) weapon).setRotationDegrees(weapon.getAngle() + ((Weapon) weapon).getAngleAdjustment());
                }
            }

            /*If the counter is above the duration of the rotation, 'stop' rotaion of weapon, remove it from world, and reset the list containing the hit entities
            * The weapon still exists, it's just not in the world.*/
            if (((Weapon) weapon).getCounter() >= ((Weapon) weapon).getRotationDuration()) {
                ((Weapon) weapon).resetHitEntityList();
                ((Weapon) weapon).resetCounter();
                world.removeEntity(weapon);
            }
        }
    }

    @Override
    public void attack(Entity attacker, GameData gameData, World world) {
        /*This method starts the attack animation of the weapon. It adds the weapon to the world, and adds its texture back and sets the rotation to start at the correct angle*/
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        IWeaponUserService weaponUser = (IWeaponUserService) attacker;
        Weapon weaponEntity = weaponUser.getWeapon();
        weaponEntity.setRotationDegrees(weaponEntity.getAngle());
        weaponEntity.setX(xPos + weaponEntity.getPositionAdjustRightX());
        weaponEntity.setY(yPos + weaponEntity.getPositionAdjustY());
        weaponEntity.setWeaponTexture();
        world.addEntity(weaponEntity);
    }


}
