package io.github.craftizz.mastery.data;

import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.FileData;
import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.mastery.RegistrableMastery;
import io.github.craftizz.mastery.player.PlayerData;
import io.github.craftizz.mastery.player.PlayerMasteryProgress;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PlayerDataLoader {

    private static final String userDataPath = "plugins/Mastery/data/";

    private final Mastery plugin;

    private final HashMap<UUID, Json> playerFiles;

    public PlayerDataLoader(@NotNull final Mastery plugin) {
        this.plugin = plugin;
        this.playerFiles = new HashMap<>();
    }

    /**
     * Loads the player data
     *
     * @param uniqueId the uniqueId of the player
     * @return the playerData from the database
     */
    public PlayerData load(final @NotNull UUID uniqueId) {

        final MasteryManager masteryManager = plugin.getMasteryManager();

        final PlayerData playerData = new PlayerData(uniqueId);
        final Json playerFile = getOrLoadPlayerFile(uniqueId);

        for (final String materialName : playerFile.singleLayerKeySet()) {

            final Material material = Material.getMaterial(materialName);

            if (material == null) {

                continue;
            }

            final RegistrableMastery registrableMastery = masteryManager.getMastery(material);

            if (registrableMastery == null) {

                continue;
            }

            final int progress = playerFile.getInt(materialName);
            final int goal = registrableMastery.getNextMasteryGoal(progress).getGoal();

            playerData.addMastery(material, new PlayerMasteryProgress(progress, goal));
        }

        return playerData;
    }

    /**
     * Loads the playerData asynchronously
     *
     * @param uniqueId the uniqueId of the player
     * @return the playerData from the database
     */
    public PlayerData loadAsync(final @NotNull UUID uniqueId) {
        try {
            return CompletableFuture.supplyAsync(() -> load(uniqueId)).get(5, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        return load(uniqueId);
    }

    /**
     * Saves playerData to the database
     *
     * @param playerData the playerData to be saved
     */
    public void save(final @NotNull PlayerData playerData) {

        final Json playerFile = getOrLoadPlayerFile(playerData.getUniqueId());
        final FileData fileData = playerFile.getFileData();

        for (Map.Entry<Material, PlayerMasteryProgress> entry : playerData
                .getMasteries()
                .entrySet()) {

            fileData.insert(entry.getKey().toString(), entry.getValue().getProgress());
        }

        playerFile.write();
    }

    /**
     * Saves playerData to the database asynchronously
     *
     * @param playerData the playerData to be saved
     */
    public void saveAsync(final @NotNull PlayerData playerData) {
        try {
            CompletableFuture.runAsync(() -> save(playerData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get or loads the playerFile from the JSON files
     *
     * @param uniqueId the uniqueId of the player
     * @return the Json file of the playerData
     */
    public Json getOrLoadPlayerFile(final @NotNull UUID uniqueId) {

        final Json userFile = playerFiles.get(uniqueId);

        if (userFile == null) {

            final Json loadedUser = LightningBuilder
                    .fromPath(uniqueId.toString(), userDataPath)
                    .createJson();

            playerFiles.putIfAbsent(uniqueId, loadedUser);
            return loadedUser;

        }

        return userFile;
    }

    /**
     * Unloads the playerFile to save up space
     *
     * @param uniqueId the uniqueId of the player
     */
    public void unloadPlayerFile(final @NotNull UUID uniqueId) {

        playerFiles.remove(uniqueId);
    }

}
