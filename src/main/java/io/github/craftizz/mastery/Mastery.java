package io.github.craftizz.mastery;

import io.github.craftizz.mastery.data.PlayerDataLoader;
import io.github.craftizz.mastery.listener.DropItemListener;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mastery extends JavaPlugin {

    private PlayerDataLoader playerDataLoader;

    private MasteryManager masteryManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {

        // Data and Configuration
        this.playerDataLoader = new PlayerDataLoader(this);

        // Managers
        this.masteryManager = new MasteryManager();
        this.playerDataManager = new PlayerDataManager(this);

        // Listeners
        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new DropItemListener(this), this);

        playerDataManager.startSaving();
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllPlayerData();
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
