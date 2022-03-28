package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.Gdx;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private float speed;
    private boolean left, right, up, down;

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

    @Override
    public void process(GameData gameData, Entity entity) {
        float x = entity.getX();
        float y = entity.getY();

        if (left) {
            x -= Gdx.graphics.getDeltaTime() * speed;
        }

        if (right) {
            x += Gdx.graphics.getDeltaTime() * speed;
        }

        if (up) {
            y += Gdx.graphics.getDeltaTime() * speed;
        }

        if (down){
            y -= Gdx.graphics.getDeltaTime() * speed;
        }


        // set position
        entity.setX(x);
        entity.setY(y);
    }
}
