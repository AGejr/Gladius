package CommonWeapon;

public enum WeaponImages {
    STARTSWORD("Sword1.png"),
    GOLDSWORD("Sword2.png"),
    DIAMONDSWORD("Sword3.png"),
    CLUB("Sword4.png");

    public String path;

    WeaponImages(String path) {
        this.path = path;
    }
}

