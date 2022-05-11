package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private float speed;
    private boolean left, right, up, down;
    private boolean colTop, colBot, colLeft,colRight;
    private final float diagonalCorrectionVal = (float) (1 / sqrt(2));
    private float slower = (float) 1;
    private boolean isSlow = false;

    public MovingPart(float speed) {
        this.speed = speed;
    }

    public void setSlow(float slower) {
        this.slower = slower;
    }

    public void setIsSlow(boolean isSlow){
        this.isSlow = isSlow;
    }

    public boolean isSlow(){
        return isSlow;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down){ this.down = down; }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public void setColTop(boolean top) {
        this.colTop = top;
    }

    public void setColBot(boolean bottom) {
        this.colBot = bottom;
    }

    public void setColLeft(boolean left) {
        this.colLeft = left;
    }

    public void setColRight(boolean right) {
        this.colRight = right;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

        LifePart lifePart = entity.getPart(LifePart.class);
        if (lifePart != null && lifePart.isDead()) {
            return;
        }

        float dx = 0;
        float dy = 0;

        if (left && !colLeft) {
            // if moving left and either up or down, correct x speed according to pythagoras a^2+b^2=c^2
            dx -= Gdx.graphics.getDeltaTime() * speed * slower;
            if(up ^ down){
                dx *= diagonalCorrectionVal;
            }
        }

        if (right && !colRight) {
            dx += Gdx.graphics.getDeltaTime() * speed * slower;
            if(up ^ down){
                dx *= diagonalCorrectionVal;
            }
        }

        if (up && !colTop) {
            dy += Gdx.graphics.getDeltaTime() * speed * slower;
            if(left ^ right){
                dy *= diagonalCorrectionVal;
            }
        }

        if (down && !colBot){
            dy -= Gdx.graphics.getDeltaTime() * speed * slower;
            if(left ^ right){
                dy *= diagonalCorrectionVal;
            }
        }

        // set position
        entity.setX(entity.getX() + dx);
        entity.setY(entity.getY() + dy);
    }
}
