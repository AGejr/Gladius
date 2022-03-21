package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

public class PlayerPlugin implements IGamePluginService {


    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData);
        world.addEntity(player);

    }

    private Entity createPlayer(GameData gamedata){
        File textureFile = new File("GladiatorSpriteSheet.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture playerTexture = new Texture(fileHandle);
        Entity player = new Player(playerTexture, 2);
        player.setX(gamedata.getDisplayWidth()/2);
        player.setY(gamedata.getDisplayHeight()/2);
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity entity : world.getEntities()) {
            world.removeEntity(entity);
        }
    }
}
