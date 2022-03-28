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
    private List<List<Long>> csv;

    @Override
    public void process(GameData gameData, World world) {
        if (csv == null) {
            fetchData();
        }
        for (Entity entity : world.getEntities()) {
            int y = (int) (40 - ((entity.getY() / 1280) * 40));
            int x = (int) (((entity.getX()+32/2) / 1600) * 50);
            Long tile = csv.get(y).get(x);
            int[] gate = new int[]{24, 25};
            int[] spawn = new int[]{161, 162};
            int[] shop = new int[]{163, 164};
            long[] noCollide = new long[]{37, 98, 99, 159, 160, 161,162,163,164, 165, 177, 178, 179, 2147483746l, 2147483685l};

            MovingPart movingPart = entity.getPart(MovingPart.class);
            if (Arrays.stream(gate).anyMatch(i -> i == tile)) {
                entity.setY(366);
            } else if (tile == 165) {
                entity.setY(200);
            } else if (tile != 0) {
                System.out.println(tile);
                if(!Arrays.stream(noCollide).anyMatch(i -> i == tile)) {
                    if (movingPart.isUp()) {
                        entity.setY(entity.getY() - 1.4f);
                    }
                    if (movingPart.isDown()) {
                        entity.setY(entity.getY() + 1.4f);
                    }
                    if (movingPart.isLeft()) {
                        entity.setX(entity.getX() + 1.4f);
                    }
                    if (movingPart.isRight()) {
                        entity.setX(entity.getX() - 1.4f);
                    }
                }
            }
            //movingPart.setColTop(Arrays.stream(topCollision).anyMatch(i -> i == tile));
            //movingPart.setColLeft(Arrays.stream(bottomCollision).anyMatch(i -> i == tile));
        }
    }

    private void fetchData() {
        Scanner scanner = null;
        csv = new ArrayList<>();
        try {
            scanner = new Scanner(new File("Map/Map.tmx"));
            for (int i = 0; i < 93; i++) {
                if (scanner.hasNextLine()) scanner.nextLine();
            }
            scanner.useDelimiter(",");
            for (int i = 1; i <= 40; i++) {
                List<Long> integers = new ArrayList<>();
                for (int j = 1; j <= 50; j++) {
                    integers.add(Long.parseLong(scanner.next().trim()));
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
