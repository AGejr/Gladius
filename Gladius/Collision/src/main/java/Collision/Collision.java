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

    @Override
    public void process(GameData gameData, World world) {
        if (csv == null) {
            fetchData();
        }

        for (Entity entity : world.getEntities()) {
            int y = (int) (40 - ((entity.getY() / 1280) * 40));
            int x = (int) ((entity.getX() / 1600) * 50);
            int tile = csv.get(y).get(x);
            int[] gate = new int[]{23, 24};
            int[] spawn = new int[]{160, 161};
            int[] shop = new int[]{162, 163};


            MovingPart movingPart = entity.getPart(MovingPart.class);
            if (Arrays.stream(gate).anyMatch(i -> i == tile)) {
                entity.setY(416);
            } else if (tile == 164) {
                entity.setY(300);
            } else if (tile != 0) {
                if (movingPart.isUp()) {
                    entity.setY(entity.getY() - 1);
                }
                if (movingPart.isDown()) {
                    entity.setY(entity.getY() + 1);
                }
                if (movingPart.isLeft()) {
                    entity.setX(entity.getX() + 1);
                }
                if (movingPart.isRight()) {
                    entity.setX(entity.getX() - 1);
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
            for (int i = 0; i < 49; i++) {
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
