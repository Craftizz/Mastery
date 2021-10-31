package io.github.craftizz.mastery.mastery;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RegisterableMastery {

    private final Material material;
    private final HashMap<Integer, MasteryLevel> levels;

    public RegisterableMastery(final Material material) {
        this.material = material;
        this.levels = new HashMap<>();
    }

    /**
     * Add a masteryLevel to the mastery
     *
     * @param masteryLevel the masteryLevel to be added
     */
    public void addMasteryLevel(@NotNull final MasteryLevel masteryLevel) {
        Preconditions.checkNotNull(masteryLevel);

        levels.put(masteryLevel.getLevel(), masteryLevel);
    }

    /**
     * @return the material of this mastery
     */
    public Material getMaterial() {
        return material;
    }



}
