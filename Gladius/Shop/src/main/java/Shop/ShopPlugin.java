package Shop;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;

import java.util.List;

public class ShopPlugin implements IGamePluginService {
    private Entity shop;

    @Override
    public void start(GameData gameData, World world) {
        shop = createShop();
        world.addEntity(shop);
    }

    private Entity createShop() {
        String[] files = {"Shop.png", "ShopItems.png"};
        FileLoader.loadFiles(files, this.getClass());

        Entity shop = new Shop(files[0]);

        shop.setX(608);
        shop.setY(192);
        return shop;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(shop);
    }
}
