package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.PositionPart;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponService;

public class WeaponControlSystem implements IEntityProcessingService, IWeaponService {
    @Override
    public void process(GameData gameData, World world) {

    }

    @Override
    public void attack(Entity attacker, GameData gameData) {
        MovingPart attackerMovingPart = attacker.getPart(MovingPart.class);
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        float deltaTime = gameData.getDelta();
        // TODO get attacker 'radians'
        // int radians = attacker.getRadians()


    }
}
