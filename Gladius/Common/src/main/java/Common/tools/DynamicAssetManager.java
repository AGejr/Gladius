package Common.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class DynamicAssetManager {

    public final AssetManager assetManager = new AssetManager();

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> sounds = new ArrayList<>();

    public DynamicAssetManager() {
    }

    public void loadAssets(){
        images.add("Minotaur.png");
        images.add("Goblin_king.png");

        FileLoader.loadFiles(images.toArray(new String[images.size()]), getClass());

        sounds.add("Sounds/minotaur_death.mp3");
        sounds.add("Sounds/minotaur_attack.mp3");
        sounds.add("Sounds/goblin_death.mp3");
        sounds.add("Sounds/goblin_attack.mp3");
        sounds.add("Sounds/walk.mp3");
        sounds.add("Sounds/walk_water.mp3");
        sounds.add("Sounds/attack.mp3");
        sounds.add("Sounds/damage.mp3");
        sounds.add("Sounds/death.mp3");
        sounds.add("Sounds/Explosion.mp3");
        sounds.add("Sounds/buy.mp3");
        sounds.add("Sounds/interact.mp3");
        sounds.add("Sounds/wave_cleared.mp3");
        sounds.add("Sounds/arena_entered.mp3");
        sounds.add("Sounds/exit_arena.mp3");
        sounds.add("Sounds/theme.ogg");

        FileLoader.loadFiles(sounds.toArray(new String[sounds.size()]), getClass());

        loadImages();
        loadSounds();

        assetManager.finishLoading();
    }

    private void loadImages(){
        for (String image: images){
            assetManager.load(image, Texture.class);
        }
    }

    private void loadSounds(){
        for (String sound: sounds){
            assetManager.load(sound, Sound.class);
        }
    }

    public Texture getTexture(String filename){
        return assetManager.get(filename, Texture.class);
    }

    public Sound getSound(String filename) {
        return assetManager.get(filename, Sound.class);
    }

    public void update(){
        assetManager.update();
    }
}
