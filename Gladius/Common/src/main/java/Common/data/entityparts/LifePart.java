package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;

public class LifePart implements EntityPart {
    private final int MAXLIFE;
    private int life;

    public LifePart(int life) {
        this.life = life;
        this.MAXLIFE = life;
    }

    public int getLife() {
        return life;
    }

    public void subtractLife(int damage) {
        this.life = life - damage;
    }

    public void resetLife() {
        this.life = MAXLIFE;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public boolean isDead(){
        return this.life <= 0;
    }
}
