package jp.takejohn.shulkerpot.bukkit.config;

import jp.takejohn.shulkerpot.bukkit.resources.LocaleSpecificStrings;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum ClickTypeKey {
    LEFT("left", "terms.click_type.left"),
    SHIFT_LEFT("shiftLeft", "terms.click_type.shiftLeft"),
    RIGHT("right", "terms.click_type.right"),
    SHIFT_RIGHT("shiftRight", "terms.click_type.shiftRight"),
    DOUBLE_CLICK("doubleClick", "terms.click_type.doubleClick"),
    DROP("drop", "terms.click_type.drop"),
    CONTROL_DROP("controlDrop", "terms.click_type.controlDrop"),
    SWAP_OFFHAND("swapOffhand", "terms.click_type.swapOffhand");

    private static final @NotNull List<@NotNull String> names = Arrays.stream(values()).map(ClickTypeKey::getName).toList();

    private final @NotNull String name;

    private final @NotNull String translateKey;

    ClickTypeKey(@NotNull String name, @NotNull String translateKey) {
        this.name = name;
        this.translateKey = translateKey;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String translate(Locale locale) {
        return LocaleSpecificStrings.get(locale, translateKey);
    }

    public static @Nullable ClickTypeKey fromName(@NotNull String name) {
        return switch (name) {
            case "left" -> LEFT;
            case "shiftLeft" -> SHIFT_LEFT;
            case "right" -> RIGHT;
            case "shiftRight" -> SHIFT_RIGHT;
            case "doubleClick" -> DOUBLE_CLICK;
            case "drop" -> DROP;
            case "controlDrop" -> CONTROL_DROP;
            case "swapOffhand" -> SWAP_OFFHAND;
            default -> null;
        };
    }

    public static @Nullable ClickTypeKey fromClickType(@NotNull ClickType clickType) {
        return switch (clickType) {
            case LEFT -> LEFT;
            case SHIFT_LEFT -> SHIFT_LEFT;
            case RIGHT -> RIGHT;
            case SHIFT_RIGHT -> SHIFT_RIGHT;
            case DOUBLE_CLICK -> DOUBLE_CLICK;
            case DROP -> DROP;
            case CONTROL_DROP -> CONTROL_DROP;
            case SWAP_OFFHAND -> SWAP_OFFHAND;
            default -> null;
        };
    }

    public static @NotNull List<@NotNull String> names() {
        return names;
    }

}
