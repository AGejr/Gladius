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
        for (Entity weapon: world.getEntities(Weapon.class)) {
            for (Entity hitEntity: world.getEntities()) {
                if (hitEntity.getPart(LifePart.class) != null && hitEntity.getPart(StatsPart.class) != null && !hitEntity.getID().equals(((Weapon) weapon).getOwner().getID()) && !((Weapon) weapon).isEntityHitted(hitEntity)) {
                    LifePart hitEntityLifePart = hitEntity.getPart(LifePart.class);
                    // StatsPart attackerStats
                    StatsPart defenderStats = hitEntity.getPart(StatsPart.class);
                    if(weapon.getTexture() != null) {
                        if (Intersector.overlapConvexPolygons(weapon.getPolygonBoundaries(), hitEntity.getPolygonBoundaries())) {
                            if (weapon instanceof Weapon) {
                                // int totalDamage = ((Weapon) weapon).getDamage() - defenderStats.getDefence() + attackerStats.getAttack();
                                int totalDamage = ((Weapon) weapon).getDamage() - defenderStats.getDefence();
                                hitEntityLifePart.subtractLife(totalDamage);
                                ((Weapon) weapon).addEntityHitted(hitEntity);
                                System.out.println("Enemy hit");
                            }
                        }
                    }
                }
            }

            // System.out.println("Box3 " + weapon.getRegionX() + " G " + weapon.getRegionY());
        }
    }
}
