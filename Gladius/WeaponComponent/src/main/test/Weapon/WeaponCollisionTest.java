package Weapon;

import Common.data.GameData;
import Common.data.World;
import CommonPlayer.Player;
import Common.data.Entity;
import CommonWeapon.Weapon;
import CommonEnemy.Enemy;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.data.entityparts.AnimationPart;
import Common.data.SoundData;
import CommonWeapon.WeaponImages;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static  org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponCollisionTest {
    World world;
    GameData gameData;
    WeaponCollision weaponCollision;
    Entity player;
    Entity weapon;
    Entity enemy;

    @BeforeEach
    public void setUp() throws Exception {
        world = new World();
        gameData = mock(GameData.class);
        weaponCollision = new WeaponCollision();
        player = new Player("", 1);
        player.add(new LifePart(300, Color.GREEN));
        player.add(new StatsPart(20,0, 0, 5, 0));
        weapon = new Weapon("Sword", 15, 8, 0, WeaponImages.STARTSWORD.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f,9.0f, 10.0f, player, 0.3f);
        enemy = new Enemy("", 20,0);
        enemy.add(new LifePart(100, Color.RED));
        enemy.add(new StatsPart(5,0, 0, 5, 0));
        ((Weapon) weapon).setOwner(player);
        world.addEntity(player);
        world.addEntity(enemy);
        world.addEntity(weapon);

        SoundData soundData = mock(SoundData.class);
        when(gameData.getSoundData()).thenReturn(soundData);
    }

    @Test
    public void process() {
        enemy.setX(300);
        enemy.setY(300);
        weapon.setX(20);
        weapon.setY(20);
        weapon.updatePolygonBoundariesPosition();
        enemy.updatePolygonBoundariesPosition();
        weaponCollision.process(gameData, world);
        // Asserting enemy doesn't get hit because they are too far from each other
        assertFalse(((Weapon) weapon).isEntityHit(enemy));
        enemy.setX(0);
        enemy.setY(0);
        weapon.updatePolygonBoundariesPosition();
        enemy.updatePolygonBoundariesPosition();
        weaponCollision.process(gameData, world);
        // Asserting enemy does get hit because they are close enough to each other
        assertTrue(((Weapon) weapon).isEntityHit(enemy));
    }

}