package Common.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
}
