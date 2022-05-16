package collision;

import Collision.Collision;
import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.tools.FileLoader;

import CommonPlayer.Player;
import com.badlogic.gdx.Gdx;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class collisionTest {
    private GameData mockGameData;
    private World mockedWorld;
    private Collision collision;
    private MovingPart mockMovingpart;
    private Player testEntity;

   @Before
   public void init() {
       mockGameData = mock(GameData.class);

       when(mockGameData.isGateEnabled()).thenReturn(true);
       when(mockGameData.getMapWidth()).thenReturn(1600);
       when(mockGameData.getMapHeight()).thenReturn(1280);

       collision = new Collision();
       mockedWorld = mock(World.class);

       testEntity = new Player("",2);
       testEntity.add(new MovingPart(1));
       testEntity.setY(225f);
       testEntity.setX(785f);

       Collection<Entity> entityList = new ArrayList<>();
       entityList.add(testEntity);

       List<List<Integer>> csv = new ArrayList<>();
       for (int i = 1; i <= 40; i++) {
           List<Integer> integers = new ArrayList<>();
           for (int j = 1; j <= 50; j++) {
                   integers.add(2);
           }
           csv.add(integers);
       }

       when(mockedWorld.getEntities()).thenReturn(entityList);
       when(mockedWorld.getCsvMap()).thenReturn(csv);
   }

  @Test
  public void isSlowedTest(){

      MovingPart part = testEntity.getPart(MovingPart.class);

      assertFalse(part.isSlow());

      collision.process(mockGameData,mockedWorld);

      assertTrue(part.isSlow());
  }

 @Test
 public void isGateTest(){

     assertEquals(testEntity.getY(),225f,1f);

     collision.process(mockGameData,mockedWorld);

     assertNotEquals(testEntity.getY(),225f,1f);

 }

}
