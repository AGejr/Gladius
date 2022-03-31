package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.GameKeys;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IEntityProcessingService;
import CommonWeapon.WeaponSPI;
import CommonPlayer.Player;
import Common.data.entityparts.AnimationPart;

public class PlayerControlSystem implements IEntityProcessingService {

    private WeaponSPI weaponService;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Player.class)){

            MovingPart movingPart = entity.getPart(MovingPart.class);
            //LifePart lifePart = entity.getPart(LifePart.class);
            AnimationPart animationPart = entity.getPart(AnimationPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

            // Key 6 = SPACE
            if (gameData.getKeys().isPressed(6) && weaponService != null) {
                weaponService.attack(entity, gameData, world);
            }

            movingPart.process(gameData, entity);
            animationPart.process(gameData,entity);
        }
    }

    public void setWeaponService(WeaponSPI weaponService) {
        this.weaponService = weaponService;
    }

    public void removeWeaponService(WeaponSPI weaponService) {
        this.weaponService = null;
    }
}
