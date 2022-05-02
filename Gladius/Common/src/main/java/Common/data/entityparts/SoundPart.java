package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundPart implements EntityPart {

    private GameData gameData;

    private SoundData soundData;

    private boolean isInit = false;
    private boolean isMoving = false;

    private Map<SoundData.SOUND, String> localSoundFileMap = new HashMap<>();
    private Map<SoundData.SOUND, Sound> localSoundMap = new HashMap<>();

    private Sound movmentSound = null;

    public SoundPart(GameData gameData){
        localSoundFileMap.put(SoundData.SOUND.WALK, "");
        localSoundFileMap.put(SoundData.SOUND.WALK_WATER, "");
        localSoundFileMap.put(SoundData.SOUND.ATTACK, "");
        localSoundFileMap.put(SoundData.SOUND.DAMAGE, "");
        localSoundFileMap.put(SoundData.SOUND.DEATH, "");
        localSoundFileMap.put(SoundData.SOUND.EXPLOSION, "");
        localSoundFileMap.put(SoundData.SOUND.BUY, "");
        localSoundFileMap.put(SoundData.SOUND.INTERACT, "");
        localSoundFileMap.put(SoundData.SOUND.WAVE_CLEARED, "");
        localSoundFileMap.put(SoundData.SOUND.ARENA_ENTERED, "");

    }

    public void initSounds(){
        this.soundData = gameData.getSoundData();
        for (SoundData.SOUND sound : localSoundFileMap.keySet()){
            String path = localSoundFileMap.get(sound);
            if(path.equals("")){
                path = soundData.getSoundFileMap().get(sound);
            }

            this.localSoundMap.put(sound, Gdx.audio.newSound(Gdx.files.internal(path)));
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {

        if(!isInit) {
            isInit = true;
            this.gameData = gameData;
            initSounds();
        }

        MovingPart movingPart = entity.getPart(MovingPart.class);
        boolean hasMovingPart = movingPart != null;


        if(hasMovingPart) {
            if ((movingPart.isUp() ^ movingPart.isDown() || movingPart.isLeft() ^ movingPart.isRight()) && !isMoving) {

                if (movingPart.isSlow()) {
                    movmentSound = this.getSound(SoundData.SOUND.WALK_WATER);
                } else {
                    movmentSound = this.getSound(SoundData.SOUND.WALK);
                }
                movmentSound.loop(1.0f);
                isMoving = true;
            }
            if (!(movingPart.isUp() || movingPart.isDown() || movingPart.isLeft() || movingPart.isRight()) &&isMoving){
                movmentSound.stop();
                isMoving = false;
            }
        }

    }

    public void putAudio(SoundData.SOUND key, String path){
        localSoundFileMap.put(key, path);
    }

    public Sound getSound(SoundData.SOUND key){

        Sound sound = null;
        if(localSoundMap.containsKey(key)){
            return localSoundMap.get(key);
        } else if (soundData.getSoundMap().containsKey(key)) {
            return soundData.soundMap.get(key);
        }

        return sound;
    }

    public void playAudio(SoundData.SOUND key){
            Sound sound = this.getSound(key);
            sound.play(1f);
    }

}
