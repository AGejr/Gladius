package CommonWeapon;

public class Sword extends Weapon {
    private static final String texturePath = "pictures\\sword.png";
    private static Weapon sword = new Sword("Sword", 10, 8, 10);
    private Sword(String name, float damage, float weight, float range) {
        super(name, damage, weight, range, texturePath);
    }

    public static Weapon getSword() {
        return sword;
    }
}
