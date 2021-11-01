package io.github.craftizz.mastery.managers;

import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.data.PlayerDataLoader;
import io.github.craftizz.mastery.mastery.MasteryLevel;
import io.github.craftizz.mastery.mastery.RegistrableMastery;
import io.github.craftizz.mastery.player.PlayerData;
import io.github.craftizz.mastery.player.PlayerMasteryProgress;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class PlayerDataManager {

    private final Mastery plugin;
    private final MasteryManager masteryManager;
    private final PlayerDataLoader playerDataLoader;

    private final HashMap<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(@NotNull final Mastery plugin) {
        this.plugin = plugin;
        this.masteryManager = plugin.getMasteryManager();
        this.playerDataLoader = plugin.getPlayerDataLoader();
        this.playerDataMap = new HashMap<>();
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
                final Player player = Bukkit.getPlayer(uniqueId);

                playerMasteryProgress.setGoal(nextMastery.getGoal());

                if (player != null) {
                    nextMastery.executeCommand(player);
                }

            }
        }

        // Create a mastery progress if absent
        else {

            final MasteryLevel nextMastery = registrableMastery.getNextMasteryGoal(0);
            final PlayerMasteryProgress newPlayerMasteryProgress = new PlayerMasteryProgress(0, nextMastery.getGoal());

            playerData.addMastery(material, newPlayerMasteryProgress);
        }
    }

    /**
     * Get the player data of the player. If absent, it will
     * load the data synchronously as it is needed immediately
     *
     * @param uniqueId the uniqueId of the player
     * @return the playerData of the player
     */
    public PlayerData getPlayerData(final UUID uniqueId) {

        final PlayerData playerData = playerDataMap.get(uniqueId);

        if (playerData != null) {
            return playerData;
        }

        return loadPlayerData(uniqueId, false);
    }

    /**
     * Load the playerData from the Database
     *
     * @param uniqueId the uniqueId of the player
     * @param async if should run asynchronously
     * @return the playerData of the player
     */
    public PlayerData loadPlayerData(final UUID uniqueId,
                                     final boolean async) {

        PlayerData playerData = playerDataMap.get(uniqueId);

        if (playerData != null) {
            return playerData;
        }
        else if (async) {
            playerData = playerDataLoader.loadAsync(uniqueId);
        } else {
            playerData = playerDataLoader.load(uniqueId);
        }

        playerDataMap.put(uniqueId, playerData);
        return playerData;
    }

    /**
     * Saves all the player data in the cache. This will
     * also remove all the player data that are not
     * online to free up memory space
     */
    public void saveAllPlayerData() {

        final Iterator<PlayerData> playerDataIterator = playerDataMap.values().iterator();

        while (playerDataIterator.hasNext()) {

            final PlayerData playerData = playerDataIterator.next();
            playerDataLoader.save(playerData);

            final Player player = Bukkit.getPlayer(playerData.getUniqueId());

            if (player == null) {

                playerDataLoader.unloadPlayerFile(player.getUniqueId());
                playerDataIterator.remove();
            }
        }

    }

    /**
     * Starts the saving of all player data in the cache.
     * This runs asynchronously
     */
    public void startSaving() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::saveAllPlayerData, 600, 600);
    }

}
