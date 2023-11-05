package jp.takejohn.shulkerpot.bukkit.command.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

class TerminalArgumentNode implements ArgumentNode {

    private final String name;

    TerminalArgumentNode(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull List<@NotNull ArgumentNode> children() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

}
