package hyperburger.me.staffmanager.commands;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.commands.subcommands.ClearChat;
import hyperburger.me.staffmanager.commands.subcommands.CloseInv;
import hyperburger.me.staffmanager.commands.subcommands.HelpCommand;
import hyperburger.me.staffmanager.commands.subcommands.PlayerList;
import hyperburger.me.staffmanager.utilities.Uti;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    // ArrayList to hold all sub-commands
    @Getter
    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    private final StaffManager staffManager;
    public CommandManager(StaffManager staffManager){
        this.staffManager = staffManager;

        // Registering the sub-command classes.
        subcommands.add(new CloseInv(staffManager));
        subcommands.add(new ClearChat(staffManager));
        subcommands.add(new PlayerList(staffManager));
        subcommands.add(new HelpCommand(staffManager));

    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;

        if (strings.length > 0){                                                  // Checking if any arguments were passed with the command
            for (SubCommand subCommand : subcommands){                            // Looping through all registered sub-commands
                if (strings[0].equalsIgnoreCase(subCommand.getName())) {          // Checking if the first argument matches a sub-command name
                    if (player.hasPermission(subCommand.permission())) {
                        // Executing the sub-command
                        subCommand.execute(commandSender, strings);
                        // Returning true to indicate the command was handled
                        return true;
                    } else {
                        Uti.sendColoredMessage(player, "&c[&f!&c] You do not have permission to execute that command!");
                    }
                }
            }
        }

        if (strings.length == 0){
            if (player.hasPermission("staffmanager.use")) {
                staffManager.getStaffInventory().openInventory(player);
            }
        }
        return true;
    }

}
