package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LifePart implements EntityPart {
    private final int MAXLIFE; // Maximum life possible for the entity
    /***
     * @param life is the beginning life of the entity
     * @param color is the color of the remaining life in the health bar
     */
    private int life;
    private Color healthbarColor = null;
    private final float healthbarWidth = 0.2f; //When 0.2 then the healthbar is 20 units wide total
    private final float healthbarHeight = 5.0f;

    public LifePart(int life) {
        this.life = life;
        this.MAXLIFE = life;
    }

    public LifePart(int life, Color healthbarColor) {
        this(life);
        this.healthbarColor = healthbarColor;
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
        if (this.healthbarColor != null) {
            ShapeRenderer sr = new ShapeRenderer();
            sr.setProjectionMatrix(gameData.getCam().combined);
            float lifePercent = ((float) life / (float) MAXLIFE) * 100;
            sr.begin(ShapeType.Filled);
            sr.setColor(this.healthbarColor);
            sr.rect(entity.getX() + entity.getTextureWidth() / 2 - 10, entity.getY() - 10, (healthbarWidth * lifePercent), healthbarHeight);
            sr.setColor(Color.LIGHT_GRAY);
            sr.rect(entity.getX() + entity.getTextureWidth() / 2 - 10 + (float) (healthbarWidth * lifePercent), entity.getY() - 10,  healthbarWidth * (100 - lifePercent), healthbarHeight);
            sr.end();
        }
    }

    public boolean isDead() {
        return this.life <= 0;
    }

    public void setHealthbarColor (Color healthbarColor) {
        this.healthbarColor  = healthbarColor;
    }
}
