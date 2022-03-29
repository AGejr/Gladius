package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private float speed, slowedSpeed;
    private boolean left, right, up, down, isSlow;
    private boolean colTop, colBot, colLeft,colRight;
    private final float diagonalCorrectionVal = (float) (1 / sqrt(2));

    public float getSpeed() {
        return speed;
    }

    public MovingPart(float speed) {
        this.speed = speed;
    }

    public void setSlow(boolean slow, int slowAmount) {
        if(slow && !isSlow) {
            slowedSpeed = slowAmount;
            this.speed -= slowedSpeed;
            isSlow = true;
        }
        else if(!slow && isSlow) {
            this.speed += slowedSpeed;
            isSlow = false;
        }
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
        float x = entity.getX();
        float y = entity.getY();

        if (left && !colLeft) {
            // if moving left and either up or down, correct x speed according to pythagoras a^2+b^2=c^2
            if(up ^ down){
                x -= Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                x -= Gdx.graphics.getDeltaTime() * speed;
            }
        }

        if (right && !colRight) {
            if(up ^ down){
                x += Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                x += Gdx.graphics.getDeltaTime() * speed;
            }
        }

        if (up && !colTop) {
            if(left ^ right){
                y += Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                y += Gdx.graphics.getDeltaTime() * speed;
            }
        }

        if (down && !colBot){
            if(left ^ right){
                y -= Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                y -= Gdx.graphics.getDeltaTime() * speed;
            }
        }


        // set position
        entity.setX(x);
        entity.setY(y);
    }
}
