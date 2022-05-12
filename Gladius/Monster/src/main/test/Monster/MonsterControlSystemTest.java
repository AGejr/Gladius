package Monster;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import CommonMonster.Monster;
import Common.services.IEntityProcessingService;
import Common.services.IGamePluginService;
import CommonPlayer.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterControlSystemTest {
    /*
    IGamePluginService plugin;
    GameData gameData;
    World world;
    IEntityProcessingService controlSystem;
    Entity player;
    Entity monster;
    LifePart playerLife;
    AnimationPart animationPart;
    LifePart monsterLife;
    MovingPart monsterMove;

    @BeforeEach
    public void setUp() {
        gameData = new GameData();
        world = new World();
        plugin = new MonsterPlugin();
        plugin.start(gameData, world);
        controlSystem = new MonsterControlSystem();
        monster = world.getEntities(Monster.class).get(0);
        player = new Player("", 5);
        world.addEntity(player);
        playerLife = new LifePart(100);
        player.add(playerLife);
        animationPart = new AnimationPart();
        monsterLife = new LifePart(100);
        monsterMove = new MovingPart(3);
        monster.add(animationPart);
        monster.add(monsterMove);
        monster.add(monsterLife);
        monster.initTexture();
    }

    @Test
    public void isMonsterAttacking() {
        // Below is plan A. Plan B is using dependency injection to get WeaponService
        player.setX(30);
        player.setY(30);
        LifePart lifePart = player.getPart(LifePart.class);
        if (lifePart != null) {
            int healthBeforeAttack = lifePart.getLife();
            System.out.println("Health before: " + healthBeforeAttack);
            monster.setX(30);
            monster.setY(30);
            controlSystem.process(gameData, world);
            int healthAfterAttack = lifePart.getLife();
            System.out.println("Health after: " + healthAfterAttack);
            assertNotEquals(healthAfterAttack, healthBeforeAttack);
        }
    }

     */
}