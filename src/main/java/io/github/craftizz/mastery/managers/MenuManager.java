package io.github.craftizz.mastery.managers;

import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.gui.MasteryMainMenu;
import io.github.craftizz.mastery.gui.MasteryMenu;
import io.github.craftizz.mastery.mastery.MasteryCategory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuManager {

    private final MasteryManager masteryManager;
    private final PlayerDataManager playerDataManager;

    public MenuManager(@NotNull final Mastery plugin) {
        this.masteryManager = plugin.getMasteryManager();
        this.playerDataManager = plugin.getPlayerDataManager();
    }

    public void openMasteryMainMenu(@NotNull final Player player) {

        final MasteryMainMenu masteryMainMenu =  new MasteryMainMenu();

        masteryMainMenu.build(player, masteryManager, playerDataManager, this);
        masteryMainMenu.open(player);
    }

    public void openMasteryMenu(@NotNull final Player player,
                                @NotNull final MasteryCategory masteryCategory) {

        final MasteryMenu masteryMenu = new MasteryMenu();

        masteryMenu.build(player, masteryManager, playerDataManager, masteryCategory);
        masteryMenu.open(player);
    }

}
