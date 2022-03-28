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
    private Map<Class, EntityPart> parts;

    public Entity(String texturePath,float radius) {
        super();
        this.parts = new ConcurrentHashMap<>();
        this.texturePath = texturePath;
        this.radius = radius;
    }


    public Entity(Entity entity){
        super(entity);
        this.parts = new ConcurrentHashMap<>();
        this.radius = entity.getRadius();
        this.parts = entity.getParts();
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

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
        //FileLoader.loadFile(texturePath, getClass());
    }

    public void removeTexturePath() {
        this.texturePath = null;
        setTexture(null);
    }
}
