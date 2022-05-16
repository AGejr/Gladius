package Shop;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;
import Common.ui.Text;
import CommonWeapon.WeaponImages;
import Shop.ShopItems.ShopElixir;
import Shop.ShopItems.ShopWeapon;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        shopElixirs.add(new ShopElixir("Strength Elixir",100,130,200, tileSize * 2, tileSize * 10, 2));
        shopElixirs.add(new ShopElixir("Strength Elixir",200,130,100, tileSize * 4, tileSize * 10, 1));
        shopElixirs.add(new ShopElixir("Defence Elixir",300,130,200, tileSize * 3, tileSize * 10,2));
        shopElixirs.add(new ShopElixir("Defence Elixir",400,130,100, tileSize * 5, tileSize * 10,1));

        List<ShopWeapon> shopWeapons = new ArrayList<>();
        Map<ShopWeapon, List<Text>> weaponMap = new HashMap<>();
        Map<ShopElixir, List<Text>> elixirMap = new HashMap<>();
        shopWeapons.add(new ShopWeapon("Stone Sword", 700, 290, WeaponImages.STARTSWORD, 0,15,8, 10));
        shopWeapons.add(new ShopWeapon("Gold Sword", 500, 290, WeaponImages.GOLDSWORD, 100,20,10,10));
        shopWeapons.add(new ShopWeapon("Diamond Sword", 300, 290, WeaponImages.DIAMONDSWORD, 600,30,14,10));
        shopWeapons.add(new ShopWeapon("Club", 100, 290, WeaponImages.CLUB, 300,25,14,10));

        for (ShopWeapon weapon : shopWeapons) {
            List<Text> textList = new ArrayList<>();
            textList.add(new Text(weapon.getDescription(), 1, 10, -1, false));
            textList.add(new Text("$" + weapon.getPrice(), 1, 10, -1, false));
            textList.add(new Text(String.valueOf(weapon.getDamage()), 1, 10, -1, false));
            textList.add(new Text(String.valueOf(weapon.getRange()), 1, 10, -1, false));
            weaponMap.put(weapon, textList);
        }

        for (ShopElixir elixir: shopElixirs) {
            List<Text> elixirList = new ArrayList<>();
            elixirList.add(new Text(elixir.getDescription(), 1, 10, -1, false));
            elixirList.add(new Text(String.valueOf(elixir.getPrice()), 1, 10, -1, false));
            elixirList.add(new Text(String.valueOf(elixir.getStatIncrease()), 1, 10, -1, false));
            elixirMap.put(elixir, elixirList);
        }

        Entity shop = new Shop(files[0], shopElixirs, shopWeapons, weaponMap,elixirMap);

        shop.setX(608);
        shop.setY(192);

        return shop;
    }


    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(shop);
    }
}
