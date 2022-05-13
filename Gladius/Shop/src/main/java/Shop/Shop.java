package Shop;

import Common.data.Entity;
import Common.ui.Text;
import CommonWeapon.WeaponImages;
import Shop.ShopItems.ShopElixir;
import Shop.ShopItems.ShopWeapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop extends Entity {
    private List<ShopElixir> shopElixirs;
    private List<ShopWeapon> shopWeapons;
    private Map<WeaponImages,List<Text>> weaponTextMap;
    private Map<String, List<Text>> elixirTextMap;

    public Shop(String texturePath, List<ShopElixir> shopElixirs, List<ShopWeapon> shopWeapons, Map<WeaponImages, List<Text>> weaponTextMap, Map<String, List<Text>> elixirTextMap) {
        super(texturePath, 0, 96, 62);
        this.shopElixirs = shopElixirs;
        this.shopWeapons = shopWeapons;
        this.weaponTextMap = weaponTextMap;
        this.elixirTextMap = elixirTextMap;
    }

    public List<ShopElixir> getShopElixirs() {
        return shopElixirs;
    }

    public List<ShopWeapon> getShopWeapons() {
        return shopWeapons;
    }

    public Map<WeaponImages, List<Text>> getWeaponTextMap() {
        return weaponTextMap;
    }
    public Map<String, List<Text>> getElixirTextMap() {
        return elixirTextMap;
    }

    public List<Text> getWeaponTextList(WeaponImages key) {
        return weaponTextMap.get(key);
    }

}
