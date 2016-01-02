package org.to2mbn.basiscommands.utils;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class IOUtils {
    public static final String LINE_SPEARATOR = System.getProperty("line.separator");

    public static List<String> readLines(File file) throws IOException {
        return readLines(new FileInputStream(file), "UTF-8");
    }

    public static List<String> readLines(InputStream stream) throws IOException {
        return readLines(stream, "UTF-8");
    }

    public static List<String> readLines(InputStream stream, String encoding) throws IOException {
        List<String> list = Lists.newArrayList();
        try (Scanner scanner = new Scanner(stream, encoding)) {
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
        }
        return list;
    }
}
