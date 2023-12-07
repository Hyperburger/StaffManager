package hyperburger.me.staffmanager.commands.subcommands;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.commands.CommandManager;
import hyperburger.me.staffmanager.commands.SubCommand;
import hyperburger.me.staffmanager.utilities.Uti;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    public HelpCommand(StaffManager staffManager) {
        super(staffManager);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "sends all the available commands";
    }

    @Override
    public String getSyntax() {
        return "/sm help";
    }

    @Override
    public String permission(){
        return "staffmanager.help";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        Uti.sendColoredMessage(player, "    &c&m----------&c&l STAFF MANAGER &c&m----------");

        for (SubCommand subCommand : staffManager.getCommandManager().getSubcommands()){
            Uti.sendColoredMessage(player, " &c[&f*&c] &f" + subCommand.getSyntax() + " &c⪼ &7" + subCommand.getDescription());

        }
        Uti.sendColoredMessage(player, " ");
        Uti.sendColoredMessage(player, " &c[&f?&c] Coded By &fHyperBurger");
        Uti.sendColoredMessage(player, " ");
        Uti.sendColoredMessage(player,      "    &c&m----------- &c&m------------ &c&m------------");

    }
}
