package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;

public class LifePart implements EntityPart {
    private int life;
    private boolean dead = false;

    public LifePart(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (life <= 0) {
            dead = true;
        }
    }
}
