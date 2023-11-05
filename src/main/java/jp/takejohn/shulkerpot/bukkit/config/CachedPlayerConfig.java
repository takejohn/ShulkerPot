package jp.takejohn.shulkerpot.bukkit.config;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;

abstract class CachedPlayerConfig implements PlayerConfig {

    private static class ClickTypesImpl implements ClickTypes {

        protected final @NotNull Set<@NotNull ClickTypeKey> trueSet = EnumSet.noneOf(ClickTypeKey.class);

        @Override
        public boolean get(@NotNull ClickTypeKey clickType) {
            return trueSet.contains(clickType);
        }

        @Override
        public void set(@NotNull ClickTypeKey clickType, boolean value) {
            if (value) {
                trueSet.add(clickType);
            } else {
                trueSet.remove(clickType);
            }
        }

    }

    private final @NotNull Player player;

    private boolean enabled;

    private final @NotNull ClickTypes clickTypes = new ClickTypesImpl();

    protected CachedPlayerConfig(@NotNull Player player) {
        this.player = player;
    }

    protected void setDefault() {
        final @NotNull FileConfiguration serverDefault = ShulkerPot.getPlugin().getConfig();
        setEnabled(serverDefault.getBoolean("shulkerpot.default.enable", true));
        clickTypes.set(ClickTypeKey.LEFT, serverDefault.getBoolean("shulkerpot.default.clickTypes.left", true));
        clickTypes.set(ClickTypeKey.SHIFT_LEFT, serverDefault.getBoolean("shulkerpot.default.clickTypes.shiftLeft", true));
        clickTypes.set(ClickTypeKey.RIGHT, serverDefault.getBoolean("shulkerpot.default.clickTypes.right", true));
        clickTypes.set(ClickTypeKey.SHIFT_RIGHT, serverDefault.getBoolean("shulkerpot.default.clickTypes.shiftRight", true));
        clickTypes.set(ClickTypeKey.DOUBLE_CLICK, serverDefault.getBoolean("shulkerpot.default.clickTypes.doubleClick", true));
        clickTypes.set(ClickTypeKey.DROP, serverDefault.getBoolean("shulkerpot.default.clickTypes.drop", true));
        clickTypes.set(ClickTypeKey.CONTROL_DROP, serverDefault.getBoolean("shulkerpot.default.clickTypes.controlDrop", true));
        clickTypes.set(ClickTypeKey.SWAP_OFFHAND, serverDefault.getBoolean("shulkerpot.default.clickTypes.swapOffhand", true));
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public @NotNull ClickTypes clickTypes() {
        return clickTypes;
    }

}
