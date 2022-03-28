package CommonPlayer;

import Common.data.Entity;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

public class Player extends Entity {
    int balance;

    public Player(String texturePath, int radius) {
        super(texturePath,radius);
        this.balance = 0;
    }

    @Override
    public void initTexture(){
        super.initTexture();
        this.setRegion(0,0,32,32);
    }

}
