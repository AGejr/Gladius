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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

        sr = new ShapeRenderer();

        SpriteBatch batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(194/255f, 178/255f, 128/255f, 1); //Black = 0,0,0,1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }




        for (Entity entity :  world.getEntities()){
            batch.begin();
            entity.draw(batch);
            batch.end();
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
