package CommonWeapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;

public interface WeaponSPI {
    /* TODO The return type might be the Entities that are hit, needs to be
       determined
    */
    /**
     *
     * @param attacker: The entity attacking
     * @param gameData: The data of the game
     * */
    void attack(Entity attacker, GameData gameData, World world);
}
