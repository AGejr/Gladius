package CommonWeapon;
import Common.data.Entity;
import java.util.List;
import java.util.ArrayList;
public class Weapon extends Entity {
    private String name;
    private int damage;
    private float weight;
    private final String texturePath;
    private Entity owner;
    private List<Entity> hitEntityList;

    public Weapon(String name, int damage, float weight, float range, String texturePath, Entity owner) {
        super(null, range, 9, 36, 0, 0.9f, 0.9f, 0);
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        this.texturePath = texturePath;
        this.owner = owner;
        this.hitEntityList = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public Entity getOwner() {
        return this.owner;
    }

    public int getDamage() {
        return this.damage;
    }

    public float getWeight() {
        return this.weight;
    }

    public float getRange() {
        return super.getRadius();
    }

    public void removeWeaponTexture() {
        super.removeTexturePath();
    }

    public void setWeaponTexture() {
        super.setTexturePath(this.texturePath);
    }

    public void addEntityHitted(Entity entity) {
        this.hitEntityList.add(entity);
    }

    public boolean isEntityHitted(Entity entity) {
        return this.hitEntityList.contains(entity);
    }

    public void resetHitEntityList() {
        this.hitEntityList.clear();
    }
}
