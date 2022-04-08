package CommonWeapon;
import Common.data.Entity;
public class Weapon extends Entity {
    private String name;
    private int damage;
    private float weight;
    private final String texturePath;

    public Weapon(String name, int damage, float weight, float range, String texturePath) {
        super(null, range, 9, 36, 0, 0.9f, 0.9f, 0);
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        this.texturePath = texturePath;
    }

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        return this.damage;
    }

    public float getWeight() {
        return this.weight;
    }

    public float getRange() {return super.getRadius();}

    public void removeWeaponTexture() { super.removeTexturePath();}
    public void setWeaponTexture() {super.setTexturePath(this.texturePath);}
}
