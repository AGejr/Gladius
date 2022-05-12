package Shop.ShopItems;

import CommonWeapon.WeaponImages;

public class ShopWeapon {
    private String description;
    private int x;
    private int y;
    private boolean owned = false;
    private WeaponImages weaponEnum;
    private int price;
    private int damage;
    private int weight;
    private int range;

    public ShopWeapon(String description, int x, int y, WeaponImages weaponEnum, int price, int damage, int weight, int range) {
        this.description = description;
        this.x = x;
        this.y = y;
        this.weaponEnum = weaponEnum;
        this.price = price;
        this.damage = damage;
        this.weight = weight;
        this.range = range;
    }

    public String getDescription() {
        return description;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOwned() {
        return owned;
    }

    public WeaponImages getWeaponEnum() {
        return weaponEnum;
    }

    public int getPrice() {
        return price;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public int getDamage() {
        return damage;
    }

    public int getWeight() {
        return weight;
    }

    public int getRange() {
        return range;
    }
}
