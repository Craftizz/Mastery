package io.github.craftizz.mastery.listener;

import io.github.craftizz.mastery.Mastery;
import io.github.craftizz.mastery.managers.PlayerDataManager;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.UUID;

public class DropItemListener implements Listener {

    /**
     * List of blacklisted blocks that will not be registered
     */
    private static final EnumSet<Material> blacklistedBlocks = EnumSet.of(
            Material.CHEST,
            Material.BARREL,
            Material.FURNACE,
            Material.BLAST_FURNACE
    );

    /**
     * List of blacklisted entities that will not be registered
     */
    private static final EnumSet<EntityType> blacklistedEntities = EnumSet.of(
            EntityType.PLAYER,
            EntityType.DONKEY
    );

    private final PlayerDataManager playerDataManager;

    public DropItemListener(@NotNull final Mastery plugin) {
        this.playerDataManager = plugin.getPlayerDataManager();
    }

    /**
     * Listens to {@link BlockDropItemEvent} to progress a
     * mastery if broken by a player
     */
    @EventHandler
    public void onBlockDropItemEvent(final BlockDropItemEvent event) {

        final UUID uniqueId = event.getPlayer().getUniqueId();

        if (blacklistedBlocks.contains(event.getBlockState().getType())) {

            return;
        }

        for (final Item item : event.getItems()) {

            final ItemStack itemStack = item.getItemStack();

            playerDataManager.handleDrop(uniqueId,
                    itemStack.getType(),
                    itemStack.getAmount());
        }
    }

    /**
     *  Listens to {@link EntityDropItemEvent} to progress a
     *  mastery if killed by a player
     */
    @EventHandler
    public void onEntityDropItemEvent(final EntityDropItemEvent event) {

        final LivingEntity livingEntity = (LivingEntity) event.getEntity();
        final Player player = livingEntity.getKiller();

        if (player == null) {

            return;
        }

        final UUID uniqueId = player.getUniqueId();

        if (blacklistedEntities.contains(event.getEntityType())) {

            return;
        }

        final ItemStack itemStack = event.getItemDrop().getItemStack();

        playerDataManager.handleDrop(uniqueId,
                itemStack.getType(),
                itemStack.getAmount());

    }

}
