package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AnimationPart implements EntityPart{

    public enum ANIMATION_STATES{
        IDLE,
        RUNNING_RIGHT,
        RUNNING_LEFT,
        ATTACK,
        DEATH,
    }

    private ANIMATION_STATES currentState = ANIMATION_STATES.IDLE;
    private HashMap<ANIMATION_STATES, Animation> animations = new HashMap<>();
    private float animationTime = 0;

    @Override
    public void process(GameData gameData, Entity entity) {

        if(!this.getCurrentAnimation().isAnimationFinished(animationTime)){
            animationTime += Gdx.graphics.getDeltaTime();
        }else{
            animationTime = 0;
        }
    }

    public ANIMATION_STATES getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ANIMATION_STATES currentState) {
        this.currentState = currentState;
    }

    public void addAnimation(ANIMATION_STATES state, Animation animation){
        this.animations.put(state,animation);
    }

    public Animation getCurrentAnimation(){
        return animations.get(currentState);
    }

    public TextureRegion getCurrentKeyFrame(){
        return getCurrentAnimation().getKeyFrame(animationTime,true);
    }


}
