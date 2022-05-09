package Shop;

import Common.data.Entity;
import Shop.ShopItems.ShopElixir;
import Shop.ShopItems.ShopWeapon;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Entity {
    private List<ShopElixir> shopElixirs;
    private List<ShopWeapon> shopWeapons;

    public Shop(String texturePath, List<ShopElixir> shopElixirs, List<ShopWeapon> shopWeapons) {
        super(texturePath, 0, 96, 62);
        this.shopElixirs = shopElixirs;
        this.shopWeapons = shopWeapons;
    }

    public List<ShopElixir> getShopElixirs() {
        return shopElixirs;
    }

    public List<ShopWeapon> getShopWeapons() {
        return shopWeapons;
    }
}
