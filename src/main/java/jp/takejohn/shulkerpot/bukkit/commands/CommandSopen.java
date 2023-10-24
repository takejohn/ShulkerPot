package jp.takejohn.shulkerpot.bukkit.commands;

import jp.takejohn.shulkerpot.bukkit.ShulkerBoxInventoryAdapter;
import jp.takejohn.shulkerpot.bukkit.entity.HumanEntities;
import jp.takejohn.shulkerpot.bukkit.inventory.ItemStacks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandSopen implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player) || HumanEntities.isOpeningExternalInventory(player)) {
            return false;
        }
        final ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!ItemStacks.isShulkerBox(itemStack)) {
            player.sendMessage("You are not holding a shulker box in the main hand.");
            return false;
        }
        ShulkerBoxInventoryAdapter.open(player, itemStack);
        return true;
    }

}
