package io.github.craftizz.mastery.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.craftizz.mastery.gui.metadata.SlotGuiItemMetaData;
import io.github.craftizz.mastery.managers.MasteryManager;
import io.github.craftizz.mastery.managers.MenuManager;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import io.github.craftizz.mastery.mastery.MasteryCategory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MasteryMainMenu extends AbstractMasteryMenu {

    private final static GuiItem borderItem = ItemBuilder
            .from(Material.GRAY_STAINED_GLASS_PANE)
            .asGuiItem();

    private final Gui gui;

    public MasteryMainMenu() {
        this.gui = Gui.gui()
                .rows(4)
                .create();
    }

    public void build(@NotNull final Player player,
                       @NotNull final MasteryManager masteryManager,
                       @NotNull final PlayerDataManager playerDataManager,
                       @NotNull final MenuManager menuManager) {

        gui.getFiller().fill(borderItem);

        for (final MasteryCategory masteryCategory : masteryManager.getMasteryCategories()) {

            final SlotGuiItemMetaData metaData = masteryCategory.getSlotGuiItemMetaData();

            gui.setItem(metaData.getSlot(), ItemBuilder
                    .from(metaData.getMaterial())
                    .asGuiItem(event -> menuManager.openMasteryMenu(player, masteryCategory)));
        }

    }

    @Override
    public void open(@NotNull Player player) {
        gui.open(player);
    }
}
