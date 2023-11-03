package jp.takejohn.shulkerpot.bukkit.commands;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import jp.takejohn.shulkerpot.bukkit.entity.HumanEntities;
import jp.takejohn.shulkerpot.bukkit.entity.Players;
import jp.takejohn.shulkerpot.bukkit.resources.LocaleSpecificStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Locale;

public class CommandSulkerpot implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player) || HumanEntities.isOpeningExternalInventory(player)) {
            sender.sendMessage(LocaleSpecificStrings.get("command.main.error_not_player"));
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
        final @NotNull Locale locale = Players.getLocale(player);
        final @NotNull String state = LocaleSpecificStrings.get(locale, enabled ? "command.main.enabled" : "command.main.disabled");
        player.sendMessage(LocaleSpecificStrings.format(locale, "command.main.pattern", ShulkerPot.getPlugin().getName(), state));
    }

}
