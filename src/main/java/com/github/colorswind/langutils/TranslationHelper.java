package com.github.colorswind.langutils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class TranslationHelper {
    public interface ITranslationProvider {
        @Nonnull String getItemTranslationKey(@Nonnull ItemStack itemStack);

        @Nonnull String getItemTranslationKey(@Nonnull Material material);

        @Nonnull String getEntityTranslationKey(@Nonnull Entity entity);

        @Nonnull String getEntityTranslationKey(@Nonnull EntityType entityType);

        @Nonnull String getBiomeTranslationKey(@Nonnull Biome biome);

        @Nonnull String getEnchantmentLevelTranslationKey(int level);

        @Nonnull String getEnchantmentTranslationKey(@Nonnull Enchantment enchantment);
    }

    private static class SpigotTranslationProver implements ITranslationProvider {

        @Override
        @Nonnull
        public String getItemTranslationKey(@Nonnull ItemStack itemStack) {
            return itemStack.getTranslationKey();
        }

        @Nonnull
        @Override
        public String getItemTranslationKey(@Nonnull Material material) {
            return material.getTranslationKey();
        }

        @Nonnull
        @Override
        public String getEntityTranslationKey(@Nonnull Entity entity) {
            return entity.getType().getTranslationKey();
        }

        @Nonnull
        @Override
        public String getEntityTranslationKey(@Nonnull EntityType entityType) {
            return entityType.getTranslationKey();
        }

        @Nonnull
        @Override
        public String getBiomeTranslationKey(@Nonnull Biome biome) {
            NamespacedKey nsKey = biome.getKey();
            return "biome." + nsKey.getNamespace() + "." + nsKey.getKey();
        }

        @Nonnull
        @Override
        public String getEnchantmentLevelTranslationKey(int level) {
            return "enchantment.level." + level;
        }

        @Nonnull
        @Override
        public String getEnchantmentTranslationKey(@Nonnull Enchantment enchantment) {
            NamespacedKey nsKey = enchantment.getKey();
            return "enchantment." + nsKey.getNamespace() + "." + nsKey.getKey();
        }
    }

    public static final ITranslationProvider PROVIDER;
    static {
        PROVIDER = new SpigotTranslationProver();
    }

}
