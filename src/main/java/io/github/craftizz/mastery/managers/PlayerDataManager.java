package io.github.craftizz.mastery.managers;

import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.mastery.MasteryLevel;
import io.github.craftizz.mastery.mastery.RegistrableMastery;
import io.github.craftizz.mastery.player.PlayerData;
import io.github.craftizz.mastery.player.PlayerMasteryProgress;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private final MasteryManager masteryManager;

    private final HashMap<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(@NotNull final Mastery plugin) {
        this.masteryManager = plugin.getMasteryManager();
        this.playerDataMap = new HashMap<>();
    }

    public PlayerData getPlayerData(final UUID uniqueId) {

        // Add a load

        return playerDataMap.get(uniqueId);
    }

    public void loadPlayerData(final UUID uniqueId,
                               final boolean async) {

    }

    /**
     * Handles the drop of events
     *
     * @param uniqueId the uniqueId of the player
     * @param material the material of the mastery
     * @param drops the amount of drops to be added
     */
    public void handleDrop(@NotNull final UUID uniqueId,
                           @NotNull final Material material,
                           final int drops) {

        // Get Mastery
        final RegistrableMastery registrableMastery = masteryManager.getMastery(material);

        if (registrableMastery == null) {

            return;
        }

        // Get Player Data and Mastery Progress
        final PlayerData playerData = getPlayerData(uniqueId);
        final PlayerMasteryProgress playerMasteryProgress = playerData.getMasteryProgress(material);

        // Progress if existent and higher
        if (playerMasteryProgress != null) {

            final int newProgress = playerMasteryProgress.progress(drops);

            if (newProgress >= playerMasteryProgress.getGoal()) {

                final MasteryLevel nextMastery = registrableMastery.getNextMasteryGoal(newProgress);
                playerMasteryProgress.setGoal(nextMastery.getGoal());
            }

        }

        // Create a mastery progress if absent
        else {

            final MasteryLevel nextMastery = registrableMastery.getNextMasteryGoal(0);
            final PlayerMasteryProgress newPlayerMasteryProgress = new PlayerMasteryProgress(0, nextMastery.getGoal());

            playerData.addMastery(material, newPlayerMasteryProgress);
        }

    }


}
