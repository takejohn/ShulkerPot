package jp.takejohn.shulkerpot.bukkit.command;

import jp.takejohn.shulkerpot.bukkit.command.nodes.ArgumentNode;
import jp.takejohn.shulkerpot.bukkit.command.nodes.RootArgumentNode;
import jp.takejohn.shulkerpot.java.util.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class ShulkerpotTabCompleter implements TabCompleter {

    private static final @NotNull RootArgumentNode ROOT_COMMAND_NODE = new RootArgumentNode();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args) {
        @NotNull ArgumentNode node = ROOT_COMMAND_NODE;
        for (@NotNull String arg : args) {
            @Nullable ArgumentNode maybeNode = node.findChild(arg);
            if (maybeNode == null) {
                return node.children().stream().map(ArgumentNode::getName)
                        .filter(s -> Strings.containsIgnoreCase(s, arg, Locale.US)).toList();
            }
            node = maybeNode;
        }
        return node.children().stream().map(ArgumentNode::getName).toList();
    }

}
