package Common.services;

import Common.data.GameData;
import Common.data.World;

public interface IEventProcessingService {
    void process(GameData gameData, World world);
}
