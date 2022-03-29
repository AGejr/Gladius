package Enemy;

import Common.data.Entity;

public class Enemy extends Entity {

    public Enemy(String texturePath, int radius) {
        super(texturePath, radius);
    }


    @Override
    public void initTexture(){
        super.initTexture();
        this.setRegion(0,0,96,96);
    }
}
