package com.github.colorswind.langutils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class DownloadHelper {
    public static String VERSION_MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";
    public static String RESOURCES_URL = "https://resources.download.minecraft.net/";

    @Nonnull
    public static JsonObject fetchAssertIndex(@Nonnull String targetVersion) throws IllegalArgumentException {
        JsonObject versionManifest = getJsonFromUrl(VERSION_MANIFEST_URL);

        // Step 2: Find version 1.20.6
        JsonArray versions = versionManifest.getAsJsonArray("versions");
        String assetIndexUrl = null;
        for (int i = 0; i < versions.size(); i++) {
            JsonObject version = versions.get(i).getAsJsonObject();
            if (version.get("id").getAsString().equals(targetVersion)) {
                String versionUrl = version.get("url").getAsString();
                JsonObject versionDetails = getJsonFromUrl(versionUrl);
                JsonObject assetIndex = versionDetails.get("assetIndex").getAsJsonObject();
                assetIndexUrl = assetIndex.get("url").getAsString();
                break;
            }
        }
        if (assetIndexUrl == null) {
            throw new IllegalArgumentException("Could not find asset index for version: " + targetVersion);
        }
        return getJsonFromUrl(assetIndexUrl);
    }

    public static void copyEnglish(@Nonnull File target) {
        try {
            try (InputStream is = Objects.requireNonNull(Bukkit.class.getClassLoader()
                    .getResourceAsStream("assets/minecraft/lang/en_us.json"))) {
                java.nio.file.Files.copy(is, target.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not get en_us.json from server.jar: ", e);
        }
    }

    public static void downloadLang(@Nonnull JsonObject assetIndex, @Nonnull String lang, @Nonnull File target) {
        JsonObject objects = assetIndex.getAsJsonObject("objects");
        JsonObject langJson = objects.getAsJsonObject("minecraft/lang/" + lang + ".json");
        String hash = langJson.get("hash").getAsString();
        String languageFileUrl = RESOURCES_URL + hash.substring(0, 2) + "/" + hash;
        try {

            URL url = new URL(languageFileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (InputStream is = conn.getInputStream()) {
                java.nio.file.Files.copy(is, target.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not download language from: " + languageFileUrl, e);
        }
    }

    @Nonnull
    private static JsonObject getJsonFromUrl(@Nonnull String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (InputStream is = conn.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return new Gson().fromJson(response.toString(), JsonObject.class);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not get JSON from URL: " + urlString, e);
        }
    }

}
