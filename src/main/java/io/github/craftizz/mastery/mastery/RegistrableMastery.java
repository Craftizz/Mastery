package io.github.craftizz.mastery.mastery;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RegistrableMastery {

    private final Material material;
    private final MasteryLevel[] levels;

    public RegistrableMastery(@NotNull final Material material,
                              @NotNull final List<MasteryLevel> masteryLevels) {

        Preconditions.checkNotNull(material);
        Preconditions.checkNotNull(masteryLevels);

        this.material = material;
        this.levels = masteryLevels.toArray(MasteryLevel[]::new);
    }

    /**
     * Get the next closest mastery according to the progress.
     * This will query the array using binary algorithm
     *
     * @param progress the progress to be compared to the
     *                 goal of the mastery level
     * @return the found mastery level
     */
    public MasteryLevel getNextMasteryGoal(final int progress) {

        if (progress <= levels[0].getGoal()) {

            return levels[0];
        }

        if (progress >= levels[levels.length - 1].getGoal()) {

            return levels[levels.length - 1];
        }

        int low = 0;
        int high = levels.length - 1;

        while (low <= high) {

            int middle = (high + low) / 2;

            if (progress < levels[middle].getGoal()) {

                high = middle - 1;

            } else if (progress > levels[middle].getGoal()) {

                low = middle + 1;

            } else {

                return levels[middle];
            }
        }

        return (levels[low].getGoal() - progress) < (progress - levels[high].getGoal())
                ? levels[low]
                : levels[high];
    }

    /**
     * @return the material of this mastery
     */
    public Material getMaterial() {
        return material;
    }
}
