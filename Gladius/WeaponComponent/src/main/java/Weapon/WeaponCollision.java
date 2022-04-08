package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IPostEntityProcessingService;
import CommonWeapon.IWeaponUserService;
import com.badlogic.gdx.math.Intersector;
import CommonWeapon.Weapon;


public class WeaponCollision implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        System.out.println("SA");
        for (Entity weapon: world.getEntities(Sword.class)) {
            System.out.println("SA2");
            for (Entity hitEntity: world.getEntities()) {
                System.out.println("SA3");
                if (hitEntity.getPart(LifePart.class) != null && hitEntity.getPart(StatsPart.class) != null) {
                    System.out.println("SA4");
                    LifePart hitEntityLifePart = hitEntity.getPart(LifePart.class);
                    StatsPart defenderStats = hitEntity.getPart(StatsPart.class);
                    if(weapon.getTexture() != null) {
                        System.out.println("SA5");
                        if (Intersector.overlapConvexPolygons(weapon.getPolygonBoundaries(), hitEntity.getPolygonBoundaries())) {
                            System.out.println("SA6");
                            if (weapon instanceof Sword) {
                                int totalDamage = ((Weapon) weapon).getDamage();
                                hitEntityLifePart.subtractLife(totalDamage);
                                System.out.println("Enemy life " + hitEntityLifePart.getLife());
                            }
                        }
                    }
                }
            }

            // System.out.println("Box3 " + weapon.getRegionX() + " G " + weapon.getRegionY());
        }
        for (Entity entity: world.getEntities(Entity.class)) {
            if (entity.getPart(LifePart.class) != null) {

                // TODO Get Entities stats
                // TODO Get weapon stats
                // TODO Minus the damage from lifepart
            }
        }
    }
}
