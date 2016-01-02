package org.to2mbn.basiscommands.utils;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

public class IOUtils {
    public static final String LINE_SPEARATOR = System.getProperty("line.separator");

    public static List<String> readLines(File file) throws IOException {
        return readLines(file, "UTF-8");
    }

    public static List<String> readLines(File file, String encoding) throws IOException {
        List<String> list = Lists.newArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            list.add(reader.readLine());
        }
        return list;
    }
}
