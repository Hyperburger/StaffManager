package hyperburger.me.staffmanager.listeners;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.managers.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatFeature implements Listener {

    private final StaffManager plugin;

    public StaffChatFeature (StaffManager plugin){
        this.plugin = plugin;
    }
    public void init() {
        Bukkit.getPluginManager().registerEvents(new StaffChatFeature(plugin), plugin);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(plugin.getStaffInventory().getTitle())) return;

        // Cancel the event to prevent items from being moved
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Profile profile = plugin.getProfileManager().getProfileHashMap().get(player.getUniqueId());
        int clickSlot = event.getSlot();
        String clickCommand = plugin.getStaffInventory().getCommandForItem(event.getCurrentItem());

        // Perform commands based on the clicked slot
        if (clickCommand == null) return;
        player.performCommand(clickCommand);

        if (profile.isCloseInventory()){
            player.closeInventory();
        }
    }
}
