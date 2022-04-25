package Shop;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;

public class ShopPlugin implements IGamePluginService {
    private Entity shop;

    @Override
    public void start(GameData gameData, World world) {
        shop = createShop();
        world.addEntity(shop);
    }

    private Entity createShop() {
        String file = "Shop.png";
        FileLoader.loadFile(file, this.getClass());

        Entity shop = new Shop(file);

        shop.setX(608);
        shop.setY(160);
        return shop;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(shop);
    }
}
