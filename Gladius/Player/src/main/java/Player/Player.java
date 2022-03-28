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

            //idle animation

            Array<TextureRegion> idleTextures = new Array<>();
            idleTextures.add(new TextureRegion(this.getTexture(),0,0,32,32));
            idleTextures.add(new TextureRegion(this.getTexture(),32,0,32,32));
            idleTextures.add(new TextureRegion(this.getTexture(),64,0,32,32));
            idleTextures.add(new TextureRegion(this.getTexture(),96,0,32,32));
            idleTextures.add(new TextureRegion(this.getTexture(),128,0,32,32));

            Animation idleAnimation = new Animation(0.10f,idleTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE, idleAnimation);

            Array<TextureRegion> rightMoveTextures = new Array<>();
            Array<TextureRegion> leftMoveTextures = new Array<>();
            for (int i = 0; i < 5; i++) {

                rightMoveTextures.add(new TextureRegion(this.getTexture(),32*i,32,32,32));

                TextureRegion leftTexture = new TextureRegion(this.getTexture(),32*i,32,32,32);
                leftTexture.flip(true,false);

                leftMoveTextures.add(leftTexture);
            }

            Animation leftMoveAnimation = new Animation(0.10f,leftMoveTextures);
            Animation rightMoveAnimation = new Animation(0.10f,rightMoveTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.RUNNING_LEFT, leftMoveAnimation);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.RUNNING_RIGHT, rightMoveAnimation);


        }

   }

}
