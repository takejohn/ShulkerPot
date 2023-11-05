package jp.takejohn.shulkerpot.bukkit.command.nodes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ArgumentNode {

    @NotNull List<@NotNull ArgumentNode> children();

    @NotNull String getName();

    default @Nullable ArgumentNode findChild(@NotNull String name) {
        for (@NotNull ArgumentNode child : children()) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

}
