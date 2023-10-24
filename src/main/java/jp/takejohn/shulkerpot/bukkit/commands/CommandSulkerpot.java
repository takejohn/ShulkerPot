package jp.takejohn.shulkerpot.bukkit.commands;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import jp.takejohn.shulkerpot.bukkit.entity.HumanEntities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class CommandSulkerpot implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player) || HumanEntities.isOpeningExternalInventory(player)) {
            sender.sendMessage("Only players can execute this command");
            return false;
        }
        if (args.length == 0) {
            sendInformingMessage(player);
            return true;
        }
        if (args.length == 1) {
            final String arg = args[0];
            if (arg.equals("on")) {
                ShulkerPot.getPlugin().getUtilizingConfig().set(player, true);
                sendInformingMessage(player);
                return true;
            } else if (arg.equals("off")) {
                ShulkerPot.getPlugin().getUtilizingConfig().set(player, false);
                sendInformingMessage(player);
                return true;
            }
        }
        player.sendMessage(MessageFormat.format("Try \"{0}\", \"{0} on\", or \"{0} off\"", label));
        return false;
    }

    private void sendInformingMessage(@NotNull Player player) {
        final boolean enabled = ShulkerPot.getPlugin().getUtilizingConfig().get(player);
        if (enabled) {
            player.sendMessage("ShulkerPot is enabled for you.");
        } else {
            player.sendMessage("ShulkerPot is disabled for you.");
        }
    }

}
