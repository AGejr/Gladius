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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;

public class ShopControlSystem implements IEntityProcessingService {
    private boolean shopEntered = false;
    private final int tileSize = 16;
    private int mapWidth;
    private float mapHeight;
    private float cursorX = 100;
    private float cursorY = 280;

    @Override
    public void process(GameData gameData, World world) {
        mapWidth = gameData.getMapWidth();
        mapHeight = gameData.getMapHeight();
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            if (shopEntered) {
                stopMoving(player.getPart(MovingPart.class));
                drawShop(gameData, player);
                moveCursor(gameData);

                //Show balance
                StatsPart statsPart = player.getPart(StatsPart.class);
                UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, mapHeight / 2f - tileSize * 2);
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                UI.box(gameData, shapeRenderer, cursorX, cursorY, 40, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
                shapeRenderer.end();

            }
            for (Entity shop : world.getEntities(Shop.class)) {
                if (player.getY() > shop.getY() - shop.getTextureHeight() / 2f && player.getX() < shop.getX() + shop.getTextureWidth() && player.getY() < shop.getY() + shop.getTextureHeight()) {
                    if (!shopEntered) {
                        UI.textBox(gameData, "Hit space to enter shop", mapWidth / 5f, mapHeight / 10f, 200, 55);
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

    private void moveCursor(GameData gameData) {
        if (gameData.getKeys().isPressed(GameKeys.RIGHT)) {
            if (cursorX < 700 && cursorY == 280) {
                cursorX += 200;
            } else if (cursorX < 500 && cursorY == 120){
                cursorX += 100;
            }
        } else if (gameData.getKeys().isPressed(GameKeys.LEFT)) {
            if (cursorX > 100 && cursorY == 280) {
                cursorX -= 200;
            } else if (cursorX > 100 && cursorY == 120) {
                cursorX -= 100;
            }
        } else if (gameData.getKeys().isPressed(GameKeys.DOWN)) {
            if (cursorY > 120) {
                cursorY -= 160;
                if (cursorX == 300) {
                    cursorX = 300;
                } else if (cursorX == 500) {
                    cursorX = 400;
                } else if (cursorX == 700) {
                    cursorX = 500;
                }
            }
        } else if (gameData.getKeys().isPressed(GameKeys.UP)) {
            if (cursorY < 280) {
                cursorY += 160;
                if (cursorX == 200) {
                    cursorX = 300;
                } else if (cursorX == 300) {
                    cursorX = 500;
                } else if (cursorX == 400) {
                    cursorX = 700;
                } else if (cursorX == 500) {
                    cursorX = 700;
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
        UI.box(gameData, shapeRenderer, 0, 0, gameData.getMapWidth() / 2, gameData.getMapHeight() / 2, new Color(162 / 255f, 124 / 255f, 91 / 255f, 1)); //lightBrown
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 2f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1)); //Dark Brown
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 4f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 8f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
        shapeRenderer.end();


        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        File textureFile = new File("ShopItems.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        TextureRegion region = new TextureRegion(texture);

        region.setRegion(tileSize * 2, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (mapWidth / 2f) - 400f, mapHeight / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (mapWidth / 2f) - 500f, mapHeight / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 3, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (mapWidth / 2f) - 600f, mapHeight / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 5, tileSize * 10, tileSize, tileSize);
        batch.draw(region, (mapWidth / 2f) - 700f, mapHeight / 8f, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 8, tileSize * 2, tileSize * 2);
        batch.draw(region, 0, mapHeight / 2f - tileSize * 3, tileSize * 4, tileSize * 4);

        drawSword(gameData, player, batch, WeaponImages.SWORD, 25, 8, 10, 100f);
        drawSword(gameData, player, batch, WeaponImages.SWORD2, 40, 10, 12, 300f);
        drawSword(gameData, player, batch, WeaponImages.SWORD3, 50, 14, 14, 500f);
        drawSword(gameData, player, batch, WeaponImages.SWORD4, 50, 14, 14, 700f);

        batch.end();

        UI.text(gameData,"Strength Elixir",(mapWidth / 2f) - 400f, mapHeight / 8f - 30f);
        UI.text(gameData,"Strength Elixir",(mapWidth / 2f) - 500f, mapHeight / 8f - 30f);
        UI.text(gameData,"Defence Elixir",(mapWidth / 2f) - 600f, mapHeight / 8f - 30f);
        UI.text(gameData,"Defence Elixir",(mapWidth / 2f) - 700f, mapHeight / 8f - 30f);
        UI.text(gameData,"Exit",(mapWidth / 2f) - 300f, mapHeight / 8f - 30f);
        UI.text(gameData,"Stone Sword",(mapWidth / 2f) - 100f, mapHeight / 4f - 30f);
        UI.text(gameData,"Gold Sword",(mapWidth / 2f) - 300f, mapHeight / 4f - 30f);
        UI.text(gameData,"Diamond Sword",(mapWidth / 2f) - 500f, mapHeight / 4f - 30f);
        UI.text(gameData,"Club",(mapWidth / 2f) - 700f, mapHeight / 4f - 30f);
    }

    private void drawSword(GameData gameData, Player player, SpriteBatch batch, WeaponImages weaponEnum, int damage, int weight, int range, float x) {
        Weapon sword = new Weapon("Sword", damage, weight, range, weaponEnum.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f, 10.0f, player);
        sword.setWeaponTexture();
        sword.initTexture();
        batch.draw((Entity) sword, (gameData.getMapWidth() / 2f) - x, mapHeight / 4f, 0, 0, sword.getTextureWidth(), sword.getTextureHeight(), sword.getScaling(), sword.getScaling(), sword.getAngle());
    }
}
