package io.github.craftizz.mastery.player;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;

    private final EnumMap<Material, Integer> masteries;

    public PlayerData(@NotNull final UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.masteries = new EnumMap<>(Material.class);
    }

    /**
     * Adds a mastery to the playerData
     *
     * @param material the material for the mastery
     * @param progress the progress of the mastery
     */
    public void addMastery(final Material material,
                           final int progress) {

        masteries.put(material, progress);
    }

    /**
     * Adds the additional to the mastery of the player
     *
     * @param material the material of the mastery
     * @param additional the additional progress to
     *                   be added for the mastery
     *
     * @return the new progress of the mastery
     */
    public int progressMastery(final Material material,
                               final int additional) {

        final int progress = masteries.get(material);
        final int newProgress = progress + additional;

        masteries.put(material, newProgress);

        return newProgress;
    }

    /**
     * Get the current mastery progress of the player
     *
     * @param material the material of the mastery
     * @return the current progress of the player
     */
    public int getMasteryProgress(final Material material) {

        return masteries.getOrDefault(material, 0);
    }
}
