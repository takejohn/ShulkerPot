package jp.takejohn.shulkerpot.bukkit.config;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VolatilePlayerConfig extends CachedPlayerConfig {

    public VolatilePlayerConfig(@NotNull Player player) {
        super(player);
        setDefault();
    }

}
