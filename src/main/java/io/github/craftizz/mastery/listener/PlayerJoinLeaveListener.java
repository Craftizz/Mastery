package io.github.craftizz.mastery.listener;

import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinLeaveListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerJoinLeaveListener(@NotNull final Mastery plugin) {
        this.playerDataManager = plugin.getPlayerDataManager();
    }

    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {

    }

    @EventHandler
    public void onPlayerLeaveEvent(final PlayerQuitEvent event) {

    }
}
