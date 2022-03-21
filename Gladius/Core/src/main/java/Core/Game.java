package Core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import javafx.scene.effect.ImageInput;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game implements ApplicationListener {
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;

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
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        //TODO: load files properly
        //tiledMap = new TmxMapLoader().load("C:/Users/janik/Map/Map.tmx");
        String[] files = {"/Map/Arena_Tileset.tsx", "Map/Map.tmx"};
        String[] newFileNames = {"Arena_Tileset.tsx", "Map.tmx"};
        List<InputStream> streams = new ArrayList<>();
        for (String file: files) {
            System.out.println(file);
            streams.add(getClass().getClassLoader().getResourceAsStream(file));
        }
        List<Scanner> scanners = new ArrayList<>();
        for (InputStream stream : streams) {
            System.out.println(stream);
            scanners.add(new Scanner(stream));
        }
        File mapTmx = null;
        for (int i = 0; i < scanners.size(); i++) {
            File newFile = new File(newFileNames[i]);
            try {
                FileWriter fileWriter = new FileWriter(newFile);
                while (scanners.get(i).hasNext()) {
                    fileWriter.append(scanners.get(i).next());
                }
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i == 0) {
                mapTmx = newFile;
            }
        }
        BufferedImage BImage = null;
        try {
            InputStream is = new BufferedInputStream(getClass().getResourceAsStream("Map/Arena_Tileset.png"));
            BImage = ImageIO.read(is);
            ImageIO.write(BImage, "png", new File("Arena_Tileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tiledMap = new TmxMapLoader().load(mapTmx.getPath());
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true); //Makes tiles transparent

        sr = new ShapeRenderer();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        //Gdx.gl.glClearColor(194/255f, 178/255f, 128/255f, 1); //Black = 0,0,0,1
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
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

}
