package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.PositionPart;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponService;
import CommonWeapon.IWeaponUser;

import java.util.List;

public class WeaponControlSystem implements IEntityProcessingService, IWeaponService {
    @Override
    public void process(GameData gameData, World world) {
        /*
            TODO Write the code for the swing with a weapon
         */

    }

    @Override
    public void attack(Entity attacker, GameData gameData, World world) {
        MovingPart attackerMovingPart = attacker.getPart(MovingPart.class);
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        float deltaTime = gameData.getDelta();
        // TODO get attacker 'radians'
        // int radians = attacker.getRadians()

        /*
        Entity weapon = attacker.getWeapon();
        List<Entity> entitiesInRange = getEntitiesInRange(attacker, world);
        for (Entity entity: entitiesInRange) {
            Lifepart entityLifePart = entity.getPart(LifePart.class);
            float damageGiven = (attacker.getStats() * weapon.getDamage()) - attacker.getArmor();
            float newHealth = entityLifePart.getLife() - damageGiven;
            entityLifePart.setHealth(newHealth);
         */
    }

    //TODO change to check collision between weapon and enemy, if hit then remove HP
    private List<Entity> getEntitiesInRange(IWeaponUser attacker, World world) {
        Entity weapon = attacker.getWeapon();
        Entity attackerEntity = (Entity) attacker;
        // float radians = attackerEntity.getRadians();
        // The range for weapon is the radius
        float range = weapon.getRadius();
        // float attackArea = (float) ((radians / 2) * Math.pow(range, 2)); //Circular sector area
        return null;
    }


}
