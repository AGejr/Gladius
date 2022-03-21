package Player;

import Common.data.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity {
    int balance;

    public Player(Texture texture, int radius) {
        super(texture, radius);
        this.balance = 0;
    }

    public Player(TextureRegion region, int radius) {
        super(region, radius);
        this.balance = 0;
    }


}
