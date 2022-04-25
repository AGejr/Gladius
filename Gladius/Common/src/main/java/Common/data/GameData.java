package Common.data;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private float lerp = 3.0f;
    private final GameKeys keys = new GameKeys();
    private boolean gateEnabled = true;
    private int wave = 1;

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
}
