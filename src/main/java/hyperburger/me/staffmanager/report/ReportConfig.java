package hyperburger.me.staffmanager.report;

import hyperburger.me.staffmanager.StaffManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReportConfig {

    private final File file;
    @Getter public FileConfiguration reportFile;
    private final StaffManager staffManager;

    /**
     * Constructor for ReportConfig.
     * Initializes the configuration file and sets up defaults if the file is new.
     *
     * @param staffManager The instance of StaffManager.
     */
    public ReportConfig(StaffManager staffManager) {
        this.staffManager = staffManager;

        // Set up the file path for the configuration file.
        file = new File(staffManager.getDataFolder(), "report.yml");

        // Check if the file already exists, and create a new one if it doesn't.
        boolean fileJustCreated = false;
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileJustCreated = true;
            } catch (Exception e) {
                // Log the error with detailed information.
                staffManager.getLogger().severe("Couldn't create file: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Load the configuration from the file.
        reportFile = YamlConfiguration.loadConfiguration(file);

        // If the file was just created, set up the default configuration.
        if (fileJustCreated) {
            setupInventoryConfig();
        }
    }

    /**
     * Reloads the configuration file.
     */
    public void reload() {
        reportFile = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves the configuration to the file.
     */
    public void save() {
        try {
            reportFile.save(file);
        } catch (Exception e) {
            staffManager.getLogger().severe("Could not save report.yml!");
        }
    }

    /**
     * Sets default inventory configuration only if the respective keys are not already set.
     *
     * @param inventoryConfig A map containing the inventory configuration.
     */
    public void setDefaultInventoryConfig(Map<String, Object> inventoryConfig) {
        for (String key : inventoryConfig.keySet()) {
            String path = "Inventory." + key;
            if (!reportFile.isSet(path)) {
                reportFile.set(path, inventoryConfig.get(key));
            }
        }
    }

    /**
     * Sets inventory configuration, overwriting any existing values.
     *
     * @param inventoryConfig A map containing the inventory configuration.
     */
    public void setInventoryConfig(Map<String, Object> inventoryConfig) {
        for (String key : inventoryConfig.keySet()) {
            reportFile.set("Inventory." + key, inventoryConfig.get(key));
        }
    }

    /**
     * Gets the inventory configuration.
     * @return Map of the inventory configuration.
     */
    public Map<String, Object> getInventoryConfig() {
        if (reportFile.isSet("Inventory")) {
            return reportFile.getConfigurationSection("Inventory").getValues(false);
        }
        return new HashMap<>();
    }

    /**
     * Sets up the default inventory configuration.
     */
    private void setupInventoryConfig() {
        Map<String, Object> inventoryConfig = new HashMap<>();

        // Set general inventory properties
        inventoryConfig.put("fill_material", "GRAY_STAINED_GLASS_PANE");
        inventoryConfig.put("title", "&6Staff Modes");
        inventoryConfig.put("size", 27);

        // Set items
        Map<String, Object> items = new HashMap<>();

        // Configuring Item 1
        Map<String, Object> item1 = new HashMap<>();
        item1.put("slot", 3);
        item1.put("report_reason_name", "&aMute");
        item1.put("lore", Arrays.asList("  ", "&7Click to vanish", " "));
        item1.put("material", "ENDER_PEARL");
        item1.put("command_on_click", "vanish");
        items.put("item1", item1);

        // Configuring Item 2
        Map<String, Object> item2 = new HashMap<>();
        item2.put("slot", 4);
        item2.put("display_name", "&bGod Mode");
        item2.put("lore", Arrays.asList(" ", "&7Click to enable God Mode", ""));
        item2.put("material", "GOLDEN_APPLE");
        item2.put("command_on_click", "godmode");
        items.put("item2", item2);

        inventoryConfig.put("items", items);

        // Apply and save the configuration
        setDefaultInventoryConfig(inventoryConfig);
        save();
    }
}
