package Player;

import Common.data.Entity;
import Common.data.entityparts.AnimationPart;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

public class Player extends Entity {
    int balance;

    public Player(String texturePath,int radius) {
        super(texturePath,radius);
        this.balance = 0;
    }

    @Override
    public void initTexture(){
        super.initTexture();
        initAnimation();
        this.setRegion(0,0,32,32);
    }

   private void initAnimation(){
        if(this.getPart(AnimationPart.class) != null){
            AnimationPart animationPart = this.getPart(AnimationPart.class);

            // IDLE animation

            Array<TextureRegion> idleRightTextures = new Array<>();
            Array<TextureRegion> idleLeftTextures = new Array<>();

            for (int i = 0; i < 5; i++) {
                // IDLE right
                idleRightTextures.add(new TextureRegion(this.getTexture(),32*i,0,32,32));

                // IDLE left
                TextureRegion idleLeftTexture = new TextureRegion(this.getTexture(),32*i,0,32,32);
                idleLeftTexture.flip(true, false);
                idleLeftTextures.add(idleLeftTexture);
            }

            // IDLE right animation
            Animation idleRightAnimation = new Animation(0.10f,idleRightTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_RIGHT, idleRightAnimation);

            // IDLE left animation
            Animation leftIdleAnimation = new Animation(0.10f,idleLeftTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_LEFT, leftIdleAnimation);

            // Running animation

            Array<TextureRegion> rightMoveTextures = new Array<>();
            Array<TextureRegion> leftMoveTextures = new Array<>();

            for (int i = 0; i < 5; i++) {
                // Running right
                rightMoveTextures.add(new TextureRegion(this.getTexture(),32*i,32,32,32));

                // Running left
                TextureRegion leftTexture = new TextureRegion(this.getTexture(),32*i,32,32,32);
                leftTexture.flip(true,false);
                leftMoveTextures.add(leftTexture);
            }

            // RUNNING right animation
            Animation rightMoveAnimation = new Animation(0.10f,rightMoveTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.RUNNING_RIGHT, rightMoveAnimation);

            // RUNNING left animation
            Animation leftMoveAnimation = new Animation(0.10f,leftMoveTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.RUNNING_LEFT, leftMoveAnimation);

            // DEATH animation
            // TODO FLIP DEATH
            Array<TextureRegion> deathTextures = new Array<>();
            for (int i = 0; i < 7; i++) {

                TextureRegion deathTexture = new TextureRegion(this.getTexture(),32*i,128,32,32);
                deathTextures.add(deathTexture);
            }
            Animation deathAnimation = new Animation(0.10f,deathTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_RIGHT, deathAnimation);

            // ATTACK animation
            // TODO FLIP ATTACK
            Array<TextureRegion> attackTextures = new Array<>();
            for (int i = 0; i < 7; i++) {

                TextureRegion attackTexture = new TextureRegion(this.getTexture(),32*i,64,32,32);
                attackTextures.add(attackTexture);
            }
            Animation attackAnimation = new Animation(0.10f,attackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_RIGHT, attackAnimation);

        }

   }

}
