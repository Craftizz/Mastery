package io.github.craftizz.mastery.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import io.github.craftizz.mastery.mastery.MasteryCategory;
import io.github.craftizz.mastery.mastery.MasteryLevel;
import io.github.craftizz.mastery.mastery.RegistrableMastery;
import io.github.craftizz.mastery.player.PlayerData;
import io.github.craftizz.mastery.player.PlayerMasteryProgress;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MasteryMenu extends AbstractMasteryMenu {

    private final static GuiItem borderItem = ItemBuilder
            .from(Material.GRAY_STAINED_GLASS_PANE)
            .asGuiItem();

    private final static List<Integer> borderSlots = List.of(
            0, 1, 9, 10, 19, 28, 36, 37, 45, 46
    );

    private final PaginatedGui gui;

    public MasteryMenu() {
        this.gui = Gui.paginated()
                .disableAllInteractions()
                .rows(6)
                .create();
    }

    /**
     * Builds this GUI menu
     *
     * @param player the player that will be used for the data
     * @param masteryManager the instance of masteryManager from the plugin
     * @param playerDataManager the instance of playerDataManager from the plugin
     * @param masteryCategory the category requested by the player
     */
    public final void build(@NotNull final Player player,
                            @NotNull final MasteryManager masteryManager,
                            @NotNull final PlayerDataManager playerDataManager,
                            @NotNull final MasteryCategory masteryCategory) {

        gui.setItem(borderSlots, borderItem);

        // Upward and previous
        gui.setItem(18, ItemBuilder
                .from(Material.ARROW)
                .asGuiItem(event -> gui.next()));

        gui.setItem(27, ItemBuilder
                .from(Material.ARROW)
                .asGuiItem(event -> gui.previous()));

        // Get player data
        final PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId());

        // Get the materials of the category
        for (final Material material : masteryCategory.getCategoryMaterials()) {

            // Get and check if the material is a registered mastery
            final RegistrableMastery registrableMastery = masteryManager.getMastery(material);
            final PlayerMasteryProgress playerMasteryProgress = playerData.getMasteryProgress(material);

            if (registrableMastery == null) {

                continue;
            }

            // Gather the mastery levels
            final MasteryLevel[] masteryLevels = registrableMastery.getLevels();

            for (int i = 0; i < 6; i++) {

                final MasteryLevel masteryLevel = masteryLevels[i];

                // If no progress or locked yet
                if (playerMasteryProgress == null || playerMasteryProgress.getLevel() < masteryLevel.getLevel()) {

                    gui.addItem(ItemBuilder
                            .from(Material.GRAY_DYE)
                            .asGuiItem());

                }

                else {

                    gui.addItem(ItemBuilder
                            .from(material)
                            .asGuiItem());

                }
            }
        }

    }

    @Override
    public void open(@NotNull Player player) {
        gui.open(player);
    }

}
