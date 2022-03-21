package Common.services;


import Common.data.GameData;
import Common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
