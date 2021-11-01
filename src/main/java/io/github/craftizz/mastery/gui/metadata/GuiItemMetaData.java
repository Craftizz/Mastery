package io.github.craftizz.mastery.gui.metadata;

import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuiItemMetaData {

   private final String title;
   private final List<String> lore;

    public GuiItemMetaData(@NotNull final String title,
                           @NotNull final List<String> lore) {

        this.title = title;
        this.lore = lore;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLore() {
        return lore;
    }
}
