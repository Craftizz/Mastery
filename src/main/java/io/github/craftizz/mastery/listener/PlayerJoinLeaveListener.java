package io.github.craftizz.mastery.listener;

import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinLeaveListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerJoinLeaveListener(@NotNull final Mastery plugin) {
        this.playerDataManager = plugin.getPlayerDataManager();
    }

    /**
     *  Listeners to {@link PlayerJoinEvent} to load the playerData.
     *  This will run async to save up space
     */
    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {
        playerDataManager.loadPlayerData(event.getPlayer().getUniqueId(), true);
    }
}
