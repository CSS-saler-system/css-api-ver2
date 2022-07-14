package com.springframework.csscapstone.utils.io_utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonWriter {

    private static Path getAbsolutePath() {
        String property = System.getProperty("user.dir");
        return Paths.get(property);
    }

    public static void saveJsonFiles(String filename, String jsonObject) {
        File file = getAbsolutePath().toFile();
        new File(file, filename);
    }
}
