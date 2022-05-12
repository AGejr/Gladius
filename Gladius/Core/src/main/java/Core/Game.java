package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.services.*;
import Common.services.IEntityProcessingService;
import Common.services.IGamePluginService;
import Common.services.IPostEntityProcessingService;
import Common.tools.FileLoader;
import Common.ui.UI;
import CommonEnemy.Enemy;
import CommonPlayer.Player;
import Event.EventRegistry;
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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private static List<IEventProcessingService> eventProcessingServiceList = new CopyOnWriteArrayList<>();

    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public Game(){
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Gladius";
        int displayWidth = 800;
        int displayHeight = 640;
        cfg.width = displayWidth;
        cfg.height = displayHeight;
        gameData.setDisplayWidth(displayWidth);
        gameData.setDisplayHeight(displayHeight);
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        gameData.setMapWidth(1600);
        gameData.setMapHeight(1280);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 1.5f);
        cam.position.x = 800;
        cam.position.y = 140;
        cam.update();
        gameData.setCam(cam);

        String[] files = {"Map/Map.tmx", "Map/Arena_Tileset.tsx", "Map/Arena_Tileset.png"};
        FileLoader.loadFiles(files, getClass());

        FileLoader.loadFile("mc.otf", getClass());

        tiledMap = new TmxMapLoader().load(files[0]);
        world.setTiledMap(tiledMap); //Saves tiledMap to the world
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true); //Makes tiles transparent

        world.setCsvMap(FileLoader.fetchData(files[0]));

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {

        gameData.getAssetManager().update();

        //Gdx.gl.glClearColor(194/255f, 178/255f, 128/255f, 1); //Black = 0,0,0,1

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);

        world.putBehind(Player.class);
        batch.begin();
        for (Entity entity :  world.getEntities()) {
            if(entity.getTexturePath() != null) {
                if (entity.getClass() == Enemy.class){
                    entity.initTextureFormAssetManager(gameData);
                }
                else if(entity.getTexture() == null){
                    entity.initTexture();
                }

                if (entity.getClass() == Player.class) {
                    Vector3 position = cam.position;
                    position.x += (entity.getX() - position.x) * gameData.getLerp() * Gdx.graphics.getDeltaTime();
                    position.y += (entity.getY() - position.y) * gameData.getLerp() * Gdx.graphics.getDeltaTime();
                }
                // draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
                batch.draw(entity, entity.getX(), entity.getY(), 0, 0, entity.getTextureWidth(), entity.getTextureHeight(), entity.getScaling(), entity.getScaling(), entity.getAngle());
                entity.updatePolygonBoundariesPosition();
            } else {
                entity.updatePolygonBoundariesPosition();
            }
        }
        drawShop(gameData, batch);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeType.Line);
        for (Entity entity :  world.getEntities()) {
            if (gameData.isDebugMode()) {
                shapeRenderer.setColor(Color.BLUE);
            } else {
                shapeRenderer.setColor(Color.CLEAR);
            }
            shapeRenderer.polygon(entity.getPolygonBoundaries().getTransformedVertices());
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // To begin a new batch takes a lot of memory. Because of that it needs to happen in Game and not in process in LifePart to prevent to many batches getting created
        shapeRenderer.begin(ShapeType.Filled);
        for (Entity entity: world.getEntities()) {
            LifePart lifePart = entity.getPart(LifePart.class);
            if (lifePart != null && lifePart.getHealthColor() != null) {
                lifePart.drawHealthBar(shapeRenderer, entity);
            }
        }
        gameData.getStage().draw();
        UI.draw(gameData);
        shapeRenderer.end();

        update();
        gameData.getKeys().update();
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

        // Handle events
        for (IEventProcessingService eventProcessingService: eventProcessingServiceList) {
            eventProcessingService.process(gameData, world);
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
        plugin.start(gameData,world);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        plugin.stop(gameData, world);
    }

    public void addEventProcessingService(IEventProcessingService iEventProcessingService) {
        EventRegistry.removeAllEvents();
        this.eventProcessingServiceList.add(iEventProcessingService);
    }

    public void removeEventProcessingService(IEventProcessingService iEventProcessingService) {
        this.eventProcessingServiceList.remove(iEventProcessingService);
    }

    public void addEntityFactoryService(IEntityFactoryService iEntityFactoryService) {
        world.addEntityFactory(iEntityFactoryService);
    }

    public void removeEntityFactoryService(IEntityFactoryService iEntityFactoryService) {
        world.removeEntityFactory(iEntityFactoryService);
    }

    /**
     * Draws the coins on top of the tilemap. This is placed here because it needs the main batch.
     */
    private void drawShop(GameData gameData, SpriteBatch batch) {
        int tileSize = 32;
        File textureFile = new File("ShopItems.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        TextureRegion region = new TextureRegion(texture);

        region.setRegion(tileSize * 4, tileSize * 8, tileSize * 2, tileSize * 2);
        batch.draw(region, 0, gameData.getMapHeight() / 2f - tileSize * 3, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 8, tileSize * 2, tileSize * 2);
        batch.draw(region, 0, gameData.getMapHeight() / 2f - tileSize * 3, tileSize * 4, tileSize * 4);
    }
}
