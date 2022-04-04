package Collision;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IPostEntityProcessingService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
            if(entity.getPart(MovingPart.class) != null) {
                float radius = entity.getRadius();
                int y = (int) (40 - ((entity.getY() / height) * 40));
                // todo : radius*16/2 should be changed to texture width / 2
                int x = (int) (((entity.getX()+(radius*16)/2) / width) * 50); // divide by 2 to get center
                int tile = csv.get(y).get(x);
                int[] gate = new int[]{24, 25};
                int[] spawn = new int[]{161, 162};
                int[] shop = new int[]{163, 164};
                int[] water = new int[]{70, 71, 78, 79, 86,87,88, 103, 123, 127, 159, 155, 108, 107};
                int[] noCollide = new int[]{70, 71, 72, 78, 79, 86,87,88, 103, 104, 107, 123, 127, 159, 155, 108, 24, 25, 37, 98, 99, 159, 160, 161,162,163,164, 165, 177, 178, 179};

                MovingPart movingPart = entity.getPart(MovingPart.class);
                // Check wall layer, if there is a wall (not 0)
                //      Should the tile be ignored?
                //      Is the distance between entity and tile < entity radius?
                if(csv.get(y-1).get(x) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y-1).get(x)) && (height-((y-1)*32)-32) - entity.getY() <= radius) {
                    movingPart.setColTop(true);
                } else {
                    movingPart.setColTop(false);
                }
                if(csv.get(y+1).get(x) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y+1).get(x)) && entity.getY() - (height-(y+1)*32) <= radius) {
                    movingPart.setColBot(true);
                } else {
                    movingPart.setColBot(false);
                }
                // todo : radius*16/2 should be changed to texture width / 2
                if(csv.get(y).get(x-1) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y).get(x-1)) && (entity.getX()+(radius*16)/2) - (((x-1)*32)+32) < radius) {
                    movingPart.setColLeft(true);
                } else {
                    movingPart.setColLeft(false);
                }
                // todo : radius*16/2 should be changed to texture width / 2
                if(csv.get(y).get(x+1) != 0 && !Arrays.stream(noCollide).anyMatch(i -> i == csv.get(y).get(x+1)) && ((x+1)*32) - (entity.getX()+(radius*16)/2) < radius) {
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
