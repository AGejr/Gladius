package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
// import Common.data.entityparts.MovingPart;
// import Common.data.entityparts.PositionPart;
import Common.services.IEntityProcessingService;
import CommonWeapon.IWeaponUser;
//import CommonWeapon.Weapon;
import CommonWeapon.WeaponSPI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class WeaponControlSystem implements IEntityProcessingService, WeaponSPI {
    private int j = 0;
    private int counter = 0;
    private float rotationDegrees = 0f;
    @Override
    public void process(GameData gameData, World world) {
        /*
            TODO Make weapon follow player
         */

        for(Entity entity: world.getEntities(Sword.class)) {
            if(entity.getTexturePath() != null) {
                counter++;
                // float xPos = attacker.getX();
                // float yPos = attacker.getY();
                float deltaTime = Gdx.graphics.getDeltaTime();
                float lerp = 0.9f;
                // Weapon weaponEntity = ((IWeaponUser) attacker).getWeapon()
                if (counter < 15) {
                    entity.setAngle(rotationDegrees);
                    rotationDegrees -= 9f;
                }

                if (counter >= 15) {
                    ((Weapon) entity).removeWeaponTexture();
                    world.removeEntity(entity);
                    rotationDegrees = 0;
                    counter = 0;

                }
            }
        }


    }

    @Override
    public void attack(Entity attacker, GameData gameData, World world) {
        System.out.println("ATTACK");
        float xPos = attacker.getX();
        float yPos = attacker.getY();
        float deltaTime = Gdx.graphics.getDeltaTime();
        float lerp = 0.9f;
        // Weapon weaponEntity = ((IWeaponUser) attacker).getWeapon();
        Weapon weaponEntity = Sword.getSword();
        world.addEntity(weaponEntity);
        weaponEntity.setX(xPos + 20);
        weaponEntity.setY(yPos + 5);
        weaponEntity.setWeaponTexture();

        // process(gameData, world);
        /*
        Texture test = new Texture(weaponEntity.getTexturePath());
        Sprite testSprite = new Sprite(test);
        SpriteBatch testBatch = new SpriteBatch();
        testBatch.begin();
        if (counter % 10 == 0) {
            testSprite.rotate(rotationDegrees * deltaTime * lerp);
            testSprite.draw(testBatch, 100);
            rotationDegrees += 10;
        }
        testBatch.end();

         */



        if (counter == 80) {
            //weaponEntity.removeWeaponTexture();
            rotationDegrees = 0;
        }
        // weaponEntity.removeWeaponTexture();




        // TODO get attacker 'radians'
        // int radians = attacker.getRadians()
        /*
        Entity weapon = attacker.getWeapon();
        List<Entity> entitiesInRange = getEntitiesInRange(attacker, world);
        for (Entity entity: entitiesInRange) {
            Lifepart entityLifePart = entity.getPart(LifePart.class);
            float damageGiven = (attacker.getStats() * weapon.getDamage()) - attacker.getArmor();
            float newHealth = entityLifePart.getLife() - damageGiven;
            entityLifePart.setHealth(newHealth);
         */
    }

    //TODO change to check collision between weapon and enemy, if hit then remove HP
    private List<Entity> getEntitiesInRange(IWeaponUser attacker, World world) {
        Entity weapon = attacker.getWeapon();
        Entity attackerEntity = (Entity) attacker;
        // float radians = attackerEntity.getRadians();
        // The range for weapon is the radius
        float range = weapon.getRadius();
        // float attackArea = (float) ((radians / 2) * Math.pow(range, 2)); //Circular sector area
        return null;
    }


}
