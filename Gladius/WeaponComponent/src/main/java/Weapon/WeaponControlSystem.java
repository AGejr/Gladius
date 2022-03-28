package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
// import Common.data.entityparts.MovingPart;
// import Common.data.entityparts.PositionPart;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponUser;
//import CommonWeapon.Weapon;
import CommonWeapon.WeaponSPI;

import java.util.List;

public class WeaponControlSystem implements IEntityProcessingService, WeaponSPI {
    @Override
    public void process(GameData gameData, World world) {
        /*
            TODO Write the code for the swing with a weapon
         */

    }

    @Override
    public void attack(Entity attacker, GameData gameData, World world) {
        System.out.println("ATTACK");
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        float deltaTime = gameData.getDelta();
        //Weapon weaponEntity = ((IWeaponUser) attacker).getWeapon();
        Weapon weaponEntity = Sword.getSword();
        weaponEntity.setX(xPos + 100);
        weaponEntity.setY(yPos + 30);
        weaponEntity.setWeaponTexture();

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
