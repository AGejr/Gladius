package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IPostEntityProcessingService;
import Event.EventRegistry;
import Event.GAME_EVENT;
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
                                boolean wasAlive = !hitEntityLifePart.isDead();
                                int totalDamage = ((Weapon) weapon).getDamage() + attackerStats.getAttack() - defenderStats.getDefence();
                                hitEntityLifePart.subtractLife(totalDamage);
                                if (wasAlive && hitEntityLifePart.isDead()){
                                    EventRegistry.addEvent(GAME_EVENT.ENTITY_KILLED);
                                }
                            }
                            ((Weapon) weapon).addEntityHit(hitEntity);
                        }
                    }
                }
            }
        }
    }
}
