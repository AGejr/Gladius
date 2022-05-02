package Common.data;

import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private TiledMap tiledMap;
    private List<List<Integer>> csvMap;
    private boolean isMapLoaded = false;

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }
    
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }


    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public List<List<Integer>> getCsvMap() {
        return csvMap;
    }

    public void setCsvMap(List<List<Integer>> csvMap) {
        this.csvMap = csvMap;
    }

    public void setIsMapLoaded(boolean isLoaded) {
        isMapLoaded = isLoaded;
    }

    public boolean isMapLoaded() {
        return isMapLoaded;
    }

    public void setTileInMap(int Y, int X, int value) {
        getCsvMap().get(Y).set(X, value);
    }
}
