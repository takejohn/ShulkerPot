package jp.takejohn.shulkerpot.bukkit.command.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MultipleSelectionNode implements ArgumentNode {

    private final @NotNull String name;

    private final @NotNull List<@NotNull String> options;

    protected MultipleSelectionNode(@NotNull String name, @NotNull Collection<@NotNull String> options) {
        this.name = name;
        this.options = new ArrayList<>(options);
    }

    @Override
    public @NotNull List<@NotNull ArgumentNode> children() {
        return options.stream().map(option -> (ArgumentNode)new MultipleSelectionNode(option, removed(options, option))).toList();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    private static @NotNull List<@NotNull String> removed(@NotNull List<@NotNull String> options, @NotNull String target) {
        return options.stream().filter(option -> !option.equals(target)).toList();
    }

}
