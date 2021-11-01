package io.github.craftizz.mastery;

import de.leonhard.storage.util.FileUtils;
import io.github.craftizz.mastery.configurations.ConfigurationHandler;
import io.github.craftizz.mastery.data.PlayerDataLoader;
import io.github.craftizz.mastery.listener.DropItemListener;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mastery extends JavaPlugin {

    private PlayerDataLoader playerDataLoader;
    private ConfigurationHandler configurationHandler;

    private MasteryManager masteryManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {

        extractResources();

        // Data and Configuration
        this.configurationHandler = new ConfigurationHandler(this);
        this.playerDataLoader = new PlayerDataLoader(this);

        // Managers
        this.masteryManager = new MasteryManager();
        this.playerDataManager = new PlayerDataManager(this);

        // Listeners
        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new DropItemListener(this), this);

        configurationHandler.loadMasteries();
        playerDataManager.startSaving();
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllPlayerData();
    }

    public void extractResources() {
        FileUtils.extractResourceFolderContents(getFile(), this.getDataFolder(),
                "mastery", false);
    }

    public MasteryManager getMasteryManager() {
        return masteryManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public PlayerDataLoader getPlayerDataLoader() {
        return playerDataLoader;
    }
}
