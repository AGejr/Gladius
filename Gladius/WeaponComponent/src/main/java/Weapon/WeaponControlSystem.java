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
import com.badlogic.gdx.math.Polygon;

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

        for (Entity entity: world.getEntities(Weapon.class)) {
            if(entity.getTexturePath() != null) {
                counter++;
                Entity attacker = ((Weapon) entity).getOwner();
                // for (Entity attacker: world.getEntities(entity.getOwner().class)) {
                AnimationPart attackerAnimationPart = attacker.getPart(AnimationPart.class);
                Polygon weaponBoundry = entity.getPolygonBoundaries();
                if (attackerAnimationPart == null) {
                    continue;
                }
                if (!attackerAnimationPart.isLeft()) {
                    float xPos = attacker.getX();
                    float yPos = attacker.getY();
                    entity.setX(xPos + adjustX);
                    entity.setY(yPos + adjustY);

                    if (counter < 15) {
                        entity.setAngle(rotationDegrees);
                        weaponBoundry.setRotation(rotationDegrees);
                        entity.getBoundingRectangle();
                        rotationDegrees = entity.getAngle() - 9.0f;
                    }

                } else {
                    if (counter == 1) {
                        rotationDegrees = 0.0f;
                    }
                    float xPos = attacker.getX();
                    float yPos = attacker.getY();
                    entity.setX(xPos + 9);
                    entity.setY(yPos + adjustY - 7);

                    if (counter < 15) {
                        entity.setAngle(rotationDegrees);
                        weaponBoundry.setRotation(rotationDegrees);
                        entity.getBoundingRectangle();
                        rotationDegrees = entity.getAngle() + 9.0f;
                    }
                }


                if (counter >= 15) {
                    ((Weapon) entity).resetHitEntityList();
                    world.removeEntity(entity);
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
        weaponEntity.setAngle(20.0f);
        rotationDegrees = weaponEntity.getAngle();
        weaponEntity.setX(xPos + adjustX);
        weaponEntity.setY(yPos + adjustY);
        weaponEntity.setWeaponTexture();
        world.addEntity(weaponEntity);
    }


}
