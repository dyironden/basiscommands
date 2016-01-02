package org.to2mbn.basiscommands.i18n;

import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class I18n {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final Locale DEFAULT_LOCALE = Locale.US;

    private static final Map<Locale, Map<String, String>> i18nSources;
    private static Locale currentLocale;

    static {
        i18nSources = new HashMap<>();
        currentLocale = Locale.getDefault();
    }

    public static String translate(String key) {
        Objects.requireNonNull(key);
        if (!hasTranslate(key)) {
            return key;
        }
        return i18nSources.get(currentLocale).get(key);
    }

    public static String format(String key, Object... args) {
        Objects.requireNonNull(key);
        if (!hasTranslate(key)) {
            return key;
        }
        return String.format(translate(key), args);
    }

    public static boolean hasTranslate(String key) {
        Map<String, String> source = i18nSources.get(currentLocale);
        return source != null && source.containsKey(key);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale locale) {
        Objects.requireNonNull(locale);
        currentLocale = locale;
    }

    public static Map<Locale, Map<String, String>> getI18nSources() {
        return i18nSources;
    }

    public static void loadSourcesFromURL(URL url, Locale locale) throws IOException {
        Objects.requireNonNull(url);
        loadSourcesFromStream(url.openStream(), locale);
    }

    public static void loadSourcesFromStream(InputStream inputStream, Locale locale) throws IOException {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(locale);

        List<String> sources = IOUtils.readLines(inputStream);
        sources.stream()
                .filter(line -> !line.startsWith("#") && !line.isEmpty())
                .forEach(source -> {
                    String[] split = source.split("=", 2);
                    if (split.length == 2 && !split[0].isEmpty() && !split[1].isEmpty()) {
                        if (!i18nSources.containsKey(locale)) {
                            i18nSources.put(locale, new HashMap<>());
                        }
                        i18nSources.get(locale).put(split[0].trim(), split[1]);
                    }
                });
    }

    public static String getLangFileName(Locale locale) {
        return String.format("%s.lang", locale.toString());
    }
}
