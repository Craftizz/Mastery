package io.github.craftizz.mastery.gui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMasteryMenu {

    /**
     * Open the GUI menu to player
     *
     * @param player the player where the menu will be opened
     */
    public abstract void open(@NotNull final Player player);

}
