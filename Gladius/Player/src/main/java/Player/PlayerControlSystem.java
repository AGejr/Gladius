package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.GameKeys;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponService;
import CommonPlayer.Player;
import Common.data.entityparts.AnimationPart;

public class PlayerControlSystem implements IEntityProcessingService {

    private IWeaponService weaponService;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player: world.getEntities(Player.class)){

            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            AnimationPart animationPart = player.getPart(AnimationPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

            if (gameData.getKeys().isPressed(GameKeys.SPACE) && weaponService != null && !lifePart.isDead()) {
                weaponService.attack(player, gameData, world);
                animationPart.setAttack();
            }

            if (gameData.getKeys().isPressed(GameKeys.P)) {
                if(gameData.isDebugMode()) {
                    gameData.setDebugMode(false);
                } else {
                    gameData.setDebugMode(true);
                }
            }

            movingPart.process(gameData, player);
            animationPart.process(gameData, player);
            lifePart.process(gameData, player);
        }
    }

    public void setWeaponService(IWeaponService weaponService) {
        this.weaponService = weaponService;
    }

    public void removeWeaponService(IWeaponService weaponService) {
        this.weaponService = null;
    }
}
