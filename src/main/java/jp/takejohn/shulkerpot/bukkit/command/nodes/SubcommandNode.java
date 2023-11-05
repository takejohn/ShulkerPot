package jp.takejohn.shulkerpot.bukkit.command.nodes;

import jp.takejohn.shulkerpot.bukkit.config.ClickTypeKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

enum SubcommandNode implements ArgumentNode {
    HELP("help", "help", "on", "off", "clickTypes"),
    ON("on"),
    OFF("off"),
    CLICK_TYPES("clickTypes", new TerminalArgumentNode("all"), new TerminalArgumentNode("clear"),
            new MultipleSelectionNode("set", ClickTypeKey.names()),
            new MultipleSelectionNode("add", ClickTypeKey.names()),
            new MultipleSelectionNode("remove", ClickTypeKey.names()));

    private final @NotNull List<@NotNull ArgumentNode> children;

    private final @NotNull String name;

    SubcommandNode(@NotNull String name) {
        this.children = Collections.emptyList();
        this.name = name;
    }

    SubcommandNode(@NotNull String name, @NotNull ArgumentNode... children) {
        this.children = List.of(children);
        this.name = name;
    }

    SubcommandNode(@NotNull String name, @NotNull String... children) {
        this.children = Stream.of(children).map(s -> (ArgumentNode)new TerminalArgumentNode(s)).toList();
        this.name = name;
    }

    @Override
    public @NotNull List<@NotNull ArgumentNode> children() {
        return children;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

}
