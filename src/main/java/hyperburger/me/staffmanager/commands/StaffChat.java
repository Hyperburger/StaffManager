package hyperburger.me.staffmanager.commands;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.managers.Profile;
import hyperburger.me.staffmanager.managers.ProfileManager;
import hyperburger.me.staffmanager.utilities.Uti;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {

    private final String prefix;
    private final String endChat;
    private final String color;
    private final StaffManager staffManager;
    public StaffChat(StaffManager staffManager){
        this.staffManager = staffManager;
        prefix = staffManager.getConfig().getString("General Settings.staff_chat_prefix");
        endChat = staffManager.getConfig().getString("General Settings.staff_chat_end");
        color = staffManager.getConfig().getString("General Settings.staff_chat_color");
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("[StaffManager] Can not perform this command.");
            return true;
        }

        Player player = (Player) commandSender;
        Profile playerProfile = staffManager.getProfileManager().getProfileHashMap().get(player.getUniqueId());

        if (!player.hasPermission("staffmanager.chat")) {
            commandSender.sendMessage("You do not have permission to use this command!");
            return true;
        }

        if (strings.length == 0){
            if (playerProfile == null) {
                staffManager.getLogger().severe("[StaffManager] !!! The Player Profile is null!!!");
                return true;
            }
            if (playerProfile.isStaffChat()){
                playerProfile.setStaffChat(false);
                Uti.sendColoredMessage(player, "&cSC has been toggled off.");
            } else {
                playerProfile.setStaffChat(true);
                Uti.sendColoredMessage(player, "&aSC has been toggled on.");
            }
            return true;
        }

        String message = String.join(" ", strings);
        for (Player staffPlayers : Bukkit.getOnlinePlayers()){
            if (staffPlayers.hasPermission("staffmanager.see")){
                Uti.sendColoredMessage(staffPlayers, prefix + " " +player.getName() + endChat + " " + color + message);
            }
        }


        return true;
    }
}
