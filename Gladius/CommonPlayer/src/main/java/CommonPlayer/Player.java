package CommonPlayer;

import Common.data.Entity;
import Common.data.entityparts.AnimationPart;
import CommonWeapon.IWeaponUserService;
import CommonWeapon.Weapon;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity implements IWeaponUserService {
    int balance;
    private List<Weapon> ownedWeapons = new ArrayList<>();
    private Weapon equippedWeapon;

    public Player(String texturePath, int radius) {
        super(texturePath,radius, 32, 32);
        this.balance = 0;
        Weapon sword = new Weapon("Sword", 10, 8, 10, "swordMiniRect.png");
        ownedWeapons.add(sword);
        equippedWeapon = ownedWeapons.get(0);
    }

    @Override
    public void initTexture(){
        super.initTexture();
        initAnimation();
    }

    @Override
    public Weapon getWeapon() {
        return this.equippedWeapon;
    }

    @Override
    public void equipWeapon(Weapon weapon) {
        if(ownedWeapons.contains(weapon)) {
            this.equippedWeapon = weapon;
        }
    }

    public void addWeapon(Weapon weapon) {
        ownedWeapons.add(weapon);
    }

    public void removeWeapon(Weapon weapon) {
        ownedWeapons.remove(weapon);
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
            Animation idleRightAnimation = new Animation(0.16f,idleRightTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.IDLE_RIGHT, idleRightAnimation);

            // IDLE left animation
            Animation leftIdleAnimation = new Animation(0.16f,idleLeftTextures);
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
            Array<TextureRegion> rightDeathTextures = new Array<>();
            Array<TextureRegion> leftDeathTextures = new Array<>();

            for (int i = 0; i < 7; i++) {

                TextureRegion rightDeathTexture = new TextureRegion(this.getTexture(),32*i,128,32,32);
                rightDeathTextures.add(rightDeathTexture);

                TextureRegion leftDeathTexture = new TextureRegion(this.getTexture(),32*i,128,32,32);
                leftDeathTexture.flip(true,false);
                leftDeathTextures.add(leftDeathTexture);
            }
            // RIGHT DEATH animation
            Animation rightDeathAnimation = new Animation(0.10f,rightDeathTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_RIGHT, rightDeathAnimation);

            Animation leftDeathAnimation = new Animation(0.10f,leftDeathTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.DEATH_LEFT, leftDeathAnimation);

            // ATTACK animation

            Array<TextureRegion> rightAttackTextures = new Array<>();
            Array<TextureRegion> leftAttackTextures = new Array<>();
            for (int i = 0; i < 7; i++) {

                TextureRegion rightAttackTexture = new TextureRegion(this.getTexture(),32*i,64,32,32);
                rightAttackTextures.add(rightAttackTexture);

                TextureRegion leftAttackTexture = new TextureRegion(this.getTexture(),32*i,64,32,32);
                leftAttackTexture.flip(true,false);
                leftAttackTextures.add(leftAttackTexture);
            }
            // ATTACK ANIMATION right
            Animation attackAnimation = new Animation(0.10f,rightAttackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_RIGHT, attackAnimation);

            // ATTACK ANIMATION left
            Animation leftAttackAnimation = new Animation(0.10f,leftAttackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_LEFT, leftAttackAnimation);

        }

   }

}
