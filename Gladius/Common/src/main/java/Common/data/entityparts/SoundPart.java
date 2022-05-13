package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundPart implements EntityPart {

    private GameData gameData;

    private SoundData soundData;

    private boolean isInit = false;
    private boolean isMoving = false;
    private boolean hasChangedTiles = false;

    private boolean isDead = false;

    private boolean playMovementSound = false;

    private Map<SoundData.SOUND, String> localSoundFileMap = new HashMap<>();
    private Map<SoundData.SOUND, Sound> localSoundMap = new HashMap<>();

    private Sound movementSound = null;
    private SoundData.SOUND prevSound = null;

    public SoundPart(){
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

    public void initSounds(GameData gameData){
        
        this.soundData = gameData.getSoundData();
        for (SoundData.SOUND sound : localSoundFileMap.keySet()){
            String path = localSoundFileMap.get(sound);

            // Check to see if an audio is local to the entity. if not the value is ""
            if(path.equals("")){
                path = soundData.getSoundFileMap().get(sound);
            }

            this.localSoundMap.put(sound, gameData.getAssetManager().getSound(path));
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {

        if(!isInit) {
            isInit = true;
            this.gameData = gameData;
            initSounds(gameData);
        }

        MovingPart movingPart = entity.getPart(MovingPart.class);
        LifePart lifePart = entity.getPart(LifePart.class);
        boolean hasMovingPart = movingPart != null;
        boolean hasLifePart = lifePart != null;


        if(hasMovingPart && playMovementSound) {
            //if player is moving
            if ((movingPart.isUp() ^ movingPart.isDown() || movingPart.isLeft() ^ movingPart.isRight())) {

                //if the entity is slowed
                if (movingPart.isSlow()) {
                    // if the previous sound is different from the sound needed to be played now
                    if(prevSound != SoundData.SOUND.WALK_WATER && movementSound != null){
                        //stop the playing loop
                        movementSound.stop();
                        hasChangedTiles = true;
                    }
                    movementSound = this.getSound(SoundData.SOUND.WALK_WATER);
                    prevSound = SoundData.SOUND.WALK_WATER;
                } else {

                    if(prevSound != SoundData.SOUND.WALK && movementSound != null){
                        movementSound.stop();
                        hasChangedTiles = true;
                    }
                    movementSound = this.getSound(SoundData.SOUND.WALK);
                    prevSound = SoundData.SOUND.WALK;
                }
                // if the flag for not moving or changed tiles, is true, start the loop
                if( !isMoving || hasChangedTiles) {
                    movementSound.loop(soundData.getVolumeModifier());
                    isMoving = true;
                    hasChangedTiles = false;
                }
            }
            if (!(movingPart.isUp() || movingPart.isDown() || movingPart.isLeft() || movingPart.isRight()) &&isMoving){
                movementSound.stop();
                isMoving = false;

            }
        }

        if(hasLifePart){
            if(lifePart.isDead() && !isDead){
                this.playAudio(SoundData.SOUND.DEATH);
                isDead = true;
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
        playAudio(key, 1f);
    }

    public void playAudio(SoundData.SOUND key, float volume){
        Sound sound = localSoundMap.get(key);
        sound.play(volume * soundData.getVolumeModifier());
    }

    public void disposeSounds(){
        for(SoundData.SOUND key : localSoundFileMap.keySet()){
            if(!localSoundFileMap.get(key).equals("")){
                localSoundMap.get(key).dispose();
            }

        }
    }

    public void setPlayMovementSound(boolean playMovementSound) {
        this.playMovementSound = playMovementSound;
    }
}
