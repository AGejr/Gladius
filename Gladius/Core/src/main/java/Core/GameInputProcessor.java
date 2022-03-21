package Core;

import Common.data.GameData;
import Common.data.GameKeys;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int k) {
        if(k == Input.Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, true);
        }
        if(k == Input.Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if(k == Input.Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if(k == Input.Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if(k == Input.Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if(k == Input.Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, true);
        }
        if(k == Input.Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if(k == Input.Keys.SHIFT_LEFT || k == Input.Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if(k == Input.Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, false);
        }
        if(k == Input.Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if(k == Input.Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if(k == Input.Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if(k == Input.Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if(k == Input.Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, false);
        }
        if(k == Input.Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if(k == Input.Keys.SHIFT_LEFT || k == Input.Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, false);
        }
        return true;
    }
}

