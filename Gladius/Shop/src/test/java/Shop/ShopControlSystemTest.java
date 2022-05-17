package Shop;

import Common.data.Entity;
import Common.data.World;
import Common.data.entityparts.StatsPart;
import CommonPlayer.Player;
import CommonWeapon.Weapon;
import CommonWeapon.WeaponImages;
import Shop.ShopItems.ShopWeapon;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ShopControlSystemTest {

    private Player player;
    private final int clubPrice = 100;
    private final Map<WeaponImages, Weapon> swordMap = new HashMap<>();

    @org.junit.Before
    public void setUp() throws Exception {
        player = new Player(null,2);
        player.add(new StatsPart(10,10,0));
        Weapon sword = new Weapon("Sword", 10, 10, 10, WeaponImages.CLUB.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f,9.0f, 10.0f, player);
        swordMap.put(WeaponImages.CLUB,sword);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void process() {
        StatsPart statsPart = player.getPart(StatsPart.class);
        ShopWeapon shopWeapon = new ShopWeapon("Club", 100, 290,WeaponImages.CLUB, clubPrice,45,14,14);


        assertEquals(statsPart.getBalance(),0);
        statsPart.depositBalance(100);
        //Assert that 100 is deposit
        assertEquals(statsPart.getBalance(),100);

        int balance = statsPart.getBalance();
        buyWeapon(statsPart,WeaponImages.CLUB, shopWeapon, player);

        //Assert that you have bought the club
        assertEquals(statsPart.getBalance(),balance-clubPrice);

        buyWeapon(statsPart,WeaponImages.CLUB, shopWeapon, player);

        //Assert that you cant buy the club
        assertEquals(statsPart.getBalance(),0);
        //Assert that you only have bought one club
        assertEquals(swordMap.size(), 1);
    }

    private void buyWeapon(StatsPart statsPart, WeaponImages weaponEnum, ShopWeapon shopWeapon, Player player) {
        if (statsPart.getBalance() >= shopWeapon.getPrice()) {
            statsPart.withdrawBalance(shopWeapon.getPrice());
            Weapon weapon = swordMap.get(weaponEnum);
            player.addWeapon(weapon);
            player.equipWeapon(weapon);
        }
    }
}