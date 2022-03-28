package Player;

import Common.data.Entity;

public class Player extends Entity {
    int balance;

    public Player(String texturePath,int radius) {
        super(texturePath,radius);
        this.balance = 0;
    }


}
