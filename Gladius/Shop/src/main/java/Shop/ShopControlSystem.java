package Shop;

import Common.data.*;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityProcessingService;
import Common.ui.Text;
import Common.ui.UI;
import CommonPlayer.Player;
import CommonWeapon.Weapon;
import CommonWeapon.WeaponImages;
import Shop.ShopItems.ShopElixir;
import Shop.ShopItems.ShopWeapon;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopControlSystem implements IEntityProcessingService {
    private boolean shopEntered = false;
    private final int tileSize = 16;
    private float cursorX = 100;
    private float cursorY = 290;
    private Map<WeaponImages, Weapon> swordMap = new HashMap<>();
    private List<ShopElixir> shopElixirs;
    private List<ShopWeapon> shopWeapons;
    private Map<ShopWeapon,List<Text>> weaponTextMap;
    private Map<ShopElixir,List<Text>> elixirTextMap;
    private List<Text> ownedTexts = new ArrayList<>();
    private boolean weaponTextAdded = false;
    private boolean elixirTextAdded = false;
    private boolean textGenerated = false;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            boolean isInRange = false;
            for (Entity entity2 : world.getEntities(Shop.class)) {
                Shop shop = (Shop) entity2;
                shopElixirs = shop.getShopElixirs();
                shopWeapons = shop.getShopWeapons();
                weaponTextMap = shop.getWeaponTextMap();
                elixirTextMap = shop.getElixirTextMap();
                if(!textGenerated) {
                    for(ShopElixir elixir : shopElixirs) {
                        for (Text text : elixirTextMap.get(elixir)) {
                            text.generateText();
                        }
                    }
                    for(ShopWeapon weapon : shopWeapons) {
                        for (Text text : weaponTextMap.get(weapon)) {
                            text.generateText();
                        }
                    }

                    textGenerated = true;
                }
                isInRange = player.getY() > shop.getY() - shop.getTextureHeight() / 2f && player.getX() < shop.getX() + shop.getTextureWidth() && player.getY() < shop.getY() + shop.getTextureHeight();
                if (!shopEntered) {
                    if (isInRange) {
                        UI.textBox(gameData, "Hit enter to enter shop", gameData.getMapWidth() / 5f, gameData.getMapHeight() / 10f, 200, 55);
                    }

                }
            }
            //Show balance when in map
            StatsPart statsPart = player.getPart(StatsPart.class);
            UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, gameData.getMapHeight() / 2f - tileSize * 2);
            if (shopEntered) {
                drawShopInterior(gameData, player, statsPart);
                moveCursor(gameData, player);
                processBuy(gameData, player, statsPart);
                //Show balance when in shop
                UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, gameData.getMapHeight() / 2f - tileSize * 2);
            } else if (isInRange){
                if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
                    shopEntered = true;
                    MovingPart movingPart = player.getPart(MovingPart.class);
                    movingPart.setSpeed(0);
                    gameData.getSoundData().playSound(SoundData.SOUND.INTERACT);
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
            for (ShopElixir shopElixir: shopElixirs) {
                if (cursorX == shopElixir.getX() && cursorY == shopElixir.getY()) {
                    if (statsPart.getBalance() >= shopElixir.getPrice()) {
                        statsPart.withdrawBalance(shopElixir.getPrice());
                        if (shopElixir.getDescription().equals("Strength Elixir")) {
                            statsPart.setAttack(statsPart.getAttack() + shopElixir.getStatIncrease());
                        } else {
                            statsPart.setDefence(statsPart.getDefence() + shopElixir.getStatIncrease());
                        }
                        Text text = new Text("Rebuy", 1, 10, -1);
                        text.setPosition(shopElixir.getX(), shopElixir.getY() - 20);
                        UI.removeText(elixirTextMap.get(shopElixir).get(1));
                        UI.addText(text);
                        ownedTexts.add(text);

                        gameData.getSoundData().playSound(SoundData.SOUND.BUY);
                    }
                }
            }
            for (ShopWeapon shopWeapon : shopWeapons) {
                if (cursorX == shopWeapon.getX() && cursorY == shopWeapon.getY()) {
                    if (player.hasOwnedWeapon(shopWeapon.getWeaponEnum())) {
                        Weapon weapon = swordMap.get(shopWeapon.getWeaponEnum());
                        player.addWeapon(weapon);
                        player.equipWeapon(weapon);
                        gameData.getSoundData().playSound(SoundData.SOUND.INTERACT);
                    } else {
                        // Checks the players balance and adds the weapon if the player can afford
                        if (statsPart.getBalance() >= shopWeapon.getPrice()) {
                            statsPart.withdrawBalance(shopWeapon.getPrice());
                            Weapon weapon = swordMap.get(shopWeapon.getWeaponEnum());
                            player.addWeapon(weapon);
                            player.equipWeapon(weapon);
                            shopWeapon.setOwned(true);
                            Text text = new Text("Owned", 1, 10, -1);
                            text.setPosition(shopWeapon.getX(), shopWeapon.getY() - 20);
                            UI.removeText(weaponTextMap.get(shopWeapon).get(1));
                            UI.addText(text);
                            ownedTexts.add(text);

                            gameData.getSoundData().playSound(SoundData.SOUND.BUY);
                        }
                    }
                }
            }
            //If the cursor is on the exit coordinates it will exit and speed will be normal
            if (cursorX == 500 && cursorY == 130) {
                shopEntered = false;
                MovingPart movingPart = player.getPart(MovingPart.class);
                movingPart.setSpeed(100);
                for (ShopWeapon weapon : shopWeapons) {
                    for (Text text : weaponTextMap.get(weapon)) {
                        UI.removeText(text);
                    }
                }
                for (ShopElixir elixir : shopElixirs) {
                    for (Text text : elixirTextMap.get(elixir)) {
                        UI.removeText(text);
                    }
                }
                weaponTextAdded = false;
                elixirTextAdded = false;
                for (Text text : ownedTexts) {
                    UI.removeText(text);
                }
                ownedTexts.clear();
            }
        }
    }

    /**
     * Draws all the content in the shop
     * @param gameData Gamedata to get map size
     * @param player The entity player is used to initialize the weapon
     */
    private void drawShopInterior(GameData gameData, Player player, StatsPart statsPart) {
        // Drawing the background of the shop
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        UI.box(gameData, shapeRenderer, 0, 0, gameData.getMapWidth() / 2, gameData.getMapHeight() / 2, new Color(162 / 255f, 124 / 255f, 91 / 255f, 1)); //lightBrown
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 2f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1)); //Dark Brown
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 4f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
        UI.box(gameData, shapeRenderer, 0, gameData.getMapHeight() / 8f, gameData.getMapWidth() / 2, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));

        UI.box(gameData, shapeRenderer, cursorX, cursorY, 40, 5, new Color(66 / 255f, 40 / 255f, 14 / 255f, 1));
        UI.text(gameData, String.valueOf(statsPart.getBalance()), tileSize * 3, gameData.getMapHeight() / 2f - tileSize * 2);
        shapeRenderer.end();
        shapeRenderer.dispose();

        //initializing the image
        SpriteBatch batch = new SpriteBatch();
        File textureFile = new File("ShopItems.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        TextureRegion region = new TextureRegion(texture);
        batch.begin();

//         Tile size is multiplied with its place in the spriteMap
        for (ShopElixir shopElixir : shopElixirs) {
            region.setRegion(shopElixir.getSpriteMapX(), shopElixir.getSpriteMapY(), tileSize, tileSize);
            batch.draw(region, shopElixir.getX(), shopElixir.getY() + 30, tileSize * 4, tileSize * 4);
        }

        for (ShopWeapon shopWeapon : shopWeapons) {
            drawSword(player, batch, shopWeapon.getWeaponEnum(), shopWeapon.getDamage(), shopWeapon.getWeight(), shopWeapon.getRange(), shopWeapon.getX(), shopWeapon.getY());
        }

        region.setRegion(tileSize * 8, tileSize * 10, tileSize * 2, tileSize);
        batch.draw(region, gameData.getMapWidth()/4f - tileSize * 5, gameData.getMapHeight() / 2f - tileSize * 5, tileSize * 10, tileSize * 5);

        batch.end();
        batch.dispose();

        int offset = 20;
        if(!elixirTextAdded) {
            for (ShopElixir elixir : shopElixirs) {
                List<Text> textList = elixirTextMap.get(elixir);
                for (int i = 0; i < textList.size(); i++) {
                    Text text = textList.get(i);
                    text.setPosition(elixir.getX(), elixir.getY() - offset * i);

                    UI.addText(text);
                }
            }
            elixirTextAdded = true;
        }
        if (!weaponTextAdded) {
            for (ShopWeapon shopWeapon : shopWeapons) {
                List<Text> textList = weaponTextMap.get(shopWeapon);
                for (int i = 0; i < textList.size(); i++) {
                    Text text = textList.get(i);
                    if (((shopWeapon.isOwned() || player.hasOwnedWeapon(shopWeapon.getWeaponEnum()) || shopWeapon.getPrice() == 0) && i == 1)) {
                        Text test = new Text("Owned", 1, 10, -1);
                        test.setPosition(shopWeapon.getX(), shopWeapon.getY() - offset * i);
                        UI.addText(test);
                        ownedTexts.add(test);
                        continue;
                    }
                    text.setPosition(shopWeapon.getX(), shopWeapon.getY() - offset * i);
                    UI.addText(text);
                }
            }
            weaponTextAdded = true;
        }
        UI.text(gameData,"Exit", 500, 130);
    }

    /**
     * Initialize the sword to the map and draw the sword to the shop.
     */
    private void drawSword(Player player, SpriteBatch batch, WeaponImages weaponEnum, int damage, int weight, int range, float x, float y) {
        Weapon sword = new Weapon("Sword", damage, weight, range, weaponEnum.path, 36, 146, 0.9f, 0.9f, 0, 20.0f, 9.0f, 20.0f,9.0f, 10.0f, player, 0.3f);
        sword.setWeaponTexture();
        sword.initTexture();
        batch.draw((Entity) sword, x, y, 0, 0, sword.getTextureWidth(), sword.getTextureHeight(), 1, 1, sword.getAngle());
        swordMap.put(weaponEnum, sword);
    }


    /**
     * Moves the cursor around by x and y. Moves the cursor a fixed lot when pressing a key.
     */
    private void moveCursor(GameData gameData, Player player) {
        final int x1 = 100, x2 = 200, x3 = 300, x4 = 400, x5 = 500, x6 = 700;
        final int y1 = 130, y2 = 290;
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
}
