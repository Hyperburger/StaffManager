package hyperburger.me.staffmanager.commands;

import hyperburger.me.staffmanager.StaffManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public abstract class SubCommand {

    protected final StaffManager staffManager;

    public SubCommand(StaffManager staffManager){
        this.staffManager = staffManager;
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract String permission();
    public abstract void execute(CommandSender sender, String[] args);
}
