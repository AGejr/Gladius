package Shop.ShopItems;

public class ShopElixir {
    private String description;
    private int x;
    private int y;
    private int price;
    private int spriteMapX;
    private int spriteMapY;
    private int statIncrease;

    public ShopElixir(String description, int x, int y, int price, int spriteMapX, int spriteMapY, int statIncrease) {
        this.description = description;
        this.x = x;
        this.y = y;
        this.price = price;
        this.spriteMapX = spriteMapX;
        this.spriteMapY = spriteMapY;
        this.statIncrease = statIncrease;
    }

    public String getDescription() {
        return description;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPrice() {
        return price;
    }

    public int getSpriteMapX() {
        return spriteMapX;
    }

    public int getSpriteMapY() {
        return spriteMapY;
    }

    public int getStatIncrease() {
        return statIncrease;
    }
}
