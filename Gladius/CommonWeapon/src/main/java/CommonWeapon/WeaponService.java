package CommonWeapon;

import Common.data.Entity;
import Common.data.GameData;

public interface WeaponService {
    /* TODO The return type might be the Entities that are hit, needs to be
       determined
    */
    void attack(Entity attacker, GameData gameData);
}
