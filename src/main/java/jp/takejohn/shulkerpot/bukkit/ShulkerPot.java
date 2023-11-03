package jp.takejohn.shulkerpot.bukkit;

import jp.takejohn.shulkerpot.bukkit.commands.CommandSopen;
import jp.takejohn.shulkerpot.bukkit.commands.CommandSulkerpot;
import jp.takejohn.shulkerpot.bukkit.entity.PlayerSpecific;
import jp.takejohn.shulkerpot.bukkit.resources.ResourceBundleControl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public final class ShulkerPot extends JavaPlugin {

    private static final @NotNull String BASE_RESOURCE_BUNDLE_NAME = "lang";

    private PlayerSpecific<@NotNull Boolean> utilizingConfig;

    private final @NotNull ResourceBundle.Control resourceBundleControl = new ResourceBundleControl(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        utilizingConfig = PlayerSpecific.withInitial(player -> getConfig().getBoolean("shulkerpot.default"));
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
        Objects.requireNonNull(getCommand("shulkerpot")).setExecutor(new CommandSulkerpot());
        Objects.requireNonNull(getCommand("sopen")).setExecutor(new CommandSopen());
    }

    public PlayerSpecific<Boolean> getUtilizingConfig() {
        return utilizingConfig;
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
