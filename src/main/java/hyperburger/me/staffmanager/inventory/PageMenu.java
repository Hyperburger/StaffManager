package hyperburger.me.staffmanager.inventory;

import hyperburger.me.staffmanager.StaffManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PageMenu {

    private Inventory inventory;  // The inventory to display
    private List<ItemStack> items;  // The list of items to paginate
    @Getter
    private int currentPage = 0;  // The current page number
    private final StaffManager staffManager;

    public PageMenu(StaffManager staffManager) {
        this.staffManager = staffManager;
        this.items = staffManager.getStaffInventory().getItems();  // Get the items from StaffInventory
    }

    // Method to open a specific page
    public void openPage(Player player, int page) {
        // Create a new inventory instance
        String title = staffManager.getStaffInventory().getTitle();
        int size = staffManager.getStaffInventory().getInv().getSize();
        this.inventory = Bukkit.createInventory(null, size, title);

        int startIndex = page * inventory.getSize();  // Calculate the start index for this page
        // Populate the inventory with items for this page
        for (int i = 0; i < inventory.getSize(); i++) {
            if (startIndex + i >= items.size()) break;
            inventory.setItem(i, items.get(startIndex + i));
        }

        // Add "Previous Page" button
        if (currentPage > 0) {
            ItemStack prevButton = new ItemStack(Material.ARROW);
            ItemMeta prevMeta = prevButton.getItemMeta();
            prevMeta.setDisplayName("Previous Page");
            prevButton.setItemMeta(prevMeta);
            inventory.setItem(inventory.getSize() - 2, prevButton);
        }

        // Add "Next Page" button
        if (startIndex + inventory.getSize() - 2 < items.size()) {
            ItemStack nextButton = new ItemStack(Material.ARROW);
            ItemMeta nextMeta = nextButton.getItemMeta();
            nextMeta.setDisplayName("Next Page");
            nextButton.setItemMeta(nextMeta);
            inventory.setItem(inventory.getSize() - 1, nextButton);
        }
        player.openInventory(this.inventory);
        currentPage = page;  // Update the current page
    }

    public void nextPage(Player player) {
        openPage(player, currentPage + 1);
    }

    public void prevPage(Player player) {
        openPage(player, currentPage - 1);
    }
    public void setItems(List<ItemStack> items) {
        this.items = items;
    }


}
