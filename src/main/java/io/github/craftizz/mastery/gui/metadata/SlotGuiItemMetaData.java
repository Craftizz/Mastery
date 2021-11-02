package io.github.craftizz.mastery.gui.metadata;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlotGuiItemMetaData extends GuiItemMetaData {

    private final Material material;
    private final int slot;

    public SlotGuiItemMetaData(final @NotNull String title,
                               final @NotNull List<String> lore,
                               final @NotNull Material material,
                               final int slot) {
        super(title, lore);
        this.material = material;
        this.slot = slot;
    }

    /**
     * @return the material of an item
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @return the slot on the where the item will be placed
     */
    public int getSlot() {
        return slot;
    }
}
