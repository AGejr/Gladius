package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()){
            System.out.println(entity);
        }
    }
}
