package Shop;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;
import CommonWeapon.WeaponImages;
import Shop.ShopItems.ShopElixir;
import Shop.ShopItems.ShopWeapon;

import java.util.ArrayList;
import java.util.List;

public class ShopPlugin implements IGamePluginService {
    private Entity shop;
    private final int tileSize = 16;

    @Override
    public void start(GameData gameData, World world) {
        shop = createShop();
        world.addEntity(shop);
    }

    private Entity createShop() {
        String[] files = {"Shop.png", "ShopItems.png"};
        FileLoader.loadFiles(files, this.getClass());
        List<ShopElixir> shopElixirs = new ArrayList<>();
        shopElixirs.add(new ShopElixir("Strength Elixir",100,130,200, tileSize * 2, tileSize * 10, 5));
        shopElixirs.add(new ShopElixir("Strength Elixir",200,130,100, tileSize * 4, tileSize * 10, 15));
        shopElixirs.add(new ShopElixir("Defence Elixir",300,130,200, tileSize * 3, tileSize * 10,5));
        shopElixirs.add(new ShopElixir("Defence Elixir",400,130,100, tileSize * 5, tileSize * 10,15));

        List<ShopWeapon> shopWeapons = new ArrayList<>();
        shopWeapons.add(new ShopWeapon("Stone Sword", 700, 290, WeaponImages.STARTSWORD, 0,25,8, 10));
        shopWeapons.add(new ShopWeapon("Gold Sword", 500, 290, WeaponImages.GOLDSWORD, 100,40,10,12));
        shopWeapons.add(new ShopWeapon("Diamond Sword", 300, 290, WeaponImages.DIAMONDSWORD, 200,50,14,14));
        shopWeapons.add(new ShopWeapon("Club", 100, 290, WeaponImages.CLUB, 300,45,14,14));

        Entity shop = new Shop(files[0], shopElixirs, shopWeapons);

        shop.setX(608);
        shop.setY(192);
        return shop;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(shop);
    }
}
