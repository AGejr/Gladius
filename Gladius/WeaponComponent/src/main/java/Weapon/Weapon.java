package Weapon;
import Common.data.Entity;
public abstract class Weapon extends Entity {
    private String name;
    private float damage;
    private float weight;
    private float range;

    protected Weapon(String name, float damage, float weight, float range) {
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        this.setRadius(range);
    }

    public String getName() {
        return this.name;
    }

    public float getDamage() {
        return this.damage;
    }

    public float getWeight() {
        return this.weight;
    }
}
