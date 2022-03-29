package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private float speed;
    private boolean left, right, up, down;
    private final float diagonalCorrectionVal = (float) (1 / sqrt(2));

    public float getSpeed() {
        return speed;
    }

    public MovingPart(float speed) {
        this.speed = speed;
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

    @Override
    public void process(GameData gameData, Entity entity) {
        float x = entity.getX();
        float y = entity.getY();

        if (left) {
            // if moving left and either up or down, correct x speed according to pythagoras a^2+b^2=c^2
            if(up ^ down){
                x -= Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                x -= Gdx.graphics.getDeltaTime() * speed;
            }
        }

        if (right) {
            if(up ^ down){
                x += Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                x += Gdx.graphics.getDeltaTime() * speed;
            }
        }

        if (up) {
            if(left ^ right){
                y += Gdx.graphics.getDeltaTime() * speed * diagonalCorrectionVal;
            } else {
                y += Gdx.graphics.getDeltaTime() * speed;
            }
        }

        if (down){
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
