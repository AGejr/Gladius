package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;

public class MovingPart implements EntityPart {

    private float speed;
    private boolean left, right, up, down;
    private boolean colTop = false, colBottom = false, colLeft = false, colRight = false;

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

    public void setColTop(boolean colTop) {
        this.colTop = colTop;
    }

    public void setColBottom(boolean colBottom) {
        this.colBottom = colBottom;
    }

    public void setColLeft(boolean colLeft) {
        this.colLeft = colLeft;
    }

    public void setColRight(boolean colRight) {
        this.colRight = colRight;
    }

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


        if (left && !colLeft) {
            x -= Gdx.graphics.getDeltaTime() * speed;
        }

        if (right && !colRight) {
            x += Gdx.graphics.getDeltaTime() * speed;
        }

        if (up && !colTop) {
            y += Gdx.graphics.getDeltaTime() * speed;
        }

        if (down && !colBottom){
            y -= Gdx.graphics.getDeltaTime() * speed;
        }


        // set position
        entity.setX(x);
        entity.setY(y);
    }
}
