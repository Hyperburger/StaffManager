package hyperburger.me.staffmanager.report;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.utilities.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ReportMenu {

    private final StaffManager staffManager;
    @Getter public final ReportConfig reportConfig;
    public ReportMenu(StaffManager  staffManager){
        this.staffManager = staffManager;
        this.reportConfig = staffManager.getReportConfig();
    }

    private Inventory createReportMenu(Player reportedPlayer){
        Inventory inventory = Bukkit.createInventory(null, 27, "Report: " + reportedPlayer.getName());

        Map<String, Object> inventoryConfig = reportConfig.getInventoryConfig();

        // This line casts the returned MemorySection to a ConfigurationSection and then gets its values as a Map
        ConfigurationSection itemsSection = reportConfig.getReportFile().getConfigurationSection("Inventory.items");

        if (itemsSection != null) {
            Map<String, Object> itemsConfig = itemsSection.getValues(false);

            for (String key : itemsConfig.keySet()) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                if (itemSection != null) {
                    Map<String, Object> itemConfig = itemSection.getValues(false);
                    ItemStack item = createItemFromConfig(itemConfig);
                    inventory.addItem(item); // Or set at a specific slot if defined in the config
                }
            }
        }

        return inventory;
    }

        private ItemStack createItemFromConfig(Map<String, Object> itemConfig) {
            // Get the material name.
            String materialName = (String) itemConfig.get("material");

            // Validate material name.
            Material material = Material.getMaterial(materialName);

            if (material == null) {
                staffManager.getLogger()
                        .warning("Invalid material " + materialName + "in report.yml. Skipping item.");
                return new ItemBuilder(Material.STONE).setName("&7Invalid material in report.yml").build();
            }

            String displayName = ChatColor.translateAlternateColorCodes('&', (String) itemConfig.get("display_name"));

            ArrayList<String> lore = new ArrayList<>();

            List<?> loreRaw = (List<?>) itemConfig.get("lore");

            if (loreRaw != null) {
                for (Object line : loreRaw) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', (String) line));
                }
            }
            return new ItemBuilder(material)
                    .setName(displayName)
                    .addLore(lore)
                    .build();
        }

        public void openReportMenu(Player player, Player playerReported) {
            player.openInventory(createReportMenu(playerReported));
        }

}

