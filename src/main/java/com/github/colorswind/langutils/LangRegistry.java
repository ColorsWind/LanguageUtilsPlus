package com.github.colorswind.langutils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class LangRegistry {
    private static final Map<String, LocaleMap> LOADED_LOCALE_MAPS = new HashMap<>();
    private static String FALLBACK_LANGUAGE = "en_us";

    @Nonnull
    public static String standardizeLocale(@Nonnull String locale) {
        return locale.toLowerCase().replace('-', '_');
    }

    public static void init(@Nonnull Logger logger, @Nonnull String version, @Nonnull List<String> localeList,
                            @Nonnull String fallbackLanguage, @Nonnull File cacheDir)  throws IOException {
        LangRegistry.FALLBACK_LANGUAGE = standardizeLocale(fallbackLanguage);
        Gson gson = new Gson();
        JsonObject assertIndex = null;
        for (String locale : localeList) {
            locale = standardizeLocale(locale);
            File localeFile = new File(cacheDir, locale + ".json");
            if (!localeFile.exists()) {
                if (locale.equalsIgnoreCase("en_us")) {
                    DownloadHelper.copyEnglish(localeFile);
                } else {
                    if (assertIndex == null) {
                        logger.info("Fetch Minecraft asset index: " + version + ".");
                        assertIndex = DownloadHelper.fetchAssertIndex(version);
                    }
                    logger.info("Download Minecraft lang: " + locale + ".");
                    DownloadHelper.downloadLang(assertIndex, locale, localeFile);
                }
            }

            try (BufferedReader reader = Files.newBufferedReader(localeFile.toPath(), StandardCharsets.UTF_8)) {
                JsonObject localeMapJson = gson.fromJson(reader, JsonObject.class);
                LOADED_LOCALE_MAPS.put(locale, new LocaleMap(locale, localeMapJson));
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to parse language file: " + localeFile.getName(), e);
            }
        }
    }

    @Nonnull
    public static LocaleMap getLocaleMap(@Nonnull String locale) {
        locale = standardizeLocale(locale);
        LocaleMap ret = LOADED_LOCALE_MAPS.get(locale);
        if (ret == null) {
            ret = Objects.requireNonNull(LOADED_LOCALE_MAPS.get(FALLBACK_LANGUAGE),
                    "Fallback language not found: " + FALLBACK_LANGUAGE);
        }
        return ret;
    }

}
