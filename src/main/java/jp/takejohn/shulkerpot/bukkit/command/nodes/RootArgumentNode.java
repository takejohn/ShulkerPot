package jp.takejohn.shulkerpot.bukkit.command.nodes;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RootArgumentNode implements ArgumentNode {

    private static final @NotNull List<@NotNull ArgumentNode> children = List.of(SubcommandNode.values());

    @Override
    public @NotNull List<@NotNull ArgumentNode> children() {
        return children;
    }

    @Override
    public @NotNull String getName() {
        throw new UnsupportedOperationException(getClass().getName() + " does not hold a name");
    }

}
