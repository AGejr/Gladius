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

    public static void add_text(GameData gameData, String text,float x, float y, float scale) {
        Stage stage = gameData.getStage();
        // TODO: load font
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/mc.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        freeTypeFontGenerator.dispose();
        Label label = new Label(text, new Label.LabelStyle(bitmapFont, new Color(Color.WHITE)));
        label.setFontScale(scale);
        label.setPosition(x, y);
        stage.addActor(label);
        stage.act();
    }
}
