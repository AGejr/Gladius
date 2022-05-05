package Shop;

import Common.data.Entity;
import Common.data.World;
import Common.data.entityparts.StatsPart;
import CommonPlayer.Player;
import CommonWeapon.Weapon;
import CommonWeapon.WeaponImages;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ShopControlSystemTest {

    private Player player;
    private int clubPrice = 100;
    private Map<WeaponImages, Weapon> swordMap = new HashMap<>();

    @org.junit.Before
    public void setUp() throws Exception {
        player = new Player(null,2);
        player.add(new StatsPart(10,10,0));
        Weapon sword = new Weapon("Sword", 10, 10, 10, WeaponImages.CLUB.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f, 10.0f, player, clubPrice);
        swordMap.put(WeaponImages.CLUB,sword);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void process() {
        StatsPart statsPart = player.getPart(StatsPart.class);

        assertEquals(statsPart.getBalance(),0);
        statsPart.depositBalance(100);
        assertEquals(statsPart.getBalance(),100);

        int balance = statsPart.getBalance();
        buyWeapon(statsPart,WeaponImages.CLUB, player);

        assertEquals(statsPart.getBalance(),balance-clubPrice);

        buyWeapon(statsPart,WeaponImages.CLUB, player);

        assertEquals(statsPart.getBalance(),balance-clubPrice);
    }

    private void buyWeapon(StatsPart statsPart, WeaponImages weaponEnum, Player player) {
        if (statsPart.getBalance() >= swordMap.get(weaponEnum).getPrice()) {
            statsPart.withdrawBalance(swordMap.get(weaponEnum).getPrice());
            Weapon weapon = swordMap.get(weaponEnum);
            player.addWeapon(weapon);
            player.equipWeapon(weapon);
        }
    }
}