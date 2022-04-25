package Common.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileLoader {
    private static int[] noCollide = new int[]{70, 71, 72, 78, 79, 86, 87, 88, 103, 104, 107, 123, 127, 159, 155, 108, 37, 98, 99, 159, 160, 161, 162, 163, 164, 165, 177, 178, 179};
    private static int[] water = new int[]{70, 71, 78, 79, 86, 87, 88, 103, 123, 127, 159, 155, 108, 107};

    public static void loadFiles(String[] files, Class<?> importClass) {
        for (String file : files) {
            try (InputStream inputStream = importClass.getClassLoader().getResourceAsStream(file)) {
                File newFile = new File(file);
                if (inputStream != null) {
                    FileUtils.copyInputStreamToFile(inputStream, newFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadFile(String file, Class<?> importClass) {
        try (InputStream inputStream = importClass.getClassLoader().getResourceAsStream(file)) {
            File newFile = new File(file);
            if (inputStream != null) {
                FileUtils.copyInputStreamToFile(inputStream, newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<Integer>> fetchData(String map) {
        Scanner scanner = null;
        List<List<Integer>> csv = new ArrayList<>();
        try {
            scanner = new Scanner(new File(map));
            for (int i = 0; i < 93; i++) {
                if (scanner.hasNextLine()) scanner.nextLine();
            }
            scanner.useDelimiter(",");
            for (int i = 1; i <= 40; i++) {
                List<Integer> integers = new ArrayList<>();
                for (int j = 1; j <= 50; j++) {
                    int nextInt = Integer.parseInt(scanner.next().trim());
                    if (Arrays.stream(water).anyMatch(w -> w == nextInt)) {
                        integers.add(2);
                    } else if (nextInt == 0 || Arrays.stream(noCollide).anyMatch(c -> c == nextInt)) {
                        integers.add(0);
                    } else {
                        integers.add(1);
                    }
                }
                scanner.nextLine();
                csv.add(integers);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return csv;
    }
}
