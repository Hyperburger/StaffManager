package hyperburger.me.staffmanager.listeners;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.inventory.PageMenu;
import hyperburger.me.staffmanager.inventory.StaffInventory;
import hyperburger.me.staffmanager.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ClickListener implements Listener {

    private final StaffManager staffManager;
    private final StaffInventory staffInventory;
    public ClickListener(StaffManager staffManager){
        this.staffManager = staffManager;
        staffInventory = staffManager.getStaffInventory();

    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        PageMenu pageMenu = staffManager.getPageMenu();

        // Handle pagination arrows
        if (clickedItem.getType() == Material.ARROW) {
            String displayName = clickedItem.getItemMeta().getDisplayName();

            if ("Next Page".equals(displayName)) {
                pageMenu.nextPage(player);
                pageMenu.openPage(player, pageMenu.getCurrentPage());
            } else if ("Previous Page".equals(displayName)) {
                pageMenu.prevPage(player);
                pageMenu.openPage(player, pageMenu.getCurrentPage());
            }

            event.setCancelled(true);
            return;
        }

        // Handle player head clicks
        if (clickedItem.getType() == Material.PLAYER_HEAD) {
            NamespacedKey key = new NamespacedKey(staffManager, "targetPlayer");

            // Assuming the player head's display name is the target player's name
            String targetPlayerName = clickedItem.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);

            // Create a new inventory for player settings
            Inventory settingsMenu = Bukkit.createInventory(null, 9, "Settings for " + targetPlayerName);

            // Add items for mute, ban, kick, etc.
            ItemStack itemStack = new ItemBuilder(Material.REDSTONE_BLOCK).setName("Mute Player").build();
            ItemStack banItem = new ItemBuilder(Material.BARRIER).setName("Ban Player").build();
            ItemStack kickItem = new ItemBuilder(Material.TNT).setName("Kick Player").build();

            // Set PersistentDataContainer for these items
            ItemMeta muteMeta = itemStack.getItemMeta();
            ItemMeta banMeta = banItem.getItemMeta();
            ItemMeta kickMeta = kickItem.getItemMeta();

            muteMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, targetPlayerName);
            banMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, targetPlayerName);
            kickMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, targetPlayerName);

            itemStack.setItemMeta(muteMeta);
            banItem.setItemMeta(banMeta);
            kickItem.setItemMeta(kickMeta);

            settingsMenu.setItem(0, itemStack);
            settingsMenu.setItem(1, banItem);
            settingsMenu.setItem(2, kickItem);


            // Open the settings menu
            player.openInventory(settingsMenu);
        }

        if (!event.getView().getTitle().startsWith("Settings for ")) return;
        event.setCancelled(true);

        int clickedSlot = event.getRawSlot();

        // Set metadata on the player to store the target player's name
        NamespacedKey key = new NamespacedKey(staffManager, "targetPlayer");
        String targetPlayerName = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);


        if (targetPlayerName == null) return;

        // Execute commands based on clicked slot
        switch (clickedSlot) {
            case 0:  // Mute
                Bukkit.dispatchCommand(player, "mute " + targetPlayerName);
                break;
            case 1:  // Ban
                Bukkit.dispatchCommand(player, "ban " + targetPlayerName);
                break;
            case 2:  // Kick
                System.out.print(" kick " + targetPlayerName);
                Bukkit.dispatchCommand(player, "kick " + targetPlayerName);
                System.out.print(" kick " + targetPlayerName);
                break;
            default:
                break;
        }
        System.out.print(" kick " + targetPlayerName);

        player.closeInventory();
        Bukkit.broadcastMessage(" " + targetPlayerName);
    }

}
