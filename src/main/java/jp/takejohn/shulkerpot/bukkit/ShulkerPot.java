package jp.takejohn.shulkerpot.bukkit;

import jp.takejohn.shulkerpot.bukkit.command.CommandShulkerpot;
import jp.takejohn.shulkerpot.bukkit.command.ShulkerpotTabCompleter;
import jp.takejohn.shulkerpot.bukkit.config.PlayerConfig;
import jp.takejohn.shulkerpot.bukkit.config.VolatilePlayerConfig;
import jp.takejohn.shulkerpot.bukkit.entity.PlayerSpecificConstant;
import jp.takejohn.shulkerpot.bukkit.resources.ResourceBundleControl;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public final class ShulkerPot extends JavaPlugin {

    private static final @NotNull String BASE_RESOURCE_BUNDLE_NAME = "lang";

    public static final @NotNull String MAIN_COMMAND_NAME = "shulkerpot";

    private PlayerSpecificConstant<@NotNull PlayerConfig> playerSpecificConfig;

    private final @NotNull ResourceBundle.Control resourceBundleControl = new ResourceBundleControl(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        playerSpecificConfig = PlayerSpecificConstant.withInitial(VolatilePlayerConfig::new);
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
        final @NotNull PluginCommand mainCommand = Objects.requireNonNull(getCommand(MAIN_COMMAND_NAME));
        mainCommand.setExecutor(new CommandShulkerpot());
        mainCommand.setTabCompleter(new ShulkerpotTabCompleter());
    }

    public PlayerSpecificConstant<@NotNull PlayerConfig> getPlayerSpecificConfig() {
        return playerSpecificConfig;
    }

    public @NotNull ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(BASE_RESOURCE_BUNDLE_NAME, resourceBundleControl);
    }

    public @NotNull ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(BASE_RESOURCE_BUNDLE_NAME, locale, resourceBundleControl);
    }

    public static @NotNull ShulkerPot getPlugin() {
        return getPlugin(ShulkerPot.class);
    }

}
