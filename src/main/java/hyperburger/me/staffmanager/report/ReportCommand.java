package hyperburger.me.staffmanager.report;

import hyperburger.me.staffmanager.StaffManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    private final StaffManager staffManager;
    public ReportCommand(StaffManager staffManager){
            this.staffManager = staffManager;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;

        if (strings.length > 0) {
            Player playerReported = Bukkit.getPlayer(strings[0]);

            if (playerReported != null){
            staffManager.getReportMenu().openReportMenu(player, playerReported);
            } else {
                player.sendMessage(ChatColor.DARK_RED + "This player is not online!");
            }

        } else {
            // Open gui without player name
        }


        return true;
    }
}
