package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.GameKeys;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityProcessingService;
import Common.services.IGamePluginService;
import Common.services.IPostEntityProcessingService;
import Common.tools.FileLoader;
import CommonPlayer.Player;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.Display.update;

public class Game implements ApplicationListener {

    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private final boolean DEBUG_MODE = true; //Set this to true to get hitbox lines

    // DEBUG
    private ShapeRenderer sr;

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
        cam.position.x = 800;
        cam.position.y = 140;
        cam.update();
        String[] files = {"Map/Map.tmx", "Map/Arena_Tileset.tsx", "Map/Arena_Tileset.png"};
        FileLoader.loadFiles(files, getClass());

        tiledMap = new TmxMapLoader().load("Map/Map.tmx");
        world.setTiledMap(tiledMap); //Saves tiledMap to the world
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true); //Makes tiles transparent

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        sr = new ShapeRenderer();

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
        batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);

        batch.begin();
        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity :  world.getEntities()){
            if(entity.getTexturePath() != null) {

                if(entity.getTexture() == null){
                    entity.initTexture();
                }

                if (entity.getClass() == Player.class) {
                    Vector3 position = cam.position;
                    position.x += (entity.getX() - position.x) * gameData.getLerp() * Gdx.graphics.getDeltaTime();
                    position.y += (entity.getY() - position.y) * gameData.getLerp() * Gdx.graphics.getDeltaTime();
                }
                // draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
                batch.draw(entity, entity.getX(), entity.getY(), 0, 0, entity.getTextureWidth(), entity.getTextureHeight(), 1, 1, entity.getAngle());
                entity.updatePolygonBoundariesPosition();
            }
        }

        batch.end();
        sr.end();
        for (Entity entity :  world.getEntities()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Line);
            if (DEBUG_MODE) {
                shapeRenderer.setColor(Color.BLUE);
            } else {
                shapeRenderer.setColor(Color.CLEAR);
            }
            shapeRenderer.polygon(entity.getPolygonBoundaries().getTransformedVertices());
            // Full texture size
            /*if (DEBUG_MODE) {
                shapeRenderer.setColor(Color.GREEN);
            } else {
                shapeRenderer.setColor(Color.CLEAR);
            }
            shapeRenderer.rect(entity.getX(), entity.getY(), entity.getTextureWidth(), entity.getTextureHeight());*/
            // Collision size
            if (DEBUG_MODE) {
                shapeRenderer.setColor(Color.RED);
            } else {
                shapeRenderer.setColor(Color.CLEAR);
            }
            if(entity.getPart(MovingPart.class) != null) {
                shapeRenderer.rect(entity.getX() + ((float) entity.getTextureWidth()/2) - (entity.getRadius()/2), entity.getY(), entity.getRadius(), entity.getRadius());
            }
            else {
                shapeRenderer.rect(entity.getX() + ((float) entity.getTextureWidth()/2) - (entity.getRadius()/2), entity.getY() + ((float) entity.getTextureHeight()/2) - (entity.getRadius()/2), entity.getRadius(), entity.getRadius());
            }
            // Explosion range
            if (DEBUG_MODE) {
                shapeRenderer.setColor(Color.GREEN
                );
            } else {
                shapeRenderer.setColor(Color.CLEAR);
            }
            if(entity.getPart(StatsPart.class) != null) {
                StatsPart entityStats = entity.getPart(StatsPart.class);
                Polygon polygonBoundaries = new Polygon(new float[]{entity.getX() + ((float) entity.getTextureWidth()/2) - (float) entityStats.getExplosionRadius()/2, entity.getY() + ((float) entity.getTextureHeight()/2) - (float) entityStats.getExplosionRadius()/2, entity.getX() + ((float) entity.getTextureWidth()/2) - (float) entityStats.getExplosionRadius()/2, entity.getY() + ((float) entity.getTextureHeight()/2) - (float) entityStats.getExplosionRadius()/2 + entityStats.getExplosionRadius(), entity.getX() + ((float) entity.getTextureWidth()/2) - (float) entityStats.getExplosionRadius()/2 + entityStats.getExplosionRadius(), entity.getY() + ((float) entity.getTextureHeight()/2) - (float) entityStats.getExplosionRadius()/2 + entityStats.getExplosionRadius(), entity.getX() + ((float) entity.getTextureWidth()/2) - (float) entityStats.getExplosionRadius()/2 + entityStats.getExplosionRadius(), entity.getY() + ((float) entity.getTextureHeight()/2) - (float) entityStats.getExplosionRadius()/2});
                shapeRenderer.polygon(polygonBoundaries.getTransformedVertices());
            }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
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
        plugin.start(gameData,world);


    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }
}
