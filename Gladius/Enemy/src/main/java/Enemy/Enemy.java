package Enemy;

import Common.data.Entity;
import Common.data.entityparts.AnimationPart;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Entity {

    public Enemy(String texturePath, int radius) {
        super(texturePath, radius, 96, 96, 0, 0.5f, 0.5f);
    }


    @Override
    public void initTexture(){
        super.initTexture();
        this.setRegion(0,0,96, 96);
        initAnimation();
    }

    private void initAnimation(){
        if(this.getPart(AnimationPart.class) != null){
            AnimationPart animationPart = this.getPart(AnimationPart.class);

            // IDLE animation

            Array<TextureRegion> idleRightTextures = new Array<>();
            Array<TextureRegion> idleLeftTextures = new Array<>();

            for (int i = 0; i < 5; i++) {
                // IDLE right
                idleRightTextures.add(new TextureRegion(this.getTexture(),96*i,0,96,96));

                // IDLE left
                TextureRegion idleLeftTexture = new TextureRegion(this.getTexture(),96*i,0,96,96);
                idleLeftTexture.flip(true, false);
                idleLeftTextures.add(idleLeftTexture);
            }

            // IDLE right animation
            Animation idleRightAnimation = new Animation(0.16f,idleRightTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_RIGHT, idleRightAnimation);

            // IDLE left animation
            Animation leftIdleAnimation = new Animation(0.16f,idleLeftTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_LEFT, leftIdleAnimation);

            // Running animation

            Array<TextureRegion> rightMoveTextures = new Array<>();
            Array<TextureRegion> leftMoveTextures = new Array<>();

            for (int i = 0; i < 7; i++) {
                // Running right
                rightMoveTextures.add(new TextureRegion(this.getTexture(),96*i,96,96,96));

                // Running left
                TextureRegion leftTexture = new TextureRegion(this.getTexture(),96*i,96,96,96);
                leftTexture.flip(true,false);
                leftMoveTextures.add(leftTexture);
            }

            // RUNNING right animation
            Animation rightMoveAnimation = new Animation(0.175f,rightMoveTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.RUNNING_RIGHT, rightMoveAnimation);

            // RUNNING left animation
            Animation leftMoveAnimation = new Animation(0.175f,leftMoveTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.RUNNING_LEFT, leftMoveAnimation);

            // DEATH animation
            Array<TextureRegion> rightDeathTextures = new Array<>();
            Array<TextureRegion> leftDeathTextures = new Array<>();

            for (int i = 0; i < 6; i++) {

                TextureRegion rightDeathTexture = new TextureRegion(this.getTexture(),96*i,96*9,96,96);
                rightDeathTextures.add(rightDeathTexture);

                TextureRegion leftDeathTexture = new TextureRegion(this.getTexture(),96*i,96*9,96,96);
                leftDeathTexture.flip(true,false);
                leftDeathTextures.add(leftDeathTexture);
            }
            // RIGHT DEATH animation
            Animation rightDeathAnimation = new Animation(0.175f,rightDeathTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_RIGHT, rightDeathAnimation);

            Animation leftDeathAnimation = new Animation(0.175f,leftDeathTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_LEFT, leftDeathAnimation);

            // ATTACK animation

            Array<TextureRegion> rightAttackTextures = new Array<>();
            Array<TextureRegion> leftAttackTextures = new Array<>();
            for (int i = 0; i < 8; i++) {

                TextureRegion rightAttackTexture = new TextureRegion(this.getTexture(),96*i,96*3,96,96);
                rightAttackTextures.add(rightAttackTexture);

                TextureRegion leftAttackTexture = new TextureRegion(this.getTexture(),96*i,96*3,96,96);
                leftAttackTexture.flip(true,false);
                leftAttackTextures.add(leftAttackTexture);
            }
            // ATTACK ANIMATION right
            Animation attackAnimation = new Animation(0.12f,rightAttackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_RIGHT, attackAnimation);

            // ATTACK ANIMATION left
            Animation leftAttackAnimation = new Animation(0.12f,leftAttackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_LEFT, leftAttackAnimation);

        }

    }
}
