package Common.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class DynamicAssetManager {

    public final AssetManager assetManager = new AssetManager();

    public DynamicAssetManager() {
    }

    public void loadAssets(){
        FileLoader.loadFile("Minotaur.png", getClass());
        FileLoader.loadFile("GladiatorSpriteSheet.png", getClass());

        loadImages();
    }

    private void loadImages(){
        assetManager.load("Minotaur.png", Texture.class);
    }

    public Texture getTexture(String filename){
        return assetManager.get(filename, Texture.class);
    }

    public void update(){
        assetManager.update();
    }
}
