package Weapon;
import Common.data.Entity;
public abstract class Weapon extends Entity {
    private String name;
    private float damage;
    private float weight;
    private final String texturePath;

    protected Weapon(String name, float damage, float weight, float range, String texturePath) {
        super(null, range);
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        this.texturePath = texturePath;
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

    public float getRange() {return super.getRadius();}

    public void removeWeaponTexture() { super.removeTexturePath();}
    public void setWeaponTexture() {setTexturePath(this.texturePath);}
}
