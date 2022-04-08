package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponUserService;
import CommonWeapon.Weapon;
import CommonWeapon.IWeaponService;
import CommonPlayer.Player;
import Common.data.entityparts.AnimationPart;

import java.util.List;

public class WeaponControlSystem implements IEntityProcessingService, IWeaponService {
    private int counter = 0;
    private float rotationDegrees = 0.0f;
    private final float adjustX = 20.0f;
    private final float adjustY = 10.0f;

    @Override
    public void process(GameData gameData, World world) {
        /*
            TODO Make weapon follow player
         */

        for (Entity entity: world.getEntities(Sword.class)) {
            if(entity.getTexturePath() != null) {
                counter++;
                for (Entity attacker: world.getEntities(Player.class)) {
                    AnimationPart attackerAnimationPart = attacker.getPart(AnimationPart.class);
                    if(!attackerAnimationPart.isLeft()) {
                        float xPos = attacker.getX();
                        float yPos = attacker.getY();
                        entity.setX(xPos + adjustX);
                        entity.setY(yPos + adjustY);

                        if (counter < 15) {
                            entity.setAngle(rotationDegrees);
                            rotationDegrees = entity.getAngle() - 9.0f;
                        }

                    } else {
                        if (counter == 1) {
                            rotationDegrees = 60.0f;
                        }
                        float xPos = attacker.getX();
                        float yPos = attacker.getY();
                        entity.setX(xPos + 9);
                        entity.setY(yPos + adjustY);

                        if (counter < 15) {
                            entity.setAngle(rotationDegrees);
                            rotationDegrees = entity.getAngle() + 9.0f;
                        }
                    }


                    if (counter >= 15) {
                        world.removeEntity(entity);
                        rotationDegrees = 20.0f;
                        counter = 0;
                    }

                }
            }
        }
    }

    @Override
    public void attack(Entity attacker, GameData gameData, World world) {
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        Weapon weaponEntity = Sword.getSword();
        weaponEntity.setAngle(20.0f);
        rotationDegrees = weaponEntity.getAngle();
        weaponEntity.setX(xPos + adjustX);
        weaponEntity.setY(yPos + adjustY);
        weaponEntity.setWeaponTexture();
        world.addEntity(weaponEntity);
    }

    //TODO change to check collision between weapon and enemy, if hit then remove HP
    private List<Entity> getEntitiesInRange(IWeaponUserService attacker, World world) {
        Entity weapon = attacker.getWeapon();
        Entity attackerEntity = (Entity) attacker;
        // float radians = attackerEntity.getRadians();
        // The range for weapon is the radius
        float range = weapon.getRadius();
        // float attackArea = (float) ((radians / 2) * Math.pow(range, 2)); //Circular sector area
        return null;
    }


}
