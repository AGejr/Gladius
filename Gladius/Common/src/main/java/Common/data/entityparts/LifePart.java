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
    private Color lostHealthColor = Color.LIGHT_GRAY;
    private final float healthbarWidth = 0.2f; //When 0.2 then the healthbar is 20 units wide total
    private final float healthbarHeight = 5.0f;
    private float lifePercent;
    public LifePart(int life) {
        this.life = life;
        this.MAXLIFE = life;
        this.lifePercent = ((float) life / (float) MAXLIFE) * 100;
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

    private void updateLifePercent() {
        this.lifePercent = ((float) life / (float) MAXLIFE) * 100;
    }

    public float getLifePercent() {
        return this.lifePercent;
    }

    public float getHealthbarWidth() {
        return this.healthbarWidth;
    }

    public float getHealthbarHeight() {
        return this.healthbarHeight;
    }

    public Color getHealthColor() {
        return this.healthbarColor;
    }

    public Color getLostHealthColor() {
        return this.lostHealthColor;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        updateLifePercent();
    }

    public void drawHealthBar(ShapeRenderer shapeRenderer, Entity entity) {
        shapeRenderer.setColor(this.getHealthColor());
        shapeRenderer.rect(entity.getX() + entity.getTextureWidth() / 2 - 10, entity.getY() - 10, (this.getHealthbarWidth() * this.getLifePercent()), this.getHealthbarHeight());
        shapeRenderer.setColor(this.getLostHealthColor());
        shapeRenderer.rect(entity.getX() + entity.getTextureWidth() / 2 - 10 + this.getHealthbarWidth() * this.getLifePercent(), entity.getY() - 10,  this.getHealthbarWidth()  * (100 - this.getLifePercent()), this.getHealthbarHeight());
    }

    public boolean isDead() {
        return this.life <= 0;
    }

    public void setHealthbarColor (Color healthbarColor) {
        this.healthbarColor  = healthbarColor;
    }
}
