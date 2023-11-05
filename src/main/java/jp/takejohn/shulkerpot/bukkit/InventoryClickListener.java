package jp.takejohn.shulkerpot.bukkit;

import jp.takejohn.shulkerpot.bukkit.config.ClickTypeKey;
import jp.takejohn.shulkerpot.bukkit.config.PlayerConfig;
import jp.takejohn.shulkerpot.bukkit.entity.HumanEntities;
import jp.takejohn.shulkerpot.bukkit.inventory.ItemStacks;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        final Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            // The entity clicked outside the inventory
            return;
        }

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        final @NotNull PlayerConfig config = ShulkerPot.getPlugin().getPlayerSpecificConfig().get(player);
        if (HumanEntities.isOpeningExternalInventory(player) || player.getGameMode() == GameMode.CREATIVE ||
                !config.enabled()) {
            return;
        }

        final @Nullable ClickTypeKey clickTypeKey = ClickTypeKey.fromClickType(event.getClick());
        if (clickTypeKey == null || !config.clickTypes().get(clickTypeKey)) {
            return;
        }

        final ItemStack itemStack = event.getCurrentItem();
        if (!ItemStacks.isShulkerBox(itemStack)) {
            return;
        }

        event.setCancelled(true);
        new BukkitRunnable() {

            @Override
            public void run() {
                ShulkerBoxInventoryAdapter.open(player, itemStack);
            }

        }.runTask(ShulkerPot.getPlugin());
    }

}
