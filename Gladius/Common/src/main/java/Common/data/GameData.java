package Common.data;

import Common.tools.DynamicAssetManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private int mapWidth;
    private int mapHeight;
    private float lerp = 3.0f;
    private final GameKeys keys = new GameKeys();
    private boolean gateEnabled = true;
    private int wave = 1;
    private Camera cam;
    private boolean debugMode = false;
    private DynamicAssetManager dynamicAssetManager = new DynamicAssetManager();
    private Stage stage;

    public int getWave() {
        return wave;
    }

    public void incrementWave() {
        this.wave++;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public float getLerp() {
        return lerp;
    }

    public void setLerp(float lerp) {
        this.lerp = lerp;
    }

    public boolean isGateEnabled() {
        return gateEnabled;
    }

    public void setGateEnabled(boolean gateEnabled) {
        this.gateEnabled = gateEnabled;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public DynamicAssetManager getAssetManager() {
        return dynamicAssetManager;
    }

    public Stage getStage() {
        if (stage == null) {
            stage = new Stage();
        }
        return stage;
    }
}
