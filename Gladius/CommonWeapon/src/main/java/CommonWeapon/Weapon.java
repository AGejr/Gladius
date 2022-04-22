package CommonWeapon;
import Common.data.Entity;
import java.util.List;
import java.util.ArrayList;
public class Weapon extends Entity {
    private String name;
    private int damage;
    private float weight;
    private final String texturePath;
    private Entity owner;
    private List<Entity> hitEntityList;
    private float angleAdjustment;
    private float positionAdjustX;
    private float positionAdjustY;

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

    public void addEntityHitted(Entity entity) {
        this.hitEntityList.add(entity);
    }

    public boolean isEntityHitted(Entity entity) {
        return this.hitEntityList.contains(entity);
    }

    public void resetHitEntityList() {
        this.hitEntityList.clear();
    }
}
