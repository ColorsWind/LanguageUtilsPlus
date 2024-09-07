package com.github.colorswind.langutils;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LocaleMap {
    private final String locale;
    private final Map<String, String> translations;
    public LocaleMap(@Nonnull String locale, @Nonnull JsonObject translations) {
        this.locale = locale;
        this.translations = new HashMap<>();
        for (String key : translations.keySet()) {
            this.translations.put(key, translations.get(key).getAsString());
        }
    }

    @Nonnull
    public String translate(@Nonnull String translateKey) {
        return translations.getOrDefault(translateKey, translateKey);
    }

    public void testMaterial(@Nonnull File file, @Nonnull Logger logger) throws IOException {
        if (file.exists()) { return; }
        logger.info("Dump " + locale + " material to " + file.getName() + ".");
        FileConfiguration config = new YamlConfiguration();
        for (Material material : Material.values()) {
            String translatedKey = TranslationHelper.PROVIDER.getItemTranslationKey(material);
            config.set(material.name(), translate(translatedKey));
        }
        config.save(file);
    }

    public void testEntityType(@Nonnull File file, @Nonnull Logger logger) throws IOException {
        if (file.exists()) { return; }
        logger.info("Dump " + locale + " entity type to " + file.getName() + ".");
        FileConfiguration config = new YamlConfiguration();
        for (EntityType entityType : EntityType.values()) {
            if (entityType == EntityType.UNKNOWN) { continue; }
            String translatedKey = TranslationHelper.PROVIDER.getEntityTranslationKey(entityType);
            config.set(entityType.name(), translate(translatedKey));
        }
        config.save(file);
    }

    public void testBiome(@Nonnull File file, @Nonnull Logger logger) throws IOException {
        if (file.exists()) { return; }
        logger.info("Dump " + locale + " biome to " + file.getName() + ".");
        FileConfiguration config = new YamlConfiguration();
        for (Biome biome : Biome.values()) {
            String translatedKey = TranslationHelper.PROVIDER.getBiomeTranslationKey(biome);
            config.set(biome.getKey().toString(), translate(translatedKey));
        }
        config.save(file);
    }

    public void testEnchantment(@Nonnull File file, @Nonnull Logger logger) throws IOException {
        if (file.exists()) { return; }
        logger.info("Dump " + locale + " enchantment to " + file.getName() + ".");
        FileConfiguration config = new YamlConfiguration();
        for (Enchantment enchantment : Enchantment.values()) {
            String translatedKey = TranslationHelper.PROVIDER.getEnchantmentTranslationKey(enchantment);
            config.set(enchantment.getKey().toString(), translate(translatedKey));
        }
        config.save(file);
    }




}
