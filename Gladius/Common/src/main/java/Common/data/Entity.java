package Common.data;


import Common.data.entityparts.EntityPart;
import Common.tools.FileLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;
import java.util.HashMap;
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

    public Entity(String texturePath,float radius, int textureWidth, int textureHeight, float angle) {
        super();
        this.parts = new ConcurrentHashMap<>();
        this.texturePath = texturePath;
        this.radius = radius;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.angle = angle;
    }

    public Entity(String texturePath,float radius, int textureWidth, int textureHeight) {
        this(texturePath, radius, textureWidth, textureHeight, 0f);
    }

    public Entity(String texturePath,float radius) {
        this(texturePath, radius, 0, 0, 0f);
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
        FileLoader.loadFile(texturePath, getClass());
    }

    public void removeTexturePath() {
        this.texturePath = null;
        setTexture(null);
    }
}
