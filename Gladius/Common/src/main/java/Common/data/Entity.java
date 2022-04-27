package Common.data;


import Common.data.entityparts.EntityPart;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity extends Sprite implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private float radius;
    private String texturePath;
    private int textureWidth;
    private int textureHeight;
    private Map<Class, EntityPart> parts;
    private float angle;
    private Polygon polygonBoundaries;
    private float scaling = 1f;

    public Entity(String texturePath,float radius, int textureWidth, int textureHeight, float angle, float hitboxScaleX, float hitboxScaleY, float hitboxOriginX) {
        super();
        this.parts = new ConcurrentHashMap<>();
        this.texturePath = texturePath;
        this.radius = radius;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.angle = angle;
        /*The polygon is the hitbox for the entity, this is used to register hits with the weapon. It is drawn rom the center of the entity's x-axis
        * The scale is used to make the hitbox fit the texture of the entity better, because of transparent areas in the texture.
        * The hitboxOriginX, defines the center of the x-axis where the box is made from.
        * The hitbox aligns with the bottom of the texture.*/
        this.polygonBoundaries = new Polygon(new float[]{super.getX(), super.getY(), super.getX(), super.getY() + textureHeight, super.getX() + textureWidth, super.getY() + textureHeight, super.getX() + textureWidth, super.getY()});
        this.polygonBoundaries.setOrigin(hitboxOriginX, 0);
        this.polygonBoundaries.setScale(hitboxScaleX, hitboxScaleY);
    }

    public Entity(String texturePath,float radius, int textureWidth, int textureHeight, float angle, float hitboxScaleX, float hitboxScaleY) {
        this(texturePath, radius, textureWidth, textureHeight, angle, hitboxScaleX, hitboxScaleY, (float) textureWidth / 2);
    }

    public Entity(String texturePath,float radius, int textureWidth, int textureHeight, float angle) {
        this(texturePath, radius, textureWidth, textureHeight, angle, 0.9f, 0.9f, (float) textureWidth / 2);
    }

    public Entity(String texturePath,float radius, int textureWidth, int textureHeight) {
        this(texturePath, radius, textureWidth, textureHeight, 0f, 0.9f,0.9f, (float) textureWidth / 2);
    }

    public Entity(String texturePath, float radius) {
        this(texturePath, radius, 0, 0, 0f, 0.9f ,0.9f, 0);
    }

    public Entity(Entity entity){
        super(entity);
        this.parts = new ConcurrentHashMap<>();
        this.radius = entity.getRadius();
        this.parts = entity.getParts();

        this.textureWidth = entity.getTextureWidth();
        this.textureHeight = entity.getTextureWidth();
    }

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public Map<Class, EntityPart> getParts() {return parts;}

    public void setRadius(float r){
        this.radius = r;
    }

    public float getRadius(){
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public String getTexturePath() {
        return texturePath;
    }

    public int getTextureWidth() {
        return this.textureWidth;
    }

    public int getTextureHeight() {
        return this.textureHeight;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;

    }

    public void removeTexturePath() {
        this.texturePath = null;
        setTexture(null);
    }

    public void initTexture() {
        File textureFile = new File(this.getTexturePath());
        FileHandle fileHandle = new FileHandle(textureFile);
        Texture texture = new Texture(fileHandle);
        this.setTexture(texture);
        this.setRegion(0,0,this.textureWidth, this.textureHeight);
    }

    public Polygon getPolygonBoundaries() {
        return this.polygonBoundaries;
    }

    public void updatePolygonBoundariesPosition() {
        this.getPolygonBoundaries().setPosition(this.getX(), this.getY());
    }

    public void setScaling(float scaling) {
        this.scaling = scaling;
    }

    public float getScaling() {
        return scaling;
    }
}
