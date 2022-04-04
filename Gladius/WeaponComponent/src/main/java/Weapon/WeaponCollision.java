package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.services.IPostEntityProcessingService;


public class WeaponCollision implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity: world.getEntities(Entity.class)) {
            if (entity.getPart(LifePart.class) != null) {

            }
        }
    }
}
