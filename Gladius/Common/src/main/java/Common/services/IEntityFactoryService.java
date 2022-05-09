package Common.services;

import Common.data.GameData;
import Common.data.World;

public interface IEntityFactoryService {
    void spawn(GameData gameData, World world, Integer amount);

    void stop(World world);
}
