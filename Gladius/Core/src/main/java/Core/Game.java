package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityProcessingService;
import Common.services.IGamePluginService;
import Common.services.IPostEntityProcessingService;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;


    public Game(){
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Gladius";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        cam.update();
        String[] files = {"Map/Map.tmx", "Map/Arena_Tileset.tsx", "Map/Arena_Tileset.png"};

        for (String file : files) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)){
                File newFile = new File(file);
                if (inputStream != null) {
                    FileUtils.copyInputStreamToFile(inputStream, newFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tiledMap = new TmxMapLoader().load("Map/Map.tmx");
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true); //Makes tiles transparent

        sr = new ShapeRenderer();

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        //Gdx.gl.glClearColor(194/255f, 178/255f, 128/255f, 1); //Black = 0,0,0,1
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        /*
        File textureFile = new File("GladiatorSpriteSheet.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture playerTexture = new Texture(fileHandle);

        batch.begin();
        batch.draw(playerTexture,10,10);
        batch.end();

         */

        for (Entity entity :  world.getEntities()){
            batch.begin();
            entity.draw(batch);
            batch.end();
        }

        update();
    }


    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }
}
