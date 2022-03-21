package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PlayerPlugin implements IGamePluginService {


    @Override
    public void start(GameData gameData, World world) {
        System.out.println("test");
        Entity player = createPlayer(gameData);
        world.addEntity(player);

    }

    private Entity createPlayer(GameData gamedata){

        String file = "GladiatorSpriteSheet.png";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)){
            File newFile = new File(file);
            if (inputStream != null) {
                FileUtils.copyInputStreamToFile(inputStream, newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        File textureFile = new File("GladiatorSpriteSheet.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture playerTexture = new Texture(fileHandle);
        Entity player = new Player(playerTexture, 2);
        player.setX(Gdx.graphics.getWidth() / 2);
        player.setY(Gdx.graphics.getHeight() / 2);
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity entity : world.getEntities()) {
            world.removeEntity(entity);
        }
    }
}
