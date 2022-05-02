package CommonWeapon;
import Common.data.Entity;
import Common.tools.FileLoader;

import java.util.List;
import java.util.ArrayList;
public class Weapon extends Entity {
    private String name;
    private int damage;
    private float weight;
    private final String texturePath;
    private Entity owner; //The owner of the weapon, aka the entity holding the weapon ex player. Used to stop hitting the entity itself and spawn positions
    private List<Entity> hitEntityList; //Contains every entity the weapon hits in one swing. This is cleared after each swing.
    private float angleAdjustment; //Used to make the weapon spawn at the right angle
    private float positionAdjustX; //Used to align the weapon to ex. the player
    private float positionAdjustY; //Used to align the weapon to ex. the player

    public Weapon(String name, int damage, float weight, float range, String texturePath, int textureWidth, int textureHeight, float hitboxScaleX, float hitboxScaleY, float hitboxOriginX, float angle, float angleAdjustment, float positionAdjustX, float positionAdjustY, Entity owner) {
        super(null, range, textureWidth, textureHeight, angle, hitboxScaleX, hitboxScaleY, hitboxOriginX);
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        this.texturePath = texturePath;
        this.owner = owner;
        this.hitEntityList = new ArrayList<>();
        this.angleAdjustment = angleAdjustment;
        this.positionAdjustX = positionAdjustX;
        this.positionAdjustY = positionAdjustY;
    }
    public Weapon(String name, int damage, float weight, float range, String texturePath, int textureWidth, int textureHeight, float hitboxScaleX,  float hitboxScaleY, float hitboxOriginX, float angle, float angleAdjustment, Entity owner) {
        this(name, damage, weight, range, texturePath, textureWidth, textureHeight, hitboxScaleX, hitboxScaleY, hitboxOriginX, angle, angleAdjustment, 0, 0, owner);
    }
    public Weapon(String name, int damage, float weight, float range, String texturePath, int textureWidth, int textureHeight, float hitboxScaleX,  float hitboxScaleY, float hitboxOriginX, Entity owner) {
        this(name, damage, weight, range, texturePath, textureWidth, textureHeight, hitboxScaleX, hitboxScaleY, hitboxOriginX, 0, 0, 0, 0, owner);
    }
    public Weapon(String name, int damage, float weight, float range, String texturePath, int textureWidth, int textureHeight, Entity owner) {
        this(name, damage, weight, range, texturePath, textureWidth, textureHeight, 0, 0, 0, 0, 0, 0, 0, owner);
    }
    public Weapon(String name, int damage, float weight, float range, Entity owner) {
        this(name, damage, weight, range, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, owner);
    }

    public Weapon() {
        texturePath = null;
    }

    public String getName() {
        return this.name;
    }

    public Entity getOwner() {
        return this.owner;
    }

    public int getDamage() {
        return this.damage;
    }

    public float getWeight() {
        return this.weight;
    }

    public float getRange() {
        return super.getRadius();
    }

    public float getAngleAdjustment() {
        return this.angleAdjustment;
    }

    public float getPositionAdjustX() {
        return this.positionAdjustX;
    }

    public float getPositionAdjustY() {
        return this.positionAdjustY;
    }

    public void removeWeaponTexture() {
        super.removeTexturePath();
    }

    public void setWeaponTexture() {
        FileLoader.loadFile(texturePath, getClass());
        super.setTexturePath(this.texturePath);
    }

    public void setAngleAdjustment(float angleAdjustment) {
        this.angleAdjustment = angleAdjustment;
    }

    public void setPositionAdjustX(float positionAdjustX) {
        this.positionAdjustX = positionAdjustX;
    }

    public void setPositionAdjustY(float positionAdjustY) {
        this.positionAdjustY = positionAdjustY;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public void addEntityHit(Entity entity) {
        this.hitEntityList.add(entity);
    }

    public boolean isEntityHit(Entity entity) {
        return this.hitEntityList.contains(entity);
    }

    public void resetHitEntityList() {
        this.hitEntityList.clear();
    }
}
