package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class AnimationPart implements EntityPart{

    public enum ANIMATION_STATES{
        IDLE_RIGHT,
        IDLE_LEFT,
        RUNNING_RIGHT,
        RUNNING_LEFT,
        ATTACK_RIGHT,
        ATTACK_LEFT,
        DEATH_RIGHT,
        DEATH_LEFT
    }

    private ANIMATION_STATES currentState = ANIMATION_STATES.IDLE_RIGHT;
    private HashMap<ANIMATION_STATES, Animation> animations = new HashMap<>();
    private float animationTime = 0;
    private boolean isLeft = true;

    @Override
    public void process(GameData gameData, Entity entity) {
        if(!this.getCurrentAnimation().isAnimationFinished(animationTime)){
            animationTime += Gdx.graphics.getDeltaTime();
        }else{
            animationTime = 0;
        }
        if (entity.getPart(MovingPart.class) != null) {
            processMovementAnimation(entity);
        }
    }

    /**
     * @param entity
     *
     * This method uses logical statements based on keyboard input to call setAnimationDirection.
     * See JavaDoc at setAnimationDirection for more context.
     */
    private void processMovementAnimation(Entity entity){
        MovingPart movingPart = entity.getPart(MovingPart.class);
        AnimationPart animationPart = this;

        boolean left = movingPart.isLeft();
        boolean right = movingPart.isRight();
        boolean up = movingPart.isUp();
        boolean down = movingPart.isDown();

        // If either left or right keys are pressed, use the running animation
        if (left ^ right){
            setAnimationDirection(animationPart, left, right, AnimationPart.ANIMATION_STATES.RUNNING_LEFT, AnimationPart.ANIMATION_STATES.RUNNING_RIGHT);
        }

        // If either up or down keys are pressed, use the running animation
        if (up ^ down) {
            setAnimationDirection(animationPart, animationPart.isLeft(), !animationPart.isLeft(), AnimationPart.ANIMATION_STATES.RUNNING_LEFT, AnimationPart.ANIMATION_STATES.RUNNING_RIGHT);
        }

        // If only left and right keys are pressed, use the idle animation
        if ((left && right) && (!up && !down)) {
            setAnimationDirection(animationPart, animationPart.isLeft(), !animationPart.isLeft(), AnimationPart.ANIMATION_STATES.IDLE_LEFT, AnimationPart.ANIMATION_STATES.IDLE_RIGHT);
        }

        // If only up and down keys are pressed, use the idle animation
        if ((up && down) && (!left && !right)) {
            setAnimationDirection(animationPart, animationPart.isLeft(), !animationPart.isLeft(), AnimationPart.ANIMATION_STATES.IDLE_LEFT, AnimationPart.ANIMATION_STATES.IDLE_RIGHT);
        }

        // If no keys are pressed, use the idle animation
        if (!left && !right && !up && !down){
            setAnimationDirection(animationPart, animationPart.isLeft(), !animationPart.isLeft(), AnimationPart.ANIMATION_STATES.IDLE_LEFT, AnimationPart.ANIMATION_STATES.IDLE_RIGHT);
        }
    }

    /**
     * @param animationPart
     * @param leftCondition
     * @param rightCondition
     * @param leftAnimationState
     * @param rightAnimationState
     *
     * This method sets the animation to either idle or running with a left or right orientation.
     * Left and right animation state is needed because both the running and idle animation has an orientation.
     */
    private void setAnimationDirection(AnimationPart animationPart, boolean leftCondition, boolean rightCondition, AnimationPart.ANIMATION_STATES leftAnimationState, AnimationPart.ANIMATION_STATES rightAnimationState){
        if (leftCondition) {
            animationPart.setCurrentState(leftAnimationState);
            animationPart.setLeft(true);
        }
        if (rightCondition) {
            animationPart.setCurrentState(rightAnimationState);
            animationPart.setLeft(false);
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

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }
}
