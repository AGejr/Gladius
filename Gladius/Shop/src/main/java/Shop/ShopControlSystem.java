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
import java.util.HashMap;
import java.util.Map;

public class ShopControlSystem implements IEntityProcessingService {
    private boolean shopEntered = false;
    private final int tileSize = 16;
    private float cursorX = 100;
    private float cursorY = 290;
    private final int x1 = 100, x2 = 200, x3 = 300, x4 = 400, x5 = 500, x6 = 700;
    private final int y1 = 130, y2 = 290;
    private final int smallElixir = 5, largeElixir = 15, smallElixirCost = 100, largeElixirCost = 200;
    private Map<WeaponImages, Weapon> swordMap = new HashMap<>();
    /*
    Coordinates of sprites
    x1        x2        x3        x4        x5   x6
y2  Club                Diamond             Gold Stone
y1  StrElixir StrElixir DefElixir DefElixir Exit
    */

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            //Show balance
            StatsPart statsPart = player.getPart(StatsPart.class);
            UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, gameData.getMapHeight() / 2f - tileSize * 2);

            SpriteBatch batch = new SpriteBatch();
            File textureFile = new File("ShopItems.png");
            FileHandle fileHandle = new FileHandle(textureFile);
            Texture texture = new Texture(fileHandle);
            TextureRegion region = new TextureRegion(texture);
            batch.begin();
            region.setRegion(tileSize * 4, tileSize * 8, tileSize * 2, tileSize * 2);
            batch.draw(region, 0, gameData.getMapHeight() / 2f - tileSize * 3, tileSize * 4, tileSize * 4);
            batch.end();

            if (shopEntered) {
                drawShop(gameData, player);
                moveCursor(gameData);

                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                UI.box(gameData, shapeRenderer, cursorX, cursorY, 40, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
                UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, gameData.getMapHeight() / 2f - tileSize * 2);
                shapeRenderer.end();
                processBuy(gameData, player, statsPart);
            } else {
                if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
                    shopEntered = true;
                    MovingPart movingPart = player.getPart(MovingPart.class);
                    movingPart.setSpeed(0);
                }
            }

            for (Entity shop : world.getEntities(Shop.class)) {
                if (player.getY() > shop.getY() - shop.getTextureHeight() / 2f && player.getX() < shop.getX() + shop.getTextureWidth() && player.getY() < shop.getY() + shop.getTextureHeight()) {
                    if (!shopEntered) {
                        UI.textBox(gameData, "Hit enter to enter shop", gameData.getMapWidth() / 5f, gameData.getMapHeight() / 10f, 200, 55);
                    }
                }
            }
        }
    }

    /**
     * Validates where the cursor is and if the player has the balance to buy the upgrade.
     * @param statsPart To get the balance from the statsPart
     */
    private void processBuy(GameData gameData, Player player, StatsPart statsPart) {
        if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
            if (cursorX == x1 && cursorY == y2) {
                buyWeapon(statsPart, WeaponImages.CLUB, player);
            } else if (cursorX == x3 && cursorY == y2) {
                buyWeapon(statsPart, WeaponImages.DIAMONDSWORD, player);
            } else if (cursorX == x5 && cursorY == y2) {
                buyWeapon(statsPart, WeaponImages.GOLDSWORD, player);
            } else if (cursorX == x6 && cursorY == y2) {
                buyWeapon(statsPart, WeaponImages.STARTSWORD, player);
            } else if (cursorX == x1 && cursorY == y1) {
                if (statsPart.getBalance() >= largeElixirCost) {
                    statsPart.withdrawBalance(largeElixirCost);
                    statsPart.setAttack(statsPart.getAttack() + largeElixir);
                }
            } else if (cursorX == x2 && cursorY == y1) {
                if (statsPart.getBalance() >= smallElixirCost) {
                    statsPart.withdrawBalance(smallElixirCost);
                    statsPart.setAttack(statsPart.getAttack() + smallElixir);
                }
            } else if (cursorX == x3 && cursorY == y1) {
                if (statsPart.getBalance() >= largeElixirCost) {
                    statsPart.withdrawBalance(largeElixirCost);
                    statsPart.setDefence(statsPart.getDefence() + largeElixir);
                }
            } else if (cursorX == x4 && cursorY == y1) {
                if (statsPart.getBalance() >= smallElixirCost) {
                    statsPart.withdrawBalance(smallElixirCost);
                    statsPart.setDefence(statsPart.getDefence() + smallElixir);
                }
            } else if (cursorX == x5 && cursorY == y1) {
                shopEntered = false;
                MovingPart movingPart = player.getPart(MovingPart.class);
                movingPart.setSpeed(100);
            }
        }
    }

    private void buyWeapon(StatsPart statsPart, WeaponImages weaponEnum, Player player) {
        if (statsPart.getBalance() >= swordMap.get(weaponEnum).getPrice()) {
            statsPart.withdrawBalance(swordMap.get(weaponEnum).getPrice());
            Weapon weapon = swordMap.get(weaponEnum);
            player.addWeapon(weapon);
            player.equipWeapon(weapon);
        }
    }

    /**
     * Moves the cursor around by x and y. Moves the cursor a fixed lot when pressing a key.
     */
    private void moveCursor(GameData gameData) {
        if (gameData.getKeys().isPressed(GameKeys.RIGHT)) {
            if (cursorX < x6 && cursorY == y2) {
                cursorX += x2;
            } else if (cursorX < x5 && cursorY == y1){
                cursorX += x1;
            }
        } else if (gameData.getKeys().isPressed(GameKeys.LEFT)) {
            if (cursorX > x1 && cursorY == y2) {
                cursorX -= x2;
            } else if (cursorX > x1 && cursorY == y1) {
                cursorX -= x1;
            }
        } else if (gameData.getKeys().isPressed(GameKeys.DOWN)) {
            if (cursorY > y1) {
                cursorY -= y1 + 30;
                if (cursorX == x3) {
                    cursorX = x2;
                } else if (cursorX == x5) {
                    cursorX = x4;
                } else if (cursorX == x6) {
                    cursorX = x5;
                }
            }
        } else if (gameData.getKeys().isPressed(GameKeys.UP)) {
            if (cursorY < y2) {
                cursorY += y1 + 30;
                if (cursorX == x2) {
                    cursorX = x3;
                } else if (cursorX == x3) {
                    cursorX = x5;
                } else if (cursorX == x4) {
                    cursorX = x6;
                } else if (cursorX == x5) {
                    cursorX = x6;
                }
            }
        }
    }

    /**
     * Draws all the content in the shop
     * @param gameData Gamedata to get map size
     * @param player The entity player is used to initialize the weapon
     */
    private void drawShop(GameData gameData, Player player) {
        // Drawing the background of the shop
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        UI.box(gameData, shapeRenderer, 0, 0, gameData.getMapWidth() / 2, gameData.getMapHeight() / 2, new Color(162 / 255f, 124 / 255f, 91 / 255f, 1)); //lightBrown
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 2f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1)); //Dark Brown
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 4f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 8f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
        shapeRenderer.end();

        //initializing the image
        SpriteBatch batch = new SpriteBatch();
        File textureFile = new File("ShopItems.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        TextureRegion region = new TextureRegion(texture);
        batch.begin();

        // Tile size is multiplied with its place in the spriteMap
        region.setRegion(tileSize * 2, tileSize * 10, tileSize, tileSize);

        batch.draw(region, x1, y1 + 30, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 10, tileSize, tileSize);
        batch.draw(region, x2, y1 + 30, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 3, tileSize * 10, tileSize, tileSize);
        batch.draw(region, x3, y1 + 30, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 5, tileSize * 10, tileSize, tileSize);
        batch.draw(region, x4, y1 + 30, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 4, tileSize * 8, tileSize * 2, tileSize * 2);
        batch.draw(region, 0, gameData.getMapHeight() / 2f - tileSize * 3, tileSize * 4, tileSize * 4);

        region.setRegion(tileSize * 8, tileSize * 10, tileSize * 2, tileSize);
        batch.draw(region, gameData.getMapWidth()/4f - tileSize * 5, gameData.getMapHeight() / 2f - tileSize * 5, tileSize * 10, tileSize * 5);


        drawSword(gameData, player, batch, WeaponImages.STARTSWORD, 25, 8, 10, x6, 0);
        drawSword(gameData, player, batch, WeaponImages.GOLDSWORD, 40, 10, 12, x5, 100);
        drawSword(gameData, player, batch, WeaponImages.DIAMONDSWORD, 50, 14, 14, x3, 200);
        drawSword(gameData, player, batch, WeaponImages.CLUB, 50, 14, 14, x1, 300);

        batch.end();

        int offset = 20;
        UI.text(gameData,"Strength Elixir", x1, y1);
        UI.text(gameData, String.valueOf(largeElixirCost), x1, y1 - offset);
        UI.text(gameData,"Strength Elixir", x2, y1);
        UI.text(gameData, String.valueOf(smallElixirCost), x2, y1 - offset);
        UI.text(gameData,"Defence Elixir", x3, y1);
        UI.text(gameData, String.valueOf(largeElixirCost), x3, y1 - offset);
        UI.text(gameData,"Defence Elixir", x4, y1);
        UI.text(gameData, String.valueOf(smallElixirCost), x4, y1 - offset);
        UI.text(gameData,"Exit", x5, y1);
        UI.text(gameData,"Stone Sword", x6, y2);
        UI.text(gameData, String.valueOf(swordMap.get(WeaponImages.STARTSWORD).getPrice()), x6, y2 - offset);
        UI.text(gameData,"Gold Sword", x5, y2);
        UI.text(gameData, String.valueOf(swordMap.get(WeaponImages.GOLDSWORD).getPrice()), x5, y2 - offset);
        UI.text(gameData,"Diamond Sword", x3, y2);
        UI.text(gameData, String.valueOf(swordMap.get(WeaponImages.DIAMONDSWORD).getPrice()), x3, y2 - offset);
        UI.text(gameData,"Club", x1, y2);
        UI.text(gameData, String.valueOf(swordMap.get(WeaponImages.CLUB).getPrice()), x1, y2 - offset);
    }

    /**
     * Initialize the sword to the map and draw the sword to the shop.
     */
    private void drawSword(GameData gameData, Player player, SpriteBatch batch, WeaponImages weaponEnum, int damage, int weight, int range, float x, int price) {
        Weapon sword = new Weapon("Sword", damage, weight, range, weaponEnum.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f, 10.0f, player, price);
        sword.setWeaponTexture();
        sword.initTexture();
        batch.draw((Entity) sword, x, y2, 0, 0, sword.getTextureWidth(), sword.getTextureHeight(), 1, 1, sword.getAngle());
        swordMap.put(weaponEnum, sword);
    }
}
