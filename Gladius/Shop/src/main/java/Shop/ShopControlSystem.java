package Shop;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.GameKeys;
import Common.data.World;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityProcessingService;
import Common.ui.UI;
import CommonPlayer.Player;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;

public class ShopControlSystem implements IEntityProcessingService {
    private boolean shopEntered = false;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            for (Entity shop : world.getEntities(Shop.class)) {
                if (player.getY() > shop.getY() - shop.getTextureHeight() / 2f && player.getX() < shop.getX() + shop.getTextureWidth() && player.getY() < shop.getY() + shop.getTextureHeight()) {
                    if (!shopEntered) {
                        UI.textBox(gameData, "Hit space to enter shop", gameData.getMapWidth() / 5f, gameData.getMapHeight() / 10f, 200, 55);
                    } else {
                        drawShop(gameData);
                        StatsPart statsPart = player.getPart(StatsPart.class);

                    }
                    if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
                        if (shopEntered) {
                            shopEntered = false;
                        } else {
                            shopEntered = true;
                        }
                    }

                }
            }
        }
    }

    private void drawShop(GameData gameData) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //Background
        shapeRenderer.setColor(162/255f,124/255f,91/255f,1);
        shapeRenderer.rect(0, 0, gameData.getMapWidth()/2f, gameData.getMapHeight()/2f);
        //Shelf
        shapeRenderer.setColor(66/255f,40/255f,14/255f,1);
        shapeRenderer.rect(0, gameData.getMapHeight()/2f, gameData.getMapWidth()/2f, 5);
        shapeRenderer.rect(0, gameData.getMapHeight()/4f, gameData.getMapWidth()/2f, 5);
        shapeRenderer.rect(0, gameData.getMapHeight()/8f, gameData.getMapWidth()/2f, 5);
        shapeRenderer.end();

        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        File textureFile = new File("ShopItems.png");
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        TextureRegion region = new TextureRegion(texture);
        region.setRegion(64,160,16, 16);
        batch.draw(region, gameData.getMapWidth()/20f, gameData.getMapHeight()/8f,112,112);

        region.setRegion(80,160,16, 16);
        batch.draw(region, gameData.getMapWidth()/10f, gameData.getMapHeight()/8f,112,112);

        region.setRegion(42,144,32, 32);
        batch.draw(region, 0, gameData.getMapHeight()/4f - 32,112,112);
        batch.end();
    }
}
