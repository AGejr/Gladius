package Common.data;

import Common.tools.FileLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SoundData {

    public final Map<SOUND, String> soundFileMap = new HashMap<>();
    public final Map<SOUND, Sound> soundMap = new HashMap<>();

    public enum SOUND{
        WALK,
        WALK_WATER,
        ATTACK,
        DAMAGE,
        DEATH,
        EXPLOSION,
        BUY,
        INTERACT,
        WAVE_CLEARED,
        ARENA_ENTERED,
        ARENA_EXIT,

        //todo
        GOBLIN_ATTACK,
        GOBLIN_SOUND,
        MINOTAUR_SOUND,
        MINOTAUR_ATTACK
    }

    public SoundData(){

        //TODO record sounds
        soundFileMap.put(SOUND.WALK, "walk.mp3");
        soundFileMap.put(SOUND.WALK_WATER, "walk_water.mp3");
        soundFileMap.put(SOUND.ATTACK, "attack.mp3");
        soundFileMap.put(SOUND.DAMAGE, "damage.mp3");
        soundFileMap.put(SOUND.DEATH, "death.mp3");
        soundFileMap.put(SOUND.EXPLOSION, "Explosion.mp3");
        soundFileMap.put(SOUND.BUY, "buy.mp3");
        soundFileMap.put(SOUND.INTERACT, "interact.mp3");
        soundFileMap.put(SOUND.WAVE_CLEARED, "wave_cleared.mp3");
        soundFileMap.put(SOUND.ARENA_ENTERED, "arena_entered.mp3");
        soundFileMap.put(SOUND.ARENA_EXIT, "exit_arena.mp3");

        // todo refactor
        soundFileMap.put(SOUND.MINOTAUR_SOUND, "minotaur_death.mp3");
        soundFileMap.put(SOUND.MINOTAUR_ATTACK, "minotaur_attack.mp3");
        soundFileMap.put(SOUND.GOBLIN_SOUND, "goblin_death.mp3");
        soundFileMap.put(SOUND.GOBLIN_ATTACK, "goblin_attack.mp3");

        for(SOUND sound : soundFileMap.keySet()){
           soundFileMap.replace(sound,"Sounds/"+soundFileMap.get(sound));
        }


    }

    public void initSound(){

        for (SOUND sound : this.soundFileMap.keySet()){
            String path = this.soundFileMap.get(sound);
            this.soundMap.put(sound, Gdx.audio.newSound(Gdx.files.internal(path)));
        }
    }

    public Map<SOUND, String> getSoundFileMap() {
        return this.soundFileMap;
    }

    public Map<SOUND, Sound> getSoundMap(){ return soundMap; }

    public Sound getSound(SOUND sound){
        return this.soundMap.get(sound);
    }

    public void playSound(SOUND key){
        playSound(key, 1f);
    }

    public void playSound(SOUND key, float volume){
        Sound sound = soundMap.get(key);
        sound.play(volume);
    }

}
