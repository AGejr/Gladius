package collision;

import Collision.Collision;
import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.tools.FileLoader;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class collisionTest {
    private GameData mockGameData;
    private World mockedWorld;
    private Collision collision;
    private MovingPart mockMovingpart;
    Entity testEntity;

   @Before
   public void init() {
       mockGameData = mock(GameData.class);

       when(mockGameData.isGateEnabled()).thenReturn(true);
       when(mockGameData.getMapWidth()).thenReturn(1600);
       when(mockGameData.getMapHeight()).thenReturn(1280);

       collision = new Collision();
       mockedWorld = mock(World.class);

       mockMovingpart = mock(MovingPart.class);

       testEntity = new Entity("",2,32,32,0,1,1,1,1,1,16,16);
       testEntity.add(mockMovingpart);
       testEntity.setY(220f);
       testEntity.setX(780f);
       /*
       when(mockEntity.getY()).thenReturn(220f);
       when(mockEntity.getX()).thenReturn(780f);
       when(mockEntity.getRadiusOffsetX()).thenReturn(16);
       when(mockEntity.getRadiusOffsetY()).thenReturn(16);
       when(mockEntity.getTextureWidth()).thenReturn(32);
       when(mockEntity.getRadius()).thenReturn(2f);
       when(mockEntity.getPart(MovingPart.class)).thenReturn(mockMovingpart); */

       Collection<Entity> entityList = new ArrayList<Entity>();
       entityList.add(testEntity);

       FileLoader.loadFile("Map/Map.tmx", getClass());

       when(mockedWorld.getEntities()).thenReturn(entityList);
       when(mockedWorld.getCsvMap()).thenReturn(FileLoader.fetchData("Map/Map.tmx"));
   }

  @Test
  public void gateEnteredTest(){

       assertEquals(testEntity.getY(),220f,1f);
       assertEquals(testEntity.getX(), 780f, 1f);
       collision.process(mockGameData,mockedWorld);
      assertNotEquals(testEntity.getY(),220f,1f);
      assertNotEquals(testEntity.getX(), 780f, 1f);
  }

}
