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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class PlayerControlSystem implements IEntityProcessingService {

    private IWeaponService weaponService;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Player.class)){

            MovingPart movingPart = entity.getPart(MovingPart.class);
            LifePart lifePart = entity.getPart(LifePart.class);
            AnimationPart animationPart = entity.getPart(AnimationPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

            if (gameData.getKeys().isPressed(GameKeys.SPACE) && weaponService != null && !lifePart.isDead()) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("hitSound.mp3"));
                sound.play(1.0f);
                weaponService.attack(entity, gameData, world);

            }

            movingPart.process(gameData, entity);
            animationPart.process(gameData,entity);
            lifePart.process(gameData,entity);
        }
    }

    public void setWeaponService(IWeaponService weaponService) {
        this.weaponService = weaponService;
    }

    public void removeWeaponService(IWeaponService weaponService) {
        this.weaponService = null;
    }
}
