package jp.takejohn.shulkerpot.bukkit.command;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import jp.takejohn.shulkerpot.bukkit.config.ClickTypeKey;
import jp.takejohn.shulkerpot.bukkit.config.PlayerConfig;
import jp.takejohn.shulkerpot.bukkit.entity.PlayerSpecificConstant;
import jp.takejohn.shulkerpot.bukkit.entity.Players;
import jp.takejohn.shulkerpot.bukkit.resources.LocaleSpecificStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CommandShulkerpot implements CommandExecutor {

    private final @NotNull ShulkerPot plugin = ShulkerPot.getPlugin();

    private final @NotNull PlayerSpecificConstant<@NotNull PlayerConfig> playerSpecificConfig = plugin.getPlayerSpecificConfig();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(LocaleSpecificStrings.get(Players.getLocale(player), "command.shulkerpot.main.info_get"));
                final @NotNull PlayerConfig config = playerSpecificConfig.get(player);
                player.sendMessage(config.getMessage());
            } else {
                final String subcommand = args[0];
                switch (subcommand) {
                    case "help" -> executeHelp(player, label);
                    case "on" -> executeOn(player);
                    case "off" -> executeOff(player);
                    case "clickTypes" -> executeClickTypes(player, args);
                    default -> {
                        final @NotNull Locale locale = Players.getLocale(player);
                        player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.unknown.error.0", subcommand),
                                LocaleSpecificStrings.format(locale, "command.shulkerpot.unknown.error.1", "/" + label + " help"));
                    }
                }
            }
        } else {
            sender.sendMessage(LocaleSpecificStrings.get("command.shulkerpot.main.error_not_player"));
        }
        return true;
    }

    public void executeHelp(@NotNull Player player, @NotNull String label) {
        final @NotNull Locale locale = Players.getLocale(player);
        final @NotNull String pluginName = ShulkerPot.getPlugin().getName();
        player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.help.help",
                "/" + label + " help"));
        player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.help.on",
                "/" + label + " on", pluginName));
        player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.help.off",
                "/" + label + " off", pluginName));
        player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.help.clickTypes",
                "/" + label + " clickTypes"));
    }

    public void executeOn(@NotNull Player player) {
        playerSpecificConfig.get(player).setEnabled(true);
        player.sendMessage(LocaleSpecificStrings.format(Players.getLocale(player), "command.shulkerpot.on", plugin.getName()));
    }

    public void executeOff(@NotNull Player player) {
        playerSpecificConfig.get(player).setEnabled(false);
        player.sendMessage(LocaleSpecificStrings.format(Players.getLocale(player), "command.shulkerpot.off", plugin.getName()));
    }

    public void executeClickTypes(@NotNull Player player, @NotNull String @NotNull[] args) {
        final @NotNull PlayerConfig.@NotNull ClickTypes config = playerSpecificConfig.get(player).clickTypes();
        final @NotNull Locale locale = Players.getLocale(player);
        if (args.length == 1) {
            player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.clickTypes.info_get",
                    clickTypesToString(locale, config)));
            return;
        }
        final @NotNull String operation = args[1];
        final @NotNull List<@NotNull ClickTypeKey> clickTypes = new ArrayList<>();
        for (int i = 2 ; i < args.length ; i++) {
            final @NotNull String name = args[i];
            final @Nullable ClickTypeKey clickType = ClickTypeKey.fromName(name);
            if (clickType == null) {
                player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.clickTypes.error_unknown_type.0", name));
                player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.clickTypes.error_unknown_type.1",
                        LocaleSpecificStrings.join(locale, Arrays.stream(ClickTypeKey.values()).map(ClickTypeKey::getName).toList())));
                return;
            }
            clickTypes.add(clickType);
        }
        switch (operation) {
            case "all" -> config.setAll(true);
            case "clear" -> config.setAll(false);
            case "set" -> {
                config.setAll(false);
                config.setAll(clickTypes, true);
            }
            case "add" -> config.setAll(clickTypes, true);
            case "remove" -> config.setAll(clickTypes, false);
            default -> {
                player.sendMessage(LocaleSpecificStrings.format(locale,
                        "command.shulkerpot.clickTypes.error_invalid_operation.0", operation));
                player.sendMessage(LocaleSpecificStrings.format(locale,
                        "command.shulkerpot.clickTypes.error_invalid_operation.1",
                        LocaleSpecificStrings.join(locale, "all", "clear", "set", "add", "remove")));
                return;
            }
        }
        player.sendMessage(LocaleSpecificStrings.format(locale, "command.shulkerpot.clickTypes.info_set",
                clickTypesToString(locale, config)));
    }

    private static @NotNull String clickTypesToString(@NotNull Locale locale, @NotNull PlayerConfig.ClickTypes clickTypes) {
        return LocaleSpecificStrings.join(locale,
                Arrays.stream(ClickTypeKey.values()).filter(clickTypes::get)
                        .map(clickTypeKey -> clickTypeKey.translate(locale)).toList());
    }

}
