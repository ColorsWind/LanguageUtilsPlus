/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package com.meowj.langutils.lang;

import com.github.colorswind.langutils.LangRegistry;
import com.github.colorswind.langutils.TranslationHelper;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Meow J on 6/20/2015.
 * <p>
 * Some methods to get the name of a item.
 *
 * @author Meow J
 */
public class LanguageHelper {

    /**
     * Return the display name of the item.
     *
     * @param item   The item
     * @param locale The language of the item(if the item doesn't have a customized name, the method will return the
     *               name of the item in this language)
     * @return The name of the item
     */
    @Nonnull
    public static String getItemDisplayName(@Nonnull ItemStack item, @Nonnull String locale) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return getItemName(item, locale);
        } else {
            return itemMeta.getDisplayName();
        }
    }

    /**
     * Return the display name of the item.
     *
     * @param item   The item
     * @param player The receiver of the name
     * @return The name of the item
     */
    @Nonnull
    public static String getItemDisplayName(@Nonnull ItemStack item, @Nonnull Player player) {
        return getItemDisplayName(item, player.getLocale());
    }

    /**
     * Return the localized name of the item.
     *
     * @param item   The item
     * @param locale The language of the item
     * @return The localized name. if the item doesn't have a localized name, this method will return the unlocalized
     * name of it.
     */
    @Nonnull
    public static String getItemName(@Nonnull ItemStack item, @Nonnull String locale) {
        String translated = translateToLocal(getItemUnlocalizedName(item), locale);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            PlayerProfile profile = skullMeta.getOwnerProfile();
            String playerName = profile != null ? profile.getName() : null;
            if (playerName != null) {
                translated = translated.replace("%s", playerName);
            }
        }
        return translated;
    }

    /**
     * Return the localized name of the item.
     *
     * @param item   The item
     * @param player The receiver of the name
     * @return The localized name. if the item doesn't have a localized name, this method will return the unlocalized name of it.
     */
    @Nonnull
    public static String getItemName(@Nonnull ItemStack item, @Nonnull Player player) {
        return getItemName(item, player.getLocale());
    }

    /**
     * Return the unlocalized name of the item(Minecraft convention)
     *
     * @param item The item
     * @return The unlocalized name. If the item doesn't have a unlocalized name, this method will return the Material
     * of it.
     */
    @Nonnull
    public static String getItemUnlocalizedName(@Nonnull ItemStack item) {
        return TranslationHelper.PROVIDER.getItemTranslationKey(item);
    }

    /**
     * Return the unlocalized name of the biome(Minecraft convention)
     *
     * @param biome The biome
     * @return The unlocalized name. If the biome doesn't have a unlocalized name, this method will return the Biome of
     * it.
     */
    @Nonnull
    public static String getBiomeUnlocalizedName(@Nonnull Biome biome) {
        return TranslationHelper.PROVIDER.getBiomeTranslationKey(biome);
    }

    /**
     * Return the localized name of the biome.
     *
     * @param biome  The biome
     * @param locale The language of the biome
     * @return The localized name. if the biome doesn't have a localized name, this method will return the unlocalized
     * name of it.
     */
    @Nonnull
    public static String getBiomeName(@Nonnull Biome biome, @Nonnull String locale) {
        return translateToLocal(getBiomeUnlocalizedName(biome), locale);
    }

    /**
     * Return the localized name of the biome.
     *
     * @param biome  The biome
     * @param player The receiver of the biome
     * @return The localized name. if the biome doesn't have a localized name, this method will return the unlocalized
     * name of it.
     */
    @Nonnull
    public static String getBiomeName(@Nonnull Biome biome, @Nonnull Player player) {
        return getBiomeName(biome, player.getLocale());
    }

    /**
     * Return the unlocalized name of the entity(Minecraft convention)
     *
     * @param entity The entity
     * @return The unlocalized name. If the entity doesn't have a unlocalized name, this method will return the
     * EntityType of it.
     */
    @Nonnull
    public static String getEntityUnlocalizedName(@Nonnull Entity entity) {
        return TranslationHelper.PROVIDER.getEntityTranslationKey(entity);
    }

    /**
     * Return the unlocalized name of the entity(Minecraft convention)
     *
     * @param entityType The EntityType of the entity
     * @return The unlocalized name. If the entity doesn't have a unlocalized name, this method will return the name of
     * the EntityType.
     */
    @Nonnull
    public static String getEntityUnlocalizedName(@Nonnull EntityType entityType) {
        return TranslationHelper.PROVIDER.getEntityTranslationKey(entityType);
    }

    /**
     * Return the display name of the entity.
     *
     * @param entity The entity
     * @param locale The language of the entity(if the entity doesn't have a customized name, the method will return
     *               the name of the entity in this language)
     * @return The name of the entity
     */
    @Nonnull
    public static String getEntityDisplayName(@Nonnull Entity entity, @Nonnull String locale) {
        return entity.getCustomName() != null ? entity.getCustomName() : getEntityName(entity, locale);
    }

    /**
     * Return the display name of the entity.
     *
     * @param entity The entity
     * @param player The receiver of the name
     * @return The name of the entity
     */
    @Nonnull
    public static String getEntityDisplayName(@Nonnull Entity entity, @Nonnull Player player) {
        return getEntityDisplayName(entity, player.getLocale());
    }

    /**
     * Return the localized name of the entity.
     *
     * @param entity The entity
     * @param locale The language of the item
     * @return The localized name. if the entity doesn't have a localized name, this method will return the unlocalized
     * name of it.
     */
    @Nonnull
    public static String getEntityName(@Nonnull Entity entity, @Nonnull String locale) {
        return translateToLocal(getEntityUnlocalizedName(entity), locale);
    }

    /**
     * Return the localized name of the entity.
     *
     * @param entity The entity
     * @param player The receiver of the entity
     * @return The localized name. if the entity doesn't have a localized name, this method will return the unlocalized
     * name of it.
     */
    @Nonnull
    public static String getEntityName(@Nonnull Entity entity, @Nonnull Player player) {
        return getEntityName(entity, player.getLocale());
    }

    /**
     * Return the localized name of the entity.
     *
     * @param entityType The EntityType of the entity
     * @param locale     The language of the item
     * @return The localized name. if the entity doesn't have a localized name, this method will return the
     * unlocalized name of it.
     */
    @Nonnull
    public static String getEntityName(@Nonnull EntityType entityType, @Nonnull String locale) {
        return translateToLocal(getEntityUnlocalizedName(entityType), locale);
    }

    /**
     * Return the localized name of the entity.
     *
     * @param entityType The EntityType of the entity
     * @param player     The receiver of the entity
     * @return The localized name. if the entity doesn't have a localized name, this method will return the unlocalized
     * name of it.
     */
    @Nonnull
    public static String getEntityName(@Nonnull EntityType entityType, @Nonnull Player player) {
        return getEntityName(entityType, player.getLocale());
    }


    /**
     * Return the unlocalized name of the enchantment level(Minecraft convention)
     *
     * @param level The enchantment level
     * @return The unlocalized name.(if level is greater than 10, it will only return the number of the level)
     */
    @Nonnull
    public static String getEnchantmentLevelUnlocalizedName(int level) {
        return TranslationHelper.PROVIDER.getEnchantmentLevelTranslationKey(level);
    }

    /**
     * Return the name of the enchantment level
     *
     * @param level  The enchantment level
     * @param player The language of the level
     * @return The name of the level.(if level is greater than 10, it will only return the number of the level)
     */
    @Nonnull
    public static String getEnchantmentLevelName(int level, Player player) {
        return translateToLocal(getEnchantmentLevelUnlocalizedName(level), player.getLocale());
    }

    /**
     * Return the name of the enchantment level
     *
     * @param level  The enchantment level
     * @param locale The language of the level
     * @return The name of the level.(if level is greater than 10, it will only return the number of the level)
     */
    @Nonnull
    public static String getEnchantmentLevelName(int level, @Nonnull String locale) {
        return translateToLocal(getEnchantmentLevelUnlocalizedName(level), locale);
    }

    /**
     * Return the unlocalized name of the enchantment(Minecraft convention)
     *
     * @param enchantment The enchantment
     * @return The unlocalized name.
     */
    @Nonnull
    public static String getEnchantmentUnlocalizedName(@Nonnull Enchantment enchantment) {
        return TranslationHelper.PROVIDER.getEnchantmentTranslationKey(enchantment);
    }

    /**
     * Return the name of the enchantment.
     *
     * @param enchantment The enchantment
     * @param player      The receiver of the name
     * @return The name of the enchantment
     */
    @Nonnull
    public static String getEnchantmentName(@Nonnull Enchantment enchantment, @Nonnull Player player) {
        return getEnchantmentName(enchantment, player.getLocale());
    }

    /**
     * Return the name of the enchantment.
     *
     * @param enchantment The enchantment
     * @param locale      The language of the name
     * @return The name of the enchantment
     */
    @Nonnull
    public static String getEnchantmentName(@Nonnull Enchantment enchantment, @Nonnull String locale) {
        return translateToLocal(getEnchantmentUnlocalizedName(enchantment), locale);
    }

    /**
     * Return the display name of the enchantment(with level).
     *
     * @param enchantment The enchantment
     * @param level       The enchantment level
     * @param player      The receiver of the name
     * @return The name of the item
     */
    @Nonnull
    public static String getEnchantmentDisplayName(@Nonnull Enchantment enchantment, int level,
                                                   @Nonnull Player player) {
        return getEnchantmentDisplayName(enchantment, level, player.getLocale());
    }

    /**
     * Return the display name of the enchantment(with level).
     *
     * @param enchantment The enchantment
     * @param level       The enchantment level
     * @param locale      The language of the name
     * @return The name of the item
     */
    @Nonnull
    public static String getEnchantmentDisplayName(@Nonnull Enchantment enchantment, int level,
                                                   @Nonnull String locale) {
        String name = getEnchantmentName(enchantment, locale);
        String enchLevel = getEnchantmentLevelName(level, locale);
        return name + (!enchLevel.isEmpty() ? " " + enchLevel : "");
    }

    /**
     * Return the display name of the enchantment(with level).
     *
     * @param entry  The Entry of an enchantment with level The type is {@code Map.Entry<Enchantment, Integer>}
     * @param locale The language of the name
     * @return The name of the item
     */
    @Nonnull
    public static String getEnchantmentDisplayName(@Nonnull Map.Entry<Enchantment, Integer> entry,
                                                   @Nonnull String locale) {
        return getEnchantmentDisplayName(entry.getKey(), entry.getValue(), locale);
    }

    /**
     * Return the display name of the enchantment(with level).
     *
     * @param entry  The Entry of an enchantment with level The type is {@code Map.Entry<Enchantment, Integer>}
     * @param player The receiver of the name
     * @return The name of the item
     */
    public static String getEnchantmentDisplayName(@Nonnull Map.Entry<Enchantment, Integer> entry,
                                                   @Nonnull Player player) {
        return getEnchantmentDisplayName(entry.getKey(), entry.getValue(), player);
    }

    /**
     * Translate unlocalized entry to localized entry.
     *
     * @param translationKey The unlocalized entry.
     * @param locale         The language to be translated to.
     * @return The localized entry. If the localized entry doesn't exist, it will first look up the fallback language
     * map. If the entry still doesn't exist, then return the unlocalized name.
     */
    public static String translateToLocal(@Nonnull String translationKey, @Nonnull String locale) {
        return LangRegistry.getLocaleMap(locale).translate(translationKey);
    }
}
