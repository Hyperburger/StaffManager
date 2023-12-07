package hyperburger.me.staffmanager.inventory;

import hyperburger.me.staffmanager.utilities.ItemBuilder;
import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.utilities.Uti;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class StaffInventory {

    // Dependency injection of the main class
    private final StaffManager staffManager;
    private final FileConfiguration config;
    @Getter
    private Inventory inv;


    // Constructor to initialize the main class dependency
    public StaffInventory(StaffManager staffManager) {
        this.staffManager = staffManager;
        config = staffManager.getConfig();

        String titlePath = staffManager.getConfig().getString("Inventory.title");
        if (titlePath == null){
            staffManager.getLogger().log(Level.SEVERE, "[StaffManager] There is something wrong with the invetory title.");
            return;
        }

        String title = ChatColor.translateAlternateColorCodes('&', titlePath);

        // Inventory size and validation
        int size = staffManager.getConfig().getInt("Inventory.size");
        if (size % 9 != 0) {
            staffManager.getLogger().log(Level.SEVERE, "[StaffManager] You put an impossible inventory size! Please fix in the config.yml");
            return;
        }

        inv = Bukkit.createInventory(null, size, title);
    }

    /**
     * Opens a configurable inventory for the given player.
     * The inventory is populated based on the settings in config.yml.
     *
     * @param player The player for whom to open the inventory.
     */
    public void openInventory(Player player) {
        // Fetch the configuration from the main class
        FileConfiguration config = staffManager.getConfig();
        Material fillMaterial = Material.valueOf(config.getString("Inventory.fill_material"));

        // Populate the inventory with items defined in config
        for (String key : config.getConfigurationSection("Inventory.items").getKeys(false)) {
            String path = "Inventory.items." + key;
            int slot = config.getInt(path + ".slot");
            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString(path + ".display_name"));
            List<String> lore = config.getStringList(path + ".lore");

            // Inventory Material and validation
            Material material;
            try {
                material = Material.valueOf(config.getString(path + ".material").toUpperCase());
                ItemStack itemBuilder = new ItemBuilder(material)
                        .setName(displayName)
                        .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .addLore(lore)
                        .addEnchantment(Enchantment.DURABILITY, 1)
                        .build();
                inv.setItem(slot, itemBuilder);
            } catch (IllegalArgumentException e){
                staffManager.getLogger().log(Level.SEVERE, "[StaffManager] !!! WRONG MATERIAL NAME IN CONFIG.YML !!! ");
            }
        }

        for (int i = 0; i < inv.getSize(); i++){
            ItemStack fillItem = new ItemBuilder(fillMaterial)
                    .setName("&r")
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();
            ItemStack invItem = inv.getItem(i);

            if (invItem == null || invItem.getType() == Material.AIR){
                inv.setItem(i, fillItem);

            }
        }

        // Open the populated inventory for the player
        player.openInventory(inv);
    }

    /**
     * Fetches the command associated with a clicked item in the inventory.
     *
     * @param item The clicked item.
     * @return The command associated with the clicked item, or null if not found.
     */
    public String getCommandForItem(ItemStack item) {
        // Fetch the configuration from the main class
        FileConfiguration config = staffManager.getConfig();

        // Loop through all items defined in config to find a match for the clicked item
        for (String key : config.getConfigurationSection("Inventory.items").getKeys(false)) {
            String path = "Inventory.items." + key;
            Material material = Material.valueOf(config.getString(path + ".material").toUpperCase());
            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString(path + ".display_name"));

            // Check if the clicked item's material and display name match with any item in config
            if (item.getType() == material && item.getItemMeta().getDisplayName().equals(displayName)) {

                // Return the command associated with the matched item
                return config.getString(path + ".command_on_click");
            }
        }
        // Return null if no matching item is found in config
        return null;
    }

    public List<ItemStack> getItems() {
        // Return the items from the inventory
        return Arrays.asList(inv.getContents());
    }

    public String getTitle(){
        return Uti.colorize(config.getString("Inventory.title"));
    }
}