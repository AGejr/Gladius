package Common.ui;

import Common.data.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Text {

    private String text;
    private String fontFilename = "mc.otf";
    private Color textColor = new Color(Color.WHITE);
    /**
     * Font size determines the resolution of the text
     */
    private int fontSize;
    /**
     * The scale of the font on screen
     * (Fonts with better resolution can be scaled more)
     */
    private int scale;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontGeneratorParameters;
    private Label label;
    /**
     * Duration is the amount seconds that the text should be displayed.
     * (-1 is used when text should have infinite display time)
     */
    private int duration;
    /**
     * The amount of game ticks that the text has existed for
     */
    private int gameTicks = 0;
    private boolean isVisible = true;

    /**
     * Used for permanently displayed text.
     * @param text
     * @param scale
     * The size of the text onscreen
     * @param fontSize
     * The size at which the text should be rendered
     */
    public Text(String text, int scale, int fontSize) {
        this.text = text;
        this.scale = scale;
        this.fontSize = fontSize;
        this.duration = -1;
        generateText();
    }

    /**
     * Used for text that needs to fade out
     * @param text
     * @param scale
     * The size of the text onscreen
     * @param fontSize
     * The size at which the text should be rendered
     * @param duration
     * The text is removed after duration (seconds)
     */
    public Text(String text,int scale, int fontSize, int duration) {
        this.text = text;
        this.scale = scale;
        this.fontSize = fontSize;
        this.duration = duration;
        generateText();
    }

    private void generateText(){
        FileHandle fontFileHandle = Gdx.files.internal(this.fontFilename);
        fontGenerator = new FreeTypeFontGenerator(fontFileHandle);
        fontGeneratorParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontGeneratorParameters.size = fontSize;
        BitmapFont bitmapFont = fontGenerator.generateFont(fontGeneratorParameters);
        fontGenerator.dispose();
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, textColor);
        label = new Label(text, labelStyle);
        label.setFontScale(scale);
    }

    public void alignScreenCenter(GameData gameData){
        float displayCenterX = (float)(gameData.getDisplayWidth() / 2);
        float displayCenterY = (float)(gameData.getDisplayHeight() / 2);
        float labelSizeX = label.getPrefWidth();
        float labelSizeY = label.getPrefHeight();
        float x = displayCenterX - (labelSizeX / 2);
        float y = displayCenterY + (labelSizeY / 2);
        label.setPosition(x, y);
    }

    public void setPosition(float x, float y){
        label.setPosition(x,y);
    }

    public void updateText(){
        if (duration < 0){
            return;
        }
        // This assumes that the game is running at 60 game cycles per second
        float timeLeft = duration - (gameTicks / 60.f);
        float opacity = 1.f;
        // Decrease opacity to apply fadeout effect when the text is in the last second of its duration
        if (timeLeft < 1.f) {
            opacity = timeLeft;
        }
        float red = textColor.r;
        float green = textColor.g;
        float blue = textColor.b;
        label.getStyle().fontColor = new Color(red,green,blue,opacity);
        gameTicks++;
        if (timeLeft < 0) {
            isVisible = false;
        }
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Label getLabel() {
        return label;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
