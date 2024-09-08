package com.github.colorswind.langutils;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LangUtils extends JavaPlugin {
    public static final int PLUGIN_ID = 23307;
    private Metrics metrics = null;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
            throw new IllegalArgumentException("Fail to create data folder.");
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveResource("config.yml", false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        DownloadHelper.VERSION_MANIFEST_URL = Objects.requireNonNull(config.getString("VersionManifestUrl"));
        DownloadHelper.RESOURCES_URL = Objects.requireNonNull(config.getString("ResourcesUrl"));
        List<String> loadLanguages = Objects.requireNonNull(config.getStringList("LoadLanguage"));
        String minecraftVersion = Objects.requireNonNull(config.getString("MinecraftVersion"));
        if (minecraftVersion.equalsIgnoreCase("auto")) {
            String regex = "(?<=MC: )\\d+\\.\\d+\\.\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(Bukkit.getVersion());
            if (matcher.find()) {
                minecraftVersion = matcher.group();
                getLogger().info("Detect Minecraft version: " + minecraftVersion + ".");
            } else {
                getLogger().severe("Fail to detect Minecraft version, please set in config.yml.");
                throw new IllegalArgumentException("Unknown minecraft version.");
            }
        }
        String fallbackLanguage = Objects.requireNonNull(config.getString("FallbackLanguage"));
        File cacheDir = new File(new File(getDataFolder(), "cache"), minecraftVersion);

        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new IllegalArgumentException("Fail to create cache directory: " + cacheDir.getAbsolutePath());
        }
        try {
            LangRegistry.init(getLogger(), minecraftVersion, loadLanguages, fallbackLanguage, cacheDir);
        } catch (IOException e) {
            getLogger().severe("Fail to init LangRegistry.");
            throw new RuntimeException(e);
        }
        for (String locale : loadLanguages) {
            File localeCacheDir = new File(cacheDir, locale);
            LocaleMap localeMap = LangRegistry.getLocaleMap(locale);
            try {
                localeMap.testMaterial(new File(localeCacheDir, "material.yml"), getLogger());
                localeMap.testEntityType(new File(localeCacheDir, "entity-type.yml"), getLogger());
                localeMap.testBiome(new File(localeCacheDir, "biome.yml"), getLogger());
                localeMap.testEnchantment(new File(localeCacheDir, "enchant.yml"), getLogger());
            } catch (Exception e) {
                throw new RuntimeException("Fail to test lang: " + locale, e);
            }
        }
        metrics = new Metrics(this, PLUGIN_ID);
    }

    @Override
    public void onDisable() {
        metrics.shutdown();
    }
}
