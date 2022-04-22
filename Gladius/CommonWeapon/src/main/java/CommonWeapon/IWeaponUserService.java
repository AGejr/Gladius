package CommonWeapon;

import Common.data.Entity;

/**
    All entities who need a weapon needs to implement this interface.
 */
public interface IWeaponUserService {
    Weapon getWeapon();
    void equipWeapon(Weapon weapon);
}
