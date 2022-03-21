package Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Sprite {
    int balance;
    //PositionPart positionPart;
    //LifePart lifePart;

    public Player(Texture texture) {
        super(texture);
        this.balance = 0;
    }

    public Player(TextureRegion region) {
        super(region);
        this.balance = 0;
    }




}
