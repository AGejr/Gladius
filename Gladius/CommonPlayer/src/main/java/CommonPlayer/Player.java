package CommonPlayer;

import Common.data.Entity;
import Common.data.entityparts.AnimationPart;
import CommonWeapon.IWeaponUserService;
import CommonWeapon.Weapon;
import CommonWeapon.WeaponImages;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity implements IWeaponUserService {
    int balance;
    private List<Weapon> ownedWeapons = new ArrayList<>();
    private Weapon equippedWeapon;

    public Player(String texturePath,float radius, int textureWidth, int textureHeight, float angle, float hitboxScaleX, float hitboxScaleY) {
        super(texturePath, radius, textureWidth, textureHeight, angle, hitboxScaleX, hitboxScaleY);
        this.balance = 0;
        Weapon sword = new Weapon("Sword", 25, 8, 10, WeaponImages.SWORD.path, 9, 36, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f, 9.0f, 10.0f, this);
        ownedWeapons.add(sword);
        equippedWeapon = ownedWeapons.get(0);
    }

    public Player(String texturePath, int radius) {
        super(texturePath,radius, 32, 32);
        this.balance = 0;
        Weapon sword = new Weapon("Sword", 25, 8, 10, WeaponImages.SWORD.path, 9, 36, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f, 9.0f, 10.0f, this);
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
            for (int i = 0; i < 5; i++) {
                // Attacking right
                rightAttackTextures.add(new TextureRegion(this.getTexture(),32*i,64,32,32));

                // Attacking left
                TextureRegion leftTexture = new TextureRegion(this.getTexture(),32*i,64,32,32);
                leftTexture.flip(true,false);
                leftAttackTextures.add(leftTexture);
            }
            // ATTACK right animation
            Animation rightAttackAnimation = new Animation(0.10f,rightAttackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_RIGHT, rightAttackAnimation);
            // ATTACK left animation
            Animation leftAttackAnimation = new Animation(0.10f,leftAttackTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.ATTACK_LEFT, leftAttackAnimation);

            // TAKE DAMAGE animation
            Array<TextureRegion> rightTakeDamageTextures = new Array<>();
            Array<TextureRegion> leftTakeDamageTextures = new Array<>();
            for (int i = 0; i < 3; i++) {
                // Take damage right
                rightTakeDamageTextures.add(new TextureRegion(this.getTexture(),32*i,96,32,32));

                // Take damage left
                TextureRegion leftTexture = new TextureRegion(this.getTexture(),32*i,96,32,32);
                leftTexture.flip(true,false);
                leftTakeDamageTextures.add(leftTexture);
            }
            // TAKE DAMAGE right animation
            Animation rightTakeDamageAnimation = new Animation(0.08f,rightTakeDamageTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.TAKE_DAMAGE_RIGHT, rightTakeDamageAnimation);
            // TAKE DAMAGE left animation
            Animation leftTakeDamageAnimation = new Animation(0.08f,leftTakeDamageTextures);
            animationPart.addAnimation(AnimationPart.ANIMATION_STATES.TAKE_DAMAGE_LEFT, leftTakeDamageAnimation);

        }

   }

}
