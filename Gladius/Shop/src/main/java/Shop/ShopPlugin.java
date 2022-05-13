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
        shopElixirs.add(new ShopElixir("Strength Elixir",100,130,200, tileSize * 2, tileSize * 10, 5));
        shopElixirs.add(new ShopElixir("Better Strength Elixir",180,130,100, tileSize * 4, tileSize * 10, 15));
        shopElixirs.add(new ShopElixir("Defence Elixir",300,130,200, tileSize * 3, tileSize * 10,5));
        shopElixirs.add(new ShopElixir("Better Defence Elixir",380,130,100, tileSize * 5, tileSize * 10,15));

        List<ShopWeapon> shopWeapons = new ArrayList<>();
        Map<WeaponImages, List<Text>> weaponMap = new HashMap<>();
        Map<String, List<Text>> elixirMap = new HashMap<>();
        shopWeapons.add(new ShopWeapon("Stone Sword", 700, 290, WeaponImages.STARTSWORD, 0,25,8, 10));
        shopWeapons.add(new ShopWeapon("Gold Sword", 500, 290, WeaponImages.GOLDSWORD, 100,40,10,10));
        shopWeapons.add(new ShopWeapon("Diamond Sword", 300, 290, WeaponImages.DIAMONDSWORD, 200,50,14,10));
        shopWeapons.add(new ShopWeapon("Club", 100, 290, WeaponImages.CLUB, 300,45,14,10));
//        public Text(String text,int scale, int fontSize, int duration) {
        for (ShopWeapon weapon : shopWeapons) {
            List<Text> textList = new ArrayList<>();
            textList.add(new Text(weapon.getDescription(), 1, 10, -1, false));
            textList.add(new Text("$" + weapon.getPrice(), 1, 10, -1, false));
            textList.add(new Text(String.valueOf(weapon.getDamage()), 1, 10, -1, false));
            textList.add(new Text(String.valueOf(weapon.getRange()), 1, 10, -1, false));
            weaponMap.put(weapon.getWeaponEnum(), textList);
        }
        for (ShopElixir elixir: shopElixirs) {
            List<Text> elixirList = new ArrayList<>();
            elixirList.add(new Text(elixir.getDescription(), 1, 10, -1, false));
            elixirList.add(new Text(String.valueOf(elixir.getPrice()), 1, 10, -1, false));
            elixirList.add(new Text(String.valueOf(elixir.getStatIncrease()), 1, 10, -1, false));
            elixirMap.put(elixir.getDescription(), elixirList);
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
