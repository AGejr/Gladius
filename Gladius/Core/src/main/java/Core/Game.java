package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.services.IEntityProcessingService;
import Common.services.IGamePluginService;
import Common.services.IPostEntityProcessingService;
import Common.tools.FileLoader;
import CommonPlayer.Player;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Music theme;

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
        gameData.setMapWidth(1600);
        gameData.setMapHeight(1280);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 1.5f);
        cam.position.x = 800;
        cam.position.y = 140;
        cam.update();
        gameData.setCam(cam);

        String[] mapFiles = {"Map/Map.tmx", "Map/Arena_Tileset.tsx", "Map/Arena_Tileset.png"};
        FileLoader.loadFiles(mapFiles, getClass());
        tiledMap = new TmxMapLoader().load(mapFiles[0]);
        world.setTiledMap(tiledMap); //Saves tiledMap to the world
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true); //Makes tiles transparent

        world.setCsvMap(FileLoader.fetchData(mapFiles[0]));

        gameData.setSoundData(new SoundData());

        // Game sounds loader
        Collection<String> soundFileMap = gameData.getSoundData().getSoundFileMap().values();

        for(String soundFile : soundFileMap){
            FileLoader.loadFile(soundFile,getClass());
        }

        gameData.getSoundData().initSound();

        // Background music
        FileLoader.loadFile("Sounds/theme.ogg" ,getClass());
        Music theme = Gdx.audio.newMusic(Gdx.files.internal("Sounds/theme.ogg"));
        theme.setVolume(0.5f);
        theme.setLooping(true);
        theme.play();


        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

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
        for (Entity entity :  world.getEntities()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Line);
            if (gameData.isDebugMode()) {
                shapeRenderer.setColor(Color.BLUE);
            } else {
                shapeRenderer.setColor(Color.CLEAR);
            }
            shapeRenderer.polygon(entity.getPolygonBoundaries().getTransformedVertices());
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
        plugin.start(gameData,world);


    }

    public void removeGamePluginService(IGamePluginService plugin) {
        plugin.stop(gameData, world);
    }
}
