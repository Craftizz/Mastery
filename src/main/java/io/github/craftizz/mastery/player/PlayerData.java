package io.github.craftizz.mastery.player;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;

    private final EnumMap<Material, PlayerMasteryProgress> masteries;

    public PlayerData(@NotNull final UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.masteries = new EnumMap<>(Material.class);
    }

    /**
     * Adds a mastery to the playerData
     *
     * @param material the material for the mastery
     * @param playerMasteryProgress the progress of the mastery
     */
    public void addMastery(final Material material,
                           final PlayerMasteryProgress playerMasteryProgress) {

        masteries.put(material, playerMasteryProgress);
    }

    /**
     * Get the current mastery progress of the player
     *
     * @param material the material of the mastery
     * @return the mastery progress of the player
     */
    public PlayerMasteryProgress getMasteryProgress(final Material material) {

        return masteries.get(material);
    }

    /**
     * @return the masteries of the player
     */
    public EnumMap<Material, PlayerMasteryProgress> getMasteries() {
        return masteries;
    }

    /**
     * @return the uniqueId of the player
     */
    public UUID getUniqueId() {
        return uniqueId;
    }
}
