package io.github.craftizz.mastery.configurations;

import com.google.common.base.Preconditions;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.mastery.MasteryLevel;
import io.github.craftizz.mastery.mastery.RegistrableMastery;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationHandler {

    private final Mastery plugin;

    public ConfigurationHandler(@NotNull final Mastery plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads and reads the mastery files then proceeds
     * to register it as a valid mastery
     */
    public final void loadMasteries() {

        final File[] masteryFiles = loadMasteryFiles();
        final MasteryManager masteryManager = plugin.getMasteryManager();

        // Loop through all the mastery files
        for (final File file : masteryFiles) {

            final Yaml masteryFile = LightningBuilder
                    .fromFile(file)
                    .createYaml();

            // Loop through all the materials
            for (final String rawMaterial : masteryFile.singleLayerKeySet()) {

                final Material material = Material.getMaterial(rawMaterial);

                // Check if material is valid
                if (material == null) {

                    plugin.getLogger().severe(rawMaterial + " is not a valid material");

                    continue;
                }

                // Load mastery levels
                final List<MasteryLevel> masteryLevels = new ArrayList<>();

                for (final String level : masteryFile.singleLayerKeySet(rawMaterial)) {

                    final List<String> commands = masteryFile.getStringList(rawMaterial + "." + level + ".command-rewards");
                    final int goal = masteryFile.getInt(rawMaterial + "." + level + ".level-goal");

                    masteryLevels.add(new MasteryLevel(Integer.parseInt(level), goal, commands));
                }

                // Check if mastery is empty
                if (masteryLevels.isEmpty()) {

                    plugin.getLogger().severe("Mastery must have at least one (1) level");

                    continue;
                }

                // Register mastery
                masteryManager.addMastery(new RegistrableMastery(material, masteryLevels));
            }
        }
    }

    /**
     * Loads the master file in the mastery
     * folder of the plugin folder
     *
     * @return the files in the mastery folder
     */
    private File[] loadMasteryFiles() {

        final File[] masteryFiles = new File(plugin
                .getDataFolder()
                .getPath() + "/mastery/")
                .listFiles();

        Preconditions.checkNotNull(masteryFiles, "Failed to find mastery folder");

        return masteryFiles;
    }

}
