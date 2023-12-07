package hyperburger.me.staffmanager.commands.subcommands;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.commands.SubCommand;
import hyperburger.me.staffmanager.utilities.Uti;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ClearChat extends SubCommand {


    public ClearChat(StaffManager staffManager) {
        super(staffManager);
    }

    @Override
    public String getName() {
        return "clearchat";
    }

    @Override
    public String getDescription() {
        return "Clears the public chat";
    }

    @Override
    public String getSyntax() {
        return "/sm clearchat";
    }

    @Override
    public String permission() {
        return "staffmanager.clearchat";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        int cleanLines = 100;

        for (int i = 0; i < cleanLines; i++){
            Bukkit.broadcastMessage(" ");
        }

        Uti.broadcastColoredMessage(staffManager.getConfig().getString("General Settings.clear_chat_message"));
    }
}
