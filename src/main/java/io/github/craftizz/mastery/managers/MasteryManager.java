package io.github.craftizz.mastery.managers;

import com.google.common.base.Preconditions;
import io.github.craftizz.mastery.mastery.RegistrableMastery;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public class MasteryManager {

    private final EnumMap<Material, RegistrableMastery> registeredMasteries;

    public MasteryManager() {
        this.registeredMasteries = new EnumMap<>(Material.class);
    }

    /**
     * Adds a mastery to the registered masteries
     *
     * @param registrableMastery the mastery to be registered
     */
    public void addMastery(@NotNull final RegistrableMastery registrableMastery) {
        Preconditions.checkNotNull(registrableMastery);

        registeredMasteries.put(registrableMastery.getMaterial(), registrableMastery);
    }

    /**
     * Get the mastery of the material. This can return null
     *
     * @param material the material of the mastery
     * @return if exists, returns the registered mastery, else null
     */
    public @Nullable RegistrableMastery getMastery(@NotNull final Material material) {

        return registeredMasteries.get(material);
    }

}
