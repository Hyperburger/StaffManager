package hyperburger.me.staffmanager.commands.subcommands;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.commands.SubCommand;
import hyperburger.me.staffmanager.managers.Profile;
import hyperburger.me.staffmanager.utilities.Uti;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseInv extends SubCommand {
    public CloseInv(StaffManager staffManager) {
        super(staffManager);
    }

    @Override
    public String getName() {
        return "closeinv";
    }

    @Override
    public String getDescription() {
        return "Toggles inventory close after clicking an item.";
    }

    @Override
    public String getSyntax() {
        return "/sm closeinv";
    }

    @Override
    public String permission() {
        return "staffmanager.closeinv";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player)sender;
        Profile profile = staffManager.getProfileManager().getProfileHashMap().get(player.getUniqueId());
        if (profile.isCloseInventory()){
            profile.setCloseInventory(false);
            Uti.sendColoredMessage(player, "&cAuto Close Inventory Disabled");
        } else {
            profile.setCloseInventory(true);
            Uti.sendColoredMessage(player, "&aAuto Close Inventory Enabled");
        }
    }
}
