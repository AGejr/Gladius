package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IPostEntityProcessingService;
import com.badlogic.gdx.math.Intersector;
import CommonWeapon.Weapon;


public class WeaponCollision implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity weapon: world.getEntities(Weapon.class)) {
            for (Entity hitEntity: world.getEntities()) {
                if (hitEntity.getPart(LifePart.class) != null && hitEntity.getPart(StatsPart.class) != null && !hitEntity.getID().equals(((Weapon) weapon).getOwner().getID()) && !((Weapon) weapon).isEntityHit(hitEntity)) {
                    LifePart hitEntityLifePart = hitEntity.getPart(LifePart.class);
                    StatsPart attackerStats = ((Weapon) weapon).getOwner().getPart(StatsPart.class);
                    StatsPart defenderStats = hitEntity.getPart(StatsPart.class);
                    // The if statement below checks if the invisible rectangles on the entities collide.
                    if (Intersector.overlapConvexPolygons(weapon.getPolygonBoundaries(), hitEntity.getPolygonBoundaries()) && !hitEntityLifePart.isDead()) {
                        if (weapon instanceof Weapon) {
                            if (defenderStats.getDefence() < ((Weapon) weapon).getDamage() + attackerStats.getAttack()) {
                                int totalDamage = ((Weapon) weapon).getDamage() + attackerStats.getAttack() - defenderStats.getDefence();
                                hitEntityLifePart.subtractLife(totalDamage);
                            }
                            AnimationPart hitEntityAnimationPart = hitEntity.getPart(AnimationPart.class);
                            if (hitEntityAnimationPart != null) {
                                hitEntityAnimationPart.setTakeDamage();
                            }
                            ((Weapon) weapon).addEntityHit(hitEntity);
                        }
                    }

                }
            }
        }
    }
}
