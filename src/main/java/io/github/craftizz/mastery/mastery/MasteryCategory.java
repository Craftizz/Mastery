package io.github.craftizz.mastery.mastery;

import io.github.craftizz.mastery.gui.metadata.SlotGuiItemMetaData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class MasteryCategory {

    private final String categoryName;
    private final EnumSet<Material> categoryMaterials;
    private final SlotGuiItemMetaData slotGuiItemMetaData;

    public MasteryCategory(@NotNull final String categoryName,
                           @NotNull final EnumSet<Material> categoryMaterials,
                           @NotNull final SlotGuiItemMetaData slotGuiItemMetaData) {

        this.categoryName = categoryName;
        this.categoryMaterials = categoryMaterials;
        this.slotGuiItemMetaData = slotGuiItemMetaData;
    }

    /**
     * @return the name of this category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @return the materials of this category
     */
    public EnumSet<Material> getCategoryMaterials() {
        return categoryMaterials;
    }

    /**
     * @return the {@link SlotGuiItemMetaData} of this category
     */
    public SlotGuiItemMetaData getSlotGuiItemMetaData() {
        return slotGuiItemMetaData;
    }
}
