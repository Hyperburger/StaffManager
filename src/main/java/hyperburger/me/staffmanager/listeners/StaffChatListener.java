package hyperburger.me.staffmanager.listeners;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.managers.Profile;
import hyperburger.me.staffmanager.utilities.Uti;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StaffChatListener implements Listener {

    private final String prefix;
    private final String endChat;
    private final String color;
    private final StaffManager staffManager;
    public StaffChatListener(StaffManager staffManager){
        this.staffManager = staffManager;
        prefix = staffManager.getConfig().getString("General Settings.staff_chat_prefix");
        endChat = staffManager.getConfig().getString("General Settings.staff_chat_end");
        color = staffManager.getConfig().getString("General Settings.staff_chat_color");
    }

    @EventHandler
    public void onStaffChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        Profile profile = staffManager.getProfileManager().getProfileHashMap().get(player.getUniqueId());
        boolean chat = profile.isStaffChat();
        String message = String.join(" ", event.getMessage());

        if (chat){
            event.setCancelled(true);
            for (Player staffPlayer : Bukkit.getOnlinePlayers()){
                if (staffPlayer.hasPermission("staffmanager.see")){
                    Uti.sendColoredMessage(staffPlayer, prefix + " " +player.getName() + endChat + " " + color + message);
                }
            }
        }
    }

    @EventHandler
    public void onStaffJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (!staffManager.getProfileManager().exists(player.getUniqueId())){
            staffManager.getProfileManager().getProfileHashMap().put(player.getUniqueId(), new Profile());
        }
    }
}
