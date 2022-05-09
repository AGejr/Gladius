package Common.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

public class DynamicAssetManager {

    public final AssetManager assetManager;

    private TextureLoader textureLoader;
    private SoundLoader soundLoader;
    private PixmapLoader pixmapLoader;
    private FreeTypeFontGeneratorLoader freeTypeFontGeneratorLoader;

    public DynamicAssetManager() {
        assetManager = new AssetManager();
        loadImages();
    }

    private void loadImages(){
        FileLoader.loadFile("Minotaur.png", getClass());
        assetManager.load("Minotaur.png", Texture.class);
    }

    public Texture getTexture(String filename){
        return assetManager.get(filename, Texture.class);
    }

    public void update(){
        assetManager.update();
    }
}
