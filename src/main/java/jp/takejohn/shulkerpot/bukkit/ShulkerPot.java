package jp.takejohn.shulkerpot.bukkit;

import jp.takejohn.shulkerpot.bukkit.commands.CommandSopen;
import jp.takejohn.shulkerpot.bukkit.commands.CommandSulkerpot;
import jp.takejohn.shulkerpot.bukkit.entity.PlayerSpecific;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ShulkerPot extends JavaPlugin {

    private PlayerSpecific<@NotNull Boolean> utilizingConfig;

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

    public static @NotNull ShulkerPot getPlugin() {
        return getPlugin(ShulkerPot.class);
    }

}
