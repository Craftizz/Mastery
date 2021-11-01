package io.github.craftizz.mastery;

import io.github.craftizz.mastery.listener.DropItemListener;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mastery extends JavaPlugin {

    private MasteryManager masteryManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {

        this.masteryManager = new MasteryManager();
        this.playerDataManager = new PlayerDataManager(this);

        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new DropItemListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MasteryManager getMasteryManager() {
        return masteryManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
