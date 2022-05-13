package Common.ui;

import Common.data.GameData;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

public class UI {

    /**
     * Used for time-based / permanent text
     * such as prompts or menu screen
     */
    public static ArrayList<Text> textList = new ArrayList<>();

    public static void textBox(GameData gameData, String text, float x, float y, int width, int height) {
        text(gameData, text, x, y);
    }

    public static void text(GameData gameData, String text, float x, float y) {
        Stage stage = gameData.getStage();
        stage.clear();
        BitmapFont dsa = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(dsa, new Color(Color.WHITE));
        Label label = new Label(text, labelStyle);
        label.setPosition(x, y);
        stage.addActor(label);
        stage.act();
        stage.draw();
    }

    public static void box(GameData gameData, ShapeRenderer shapeRenderer, float x, float y, int width, int height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
    }

    /**
     * Draws all the texts in this.textList
     */
    public static void draw(GameData gameData){
        Stage stage = gameData.getStage();
        ArrayList<Text> removeText = new ArrayList<>();
        for (Text text : textList) {
            stage.addActor(text.getLabel());
            text.updateText();
            if (!text.isVisible()) {
                removeText.add(text);
            }
        }
        for (Text text : removeText) {
            textList.remove(text);
        }
        stage.act();
        stage.draw();
    }

    /**
     * Add text to the UI
     * (Use this for persistent text which needs effects)
     *
     * @param text
     */
    public static void addText(Text text) {
        textList.add(text);
    }

    public static void removeText(Text text) {
        textList.remove(text);
    }
}

