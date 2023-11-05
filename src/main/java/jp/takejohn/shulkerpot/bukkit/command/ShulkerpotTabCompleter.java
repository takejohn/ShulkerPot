package jp.takejohn.shulkerpot.bukkit.command;

import jp.takejohn.shulkerpot.bukkit.command.nodes.ArgumentNode;
import jp.takejohn.shulkerpot.bukkit.command.nodes.RootArgumentNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShulkerpotTabCompleter implements TabCompleter {

    public static final @NotNull List<@NotNull String> CLICK_TYPES = List.of("left", "shiftLeft", "right", "shiftRight",
            "doubleClick", "drop", "controlDrop", "swapOffhand");

    private static final @NotNull RootArgumentNode ROOT_COMMAND_NODE = new RootArgumentNode();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args) {
        @NotNull ArgumentNode node = ROOT_COMMAND_NODE;
        for (@NotNull String arg : args) {
            @Nullable ArgumentNode maybeNode = node.findChild(arg);
            if (maybeNode == null) {
                return node.children().stream().map(ArgumentNode::getName).filter(s -> s.contains(arg)).toList();
            }
            node = maybeNode;
        }
        return node.children().stream().map(ArgumentNode::getName).toList();
    }

}
