package io.github.craftizz.mastery.mastery;

import com.google.common.base.Preconditions;
import io.github.craftizz.mastery.gui.metadata.GuiItemMetaData;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MasteryLevel {

    private final int level;
    private final int goal;

    private final GuiItemMetaData guiItemMetaData;
    private final List<String> commandsOnCompletion;

    public MasteryLevel(final int level,
                        final int goal,
                        final GuiItemMetaData guiItemMetaData,
                        final List<String> commandsOnCompletion) {

        this.level = level;
        this.goal = goal;
        this.guiItemMetaData = guiItemMetaData;
        this.commandsOnCompletion = commandsOnCompletion;
    }

    /**
     * Executes the completion command with player as a variable
     *
     * @param player the player that will be used
     *               as a placeholder
     */
    public void executeCommand(@NotNull final Player player) {

        final ConsoleCommandSender console = Bukkit.getConsoleSender();

        for (final String command : commandsOnCompletion) {

            Bukkit.dispatchCommand(console, command.replace("<player>", player.getName()));
        }
    }

    /**
     * @return the {@link GuiItemMetaData} of this mastery level
     */
    public GuiItemMetaData getGuiItemMetaData() {
        return guiItemMetaData;
    }

    /**
     * @return the level of this mastery level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the goal to be achieved for this to be completed
     */
    public int getGoal() {
        return goal;
    }
}
