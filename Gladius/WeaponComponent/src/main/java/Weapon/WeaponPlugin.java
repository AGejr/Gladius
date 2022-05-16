package Weapon;

import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import CommonWeapon.Weapon;
import Common.data.Entity;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity weapon : world.getEntities(Weapon.class)) {
            if (weapon instanceof  Weapon) {
                if (weapon.getTexture() != null) {
                    weapon.getTexture().dispose();
                }
                world.removeEntity(weapon);
            }
        }
    }
}
