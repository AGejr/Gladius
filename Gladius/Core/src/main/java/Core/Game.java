package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityProcessingService;
import Common.services.IGamePluginService;
import Common.services.IPostEntityProcessingService;
import Common.tools.FileLoader;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;

import java.io.File;
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
        cfg.height = 640;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 1.5f);
        cam.update();
        String[] files = {"Map/Map.tmx", "Map/Arena_Tileset.tsx", "Map/Arena_Tileset.png"};
        FileLoader.loadFiles(files, getClass());

        tiledMap = new TmxMapLoader().load("Map/Map.tmx");
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true); //Makes tiles transparent

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        sr = new ShapeRenderer();

        batch = new SpriteBatch();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(237/255f, 190/255f, 82/255f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        for (Entity entity :  world.getEntities()){

            if(entity.getTexture() == null){
                // if entity doesn't have texture, create and assign the texture
                File textureFile = new File(entity.getTexturePath());
                FileHandle fileHandle = new FileHandle(textureFile);
                Texture playerTexture = new Texture(fileHandle);
                entity.setTexture(playerTexture);
                // Set the region of the texture we want to draw
                entity.setRegion(0,0,32,32);

            }
            batch.draw(entity,entity.getX(),entity.getY());
            cam.position.y = entity.getY();
            cam.position.x = entity.getX();
        }
        batch.end();

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
