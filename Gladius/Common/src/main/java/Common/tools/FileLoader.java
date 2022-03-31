package Common.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoader {

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
        List<List<Integer>> csv = new ArrayList<List<Integer>>();
        try {
            scanner = new Scanner(new File(map));
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
        return csv;
    }
}
