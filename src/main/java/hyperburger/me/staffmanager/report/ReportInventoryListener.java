package hyperburger.me.staffmanager.report;

import hyperburger.me.staffmanager.report.Report;
import hyperburger.me.staffmanager.report.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ReportInventoryListener implements Listener {

    private final ReportManager reportManager;

    public ReportInventoryListener(ReportManager reportManager){
        this.reportManager = reportManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        // Check if the inventory is the report GUI
        if (inventory == null) {
            return;
        }

        // Check if the inventory has the right name
        if (!(event.getView().getTitle().startsWith("Report: "))){
            return;
        }

        // Execute code after all checks.
        event.setCancelled(true); // Prevent taking item from the GUI.

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        // Get report information
        String reportReason  = clickedItem.getItemMeta().getDisplayName();
        String reportedName = event.getView().getTitle().substring(8);

        // Create a new report
        Report report = new Report(player, Bukkit.getPlayer(reportedName), reportReason, 100);
        reportManager.addReport(player, Bukkit.getPlayer(reportedName), reportReason, 100);
        reportManager.getReports().put(report.getId(), report);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYour report has been submitted for &f" + reportedName + " &awith reason: " + reportReason));
        player.closeInventory();


    }
}
