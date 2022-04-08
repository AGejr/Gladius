package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.services.IPostEntityProcessingService;
import CommonWeapon.Weapon;


public class WeaponCollision implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity weapon: world.getEntities(Sword.class)) {
            if(weapon.getTexture() != null) {

            }
            // System.out.println("Box3 " + weapon.getRegionX() + " G " + weapon.getRegionY());
        }
        for (Entity entity: world.getEntities(Entity.class)) {
            if (entity.getPart(LifePart.class) != null) {
                // TODO Get player stats
                // TODO Get weapon stats
                // TODO Minus the damage from lifepart
            }
        }
    }
}
