package Weapon;

import CommonWeapon.Weapon;

public class Sword extends Weapon {
    private static final String texturePath = "swordMiniRect.png";
    private static Weapon sword = new Sword("Sword", 10, 8, 10);
    private Sword(String name, int damage, float weight, float range) {
        super(name, damage, weight, range, texturePath);
    }

    public static Weapon getSword() {
        return sword;
    }
}