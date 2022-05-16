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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

       Collection<Entity> entityList = new ArrayList<Entity>();
       entityList.add(testEntity);

       List<List<Integer>> csv = new ArrayList<>();
       for (int i = 1; i <= 40; i++) {
           List<Integer> integers = new ArrayList<>();
           for (int j = 1; j <= 50; j++) {
                   integers.add(1);
           }
           csv.add(integers);
       }

       when(mockedWorld.getEntities()).thenReturn(entityList);
       when(mockedWorld.getCsvMap()).thenReturn(csv);
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
