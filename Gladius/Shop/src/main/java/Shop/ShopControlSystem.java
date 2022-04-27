package Shop;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.GameKeys;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityProcessingService;
import Common.ui.UI;
import CommonPlayer.Player;
import CommonWeapon.Weapon;
import CommonWeapon.WeaponImages;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ShopControlSystem implements IEntityProcessingService {
    private boolean shopEntered = false;
    private final int tileSize = 16;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            if (shopEntered) {
                stopMoving(player.getPart(MovingPart.class));
                drawShop(gameData, player);
                StatsPart statsPart = player.getPart(StatsPart.class);
                UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, gameData.getMapHeight() / 2f - tileSize * 2);
            }
            for (Entity shop : world.getEntities(Shop.class)) {
                if (player.getY() > shop.getY() - shop.getTextureHeight() / 2f && player.getX() < shop.getX() + shop.getTextureWidth() && player.getY() < shop.getY() + shop.getTextureHeight()) {
                    if (!shopEntered) {
                        UI.textBox(gameData, "Hit space to enter shop", gameData.getMapWidth() / 5f, gameData.getMapHeight() / 10f, 200, 55);
                    }
                    if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
                        if (!shopEntered) {
                            shopEntered = true;
                        }
                    }
                }
            }
        }
    }

    private void stopMoving(MovingPart movingPart) {
        movingPart.setRight(false);
        movingPart.setLeft(false);
        movingPart.setDown(false);
        movingPart.setUp(false);
    }

    private void drawShop(GameData gameData, Player player) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //Background
        shapeRenderer.setColor(162 / 255f, 124 / 255f, 91 / 255f, 1);
        shapeRenderer.rect(0, 0, gameData.getMapWidth() / 2f, gameData.getMapHeight() / 2f);
        //Shelf
        shapeRenderer.setColor(66 / 255f, 40 / 255f, 14 / 255f, 1);
        shapeRenderer.rect(0, gameData.getMapHeight() / 2f, gameData.getMapWidth() / 2f, 5);
        shapeRenderer.rect(0, gameData.getMapHeight() / 4f, gameData.getMapWidth() / 2f, 5);
        shapeRenderer.rect(0, gameData.getMapHeight() / 8f, gameData.getMapWidth() / 2f, 5);
        shapeRenderer.end();

        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        File textureFile = new File("ShopItems.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        TextureRegion region = new TextureRegion(texture);

        region.setRegion(tileSize * 2, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (gameData.getMapWidth() / 2f) - 400f, gameData.getMapHeight() / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (gameData.getMapWidth() / 2f) - 500f, gameData.getMapHeight() / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 3, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (gameData.getMapWidth() / 2f) - 600f, gameData.getMapHeight() / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 5, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (gameData.getMapWidth() / 2f) - 700f, gameData.getMapHeight() / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 8, tileSize * 2, tileSize * 2);
        batch.draw(region, 0, gameData.getMapHeight() / 2f - tileSize * 3, tileSize * 4, tileSize * 4);

        drawSword(gameData, player, batch, WeaponImages.SWORD, 25, 8, 10, 300f);
        drawSword(gameData, player, batch, WeaponImages.SWORD2, 40, 10, 12, 500f);
        drawSword(gameData, player, batch, WeaponImages.SWORD3, 50, 14, 14, 700f);

        batch.end();
    }

    private void drawSword(GameData gameData, Player player, SpriteBatch batch, WeaponImages weaponEnum, int damage, int weight, int range, float x) {
        Weapon sword = new Weapon("Sword", damage, weight, range, weaponEnum.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f, 10.0f, player);
        sword.setWeaponTexture();
        sword.initTexture();
        batch.draw((Entity) sword, (gameData.getMapWidth() / 2f) - x, gameData.getMapHeight() / 4f, 0, 0, sword.getTextureWidth(), sword.getTextureHeight(), sword.getScaling(), sword.getScaling(), sword.getAngle());
    }
}
