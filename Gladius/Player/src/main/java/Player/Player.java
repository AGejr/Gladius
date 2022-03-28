package Player;

import Common.data.Entity;
import CommonWeapon.IWeaponUser;
import CommonWeapon.Weapon;

public class Player extends Entity implements IWeaponUser {
    int balance;
    // TODO Change the code below when shop is invented
    // protected Sword(String name, float damage, float weight, float range) {
    private Weapon weapon;

    public Player(String texturePath,int radius) {
        super(texturePath, radius);
        this.balance = 0;
    }

    public Player(Weapon weapon, String texturePath,int radius) {
        super(texturePath, radius);
        this.weapon = weapon;
    }


    @Override
    public Weapon getWeapon() {
        return this.weapon;
    }
}
