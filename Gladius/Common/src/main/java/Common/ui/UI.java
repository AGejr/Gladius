package Common.ui;

import Common.data.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UI {

    public static void textBox(GameData gameData, String text, float x, float y, int width, int height) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(x - 25, y - 15, width, height);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x - 20, y - 10, width-10, height-10);
        shapeRenderer.end();

        text(gameData, text, x, y);
    }

    private static void text(GameData gameData, String text, float x, float y) {
        Stage stage = gameData.getStage();
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), new Color(Color.WHITE));
        Label label = new Label(text, labelStyle);
        label.setPosition(x, y);
        stage.addActor(label);
        stage.act();
    }

    // TODO: add fade out effect, based on duration
    public static void addCenteredPrompt(GameData gameData, String text, float scale, int duration) {
        float x = (float)(gameData.getDisplayWidth() / 2);
        float y = (float)(gameData.getDisplayHeight() / 2);
        Stage stage = gameData.getStage();
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("mc.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        int fontSize = 20;
        parameter.size = fontSize;
        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        freeTypeFontGenerator.dispose();
        Label label = new Label(text, new Label.LabelStyle(bitmapFont, new Color(Color.WHITE)));
        label.setFontScale(scale);
        x -= ((label.getPrefWidth())/2);
        y += ((label.getPrefHeight())/2);
        label.setPosition(x, y);
        stage.addActor(label);
        stage.act();
    }
}
