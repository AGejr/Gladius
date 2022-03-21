package Weapon;
import Common.data.Entity;
public abstract class Weapon extends Entity {
    private String name;
    private float damage;
    private float weight;

    protected Weapon(String name, float damage, float weight) {
        this.name = name;
        this.damage = damage;
        this.weight = weight;
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
