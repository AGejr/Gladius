package Collision;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IPostEntityProcessingService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Collision implements IPostEntityProcessingService {
    private List<List<Integer>> csv;
    private int width;
    private int height;

    @Override
    public void process(GameData gameData, World world) {
        if (csv == null) {
            fetchData();
            height = 1280;
            width = 1600;
        }
        for (Entity entity : world.getEntities()) {
            // Collision with tiles
            if(entity.getPart(MovingPart.class) != null) {
                float entityLeft = (entity.getX() + ((float) entity.getTextureWidth()/2) - entity.getRadius()/2);
                float entityRight = (entity.getX() + ((float) entity.getTextureWidth()/2) + entity.getRadius()/2);
                float entityTop = (entity.getY() + entity.getRadius());
                float entityBottom = entity.getY();
                float radius = entity.getRadius();
                int y = (int) (40 - ((entity.getY() / height) * 40));
                int x = (int) (((entity.getX()+(entity.getTextureWidth())/2) / width) * 50); // divide by 2 to get center
                int tile = csv.get(y).get(x);
                int[] gate = new int[]{24, 25};
                int[] spawn = new int[]{161, 162};
                int[] shop = new int[]{163, 164};
                int[] water = new int[]{70, 71, 78, 79, 86,87,88, 103, 123, 127, 159, 155, 108, 107};
                int[] noCollide = new int[]{70, 71, 72, 78, 79, 86,87,88, 103, 104, 107, 123, 127, 159, 155, 108, 24, 25, 37, 98, 99, 159, 160, 161,162,163,164, 165, 177, 178, 179};

                MovingPart movingPart = entity.getPart(MovingPart.class);
                // Checks wall layer, if there is a wall (not 0)
                //      Checks should the tile be ignored
                //      Checks if the distance between entity and tile < entity radius
                if(csv.get(y-1).get(x) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y-1).get(x)) && (height-((y-1)*32)-32) - entityTop <= 2) {
                    movingPart.setColTop(true);
                } else {
                    movingPart.setColTop(false);
                }
                if(csv.get(y+1).get(x) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y+1).get(x)) && entityBottom - (height-(y+1)*32) <= 2) {
                    movingPart.setColBot(true);
                } else {
                    movingPart.setColBot(false);
                }
                if(csv.get(y).get(x-1) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y).get(x-1)) && entityLeft - (((x-1)*32)+32) <= 2) {
                    movingPart.setColLeft(true);
                } else {
                    movingPart.setColLeft(false);
                }
                if(csv.get(y).get(x+1) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y).get(x+1)) && ((x+1)*32) - entityRight <= 2) {
                    movingPart.setColRight(true);
                } else {
                    movingPart.setColRight(false);
                }

                if (Arrays.stream(water).anyMatch(i -> i == csv.get(y).get(x))) {
                    movingPart.setSlow(0.5f);
                } else {
                    movingPart.setSlow(1f);
                }

                if (Arrays.stream(gate).anyMatch(i -> i == tile)) {
                    entity.setY(346);
                } else if (tile == 165) {
                    entity.setY(200);
                }

                // Collision with entities
                for(Entity collidingEntity : world.getEntities()) {
                    // ^ TO AVOID COLLISION BETWEEN TWO MOVING ENTITIES
                    // SHOULD MAYBE BE REMOVED!
                    LifePart collidingEntityLifePart = collidingEntity.getPart(LifePart.class);
                    if(collidingEntity.getPart(MovingPart.class) == null && collidingEntityLifePart != null && collidingEntityLifePart.getLife() > 0) {
                        float collidingEntityLeft = (collidingEntity.getX() + ((float) collidingEntity.getTextureWidth()/2) - collidingEntity.getRadius()/2);
                        float collidingEntityRight = (collidingEntity.getX() + ((float) collidingEntity.getTextureWidth()/2) + collidingEntity.getRadius()/2);
                        float collidingEntityTop = (collidingEntity.getY() + ((float) collidingEntity.getTextureHeight()/2) + collidingEntity.getRadius()/2);
                        float collidingEntityBottom = (collidingEntity.getY() + ((float) collidingEntity.getTextureHeight()/2) - collidingEntity.getRadius()/2);
                        StatsPart collidingEntityStats = collidingEntity.getPart(StatsPart.class);
                        // check left collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                entityLeft - collidingEntityRight <= 2 &&
                                entityLeft >= collidingEntityLeft &&
                                entityBottom <= collidingEntityTop &&
                                entityTop >= collidingEntityBottom) {
                            movingPart.setColLeft(true);
                            if(collidingEntityStats != null && collidingEntity.getPart(MovingPart.class) == null) {
                                LifePart defendingEntity = entity.getPart(LifePart.class);
                                StatsPart defenderStats = entity.getPart(StatsPart.class);
                                if(collidingEntityStats.getAttack() != 0) {
                                    int totalDamage = collidingEntityStats.getAttack() - defenderStats.getDefence();
                                    defendingEntity.subtractLife(totalDamage);
                                }
                            }
                        }
                        // check right collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                collidingEntityLeft - entityRight <= 2 &&
                                entityRight <= collidingEntityRight &&
                                entityBottom <= collidingEntityTop &&
                                entityTop >= collidingEntityBottom){
                            movingPart.setColRight(true);
                            if(collidingEntityStats != null && collidingEntity.getPart(MovingPart.class) == null) {
                                LifePart defendingEntity = entity.getPart(LifePart.class);
                                StatsPart defenderStats = entity.getPart(StatsPart.class);
                                if(collidingEntityStats.getAttack() != 0) {
                                    int totalDamage = collidingEntityStats.getAttack() - defenderStats.getDefence();
                                    defendingEntity.subtractLife(totalDamage);
                                }
                            }
                        }
                        // check top collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                entityRight >= collidingEntityLeft &&
                                entityLeft <= collidingEntityRight &&
                                entityBottom - collidingEntityTop <= 2 &&
                                entityTop >= collidingEntityBottom){
                            movingPart.setColBot(true);
                            if(collidingEntityStats != null && collidingEntity.getPart(MovingPart.class) == null) {
                                LifePart defendingEntity = entity.getPart(LifePart.class);
                                StatsPart defenderStats = entity.getPart(StatsPart.class);
                                if(collidingEntityStats.getAttack() != 0) {
                                    int totalDamage = collidingEntityStats.getAttack() - defenderStats.getDefence();
                                    defendingEntity.subtractLife(totalDamage);
                                }
                            }
                        }
                        // check bottom collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                entityRight >= collidingEntityLeft &&
                                entityLeft <= collidingEntityRight &&
                                collidingEntityBottom - entityTop <= 2 &&
                                entityBottom <= collidingEntityTop){
                            movingPart.setColTop(true);
                            if(collidingEntityStats != null && collidingEntity.getPart(MovingPart.class) == null) {
                                LifePart defendingEntity = entity.getPart(LifePart.class);
                                StatsPart defenderStats = entity.getPart(StatsPart.class);
                                if(collidingEntityStats.getAttack() != 0) {
                                    int totalDamage = collidingEntityStats.getAttack() - defenderStats.getDefence();
                                    defendingEntity.subtractLife(totalDamage);
                                }
                            }
                        }
                    }
                }
            } else if(entity.getPart(MovingPart.class) == null && entity.getPart(LifePart.class) != null){
                // Entity is a spawned object
                // Makes sure spawned objects aren't inside walls
                int y = (int) (40 - (((entity.getY() + entity.getTextureHeight()/2) / height) * 40));
                int x = (int) ((entity.getX() + entity.getTextureWidth()/2) / width) * 50; // divide by 2 to get center
                if(csv.get(y).get(x) == 1) {
                    // Entity is inside a wall
                    entity.setX(new Random().nextInt((1000 - 400) + 1) + 400);
                    entity.setY(new Random().nextInt((1000 - 400) + 1) + 400);
                    System.out.println("Moving Object");
                }
            }
        }
    }

    private void fetchData() {
        Scanner scanner = null;
        csv = new ArrayList<List<Integer>>();
        try {
            scanner = new Scanner(new File("Map/Map.tmx"));
            for (int i = 0; i < 93; i++) {
                if (scanner.hasNextLine()) scanner.nextLine();
            }
            scanner.useDelimiter(",");
            for (int i = 1; i <= 40; i++) {
                List<Integer> integers = new ArrayList<>();
                for (int j = 1; j <= 50; j++) {
                    integers.add(Integer.parseInt(scanner.next().trim()));
                }
                scanner.nextLine();
                csv.add(integers);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
