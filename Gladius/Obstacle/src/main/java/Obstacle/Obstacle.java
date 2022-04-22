package Obstacle;

import Common.data.Entity;
import Common.data.entityparts.AnimationPart;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Obstacle extends Entity {

    public Obstacle(String texturePath, int radius) {
        super(texturePath, radius, 64, 64);
    }

    @Override
    public void initTexture(){
        super.initTexture();
        this.setRegion(0,0,64, 64);
        initAnimation();
    }

    private void initAnimation(){
        if(this.getPart(AnimationPart.class) != null){
            AnimationPart animationPart = this.getPart(AnimationPart.class);

            // IDLE animation
            Array<TextureRegion> idleTextures = new Array<>();
            for (int i = 0; i < 1; i++) {
                idleTextures.add(new TextureRegion(this.getTexture(),64*i,0,64,64));
            }
            Animation idleAnimation = new Animation(0.24f,idleTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_RIGHT, idleAnimation);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_LEFT, idleAnimation);

            // TAKE DAMAGE animation
            Array<TextureRegion> takeDamageTextures = new Array<>();
            for (int i = 0; i < 3; i++) {
                takeDamageTextures.add(new TextureRegion(this.getTexture(),64*i,0,64,64));
            }
            Animation takeDamageAnimation = new Animation(0.16f,takeDamageTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.TAKE_DAMAGE, takeDamageAnimation);

            // DEATH animation
            Array<TextureRegion> DeathTextures = new Array<>();
            for (int i = 0; i < 7; i++) {
                TextureRegion DeathTexture = new TextureRegion(this.getTexture(),64*i,64,64,64);
                DeathTextures.add(DeathTexture);
            }
            Animation DeathAnimation = new Animation(0.10f,DeathTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_RIGHT, DeathAnimation);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_LEFT, DeathAnimation);
        }

    }
}
