package Enemy;

import Common.data.Entity;

public class Enemy extends Entity {
    int balance;

    public Enemy(String texturePath, int radius) {
        super(texturePath, radius);
        this.balance = 0;
    }

    @Override
    public void initTexture(){
        super.initTexture();
        this.setRegion(0,0,96,96);
    }
}
