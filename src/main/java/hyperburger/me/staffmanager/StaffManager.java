package hyperburger.me.staffmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import hyperburger.me.staffmanager.commands.CommandManager;
import hyperburger.me.staffmanager.commands.StaffChat;
import hyperburger.me.staffmanager.inventory.PageMenu;
import hyperburger.me.staffmanager.inventory.StaffInventory;
import hyperburger.me.staffmanager.listeners.ClickListener;
import hyperburger.me.staffmanager.listeners.StaffChatFeature;
import hyperburger.me.staffmanager.listeners.StaffChatListener;
import hyperburger.me.staffmanager.managers.ProfileManager;
import hyperburger.me.staffmanager.report.*;
import hyperburger.me.staffmanager.report.api.ReportController;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffManager extends JavaPlugin {

    @Getter @Setter
    private static StaffManager instance;

    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Here's where we keep our cool stuff!
    @Getter private StaffInventory staffInventory;
    @Getter private PageMenu pageMenu;
    @Getter private ProfileManager profileManager;
    @Getter private ReportMenu reportMenu;
    @Getter private CommandManager commandManager;
    @Getter private ReportConfig reportConfig;

    @Getter private ReportManager reportManager;
    private MongoDatabase database;
    private MongoClient mongoClient;

    @Override
    public void onEnable() {
        StaffManager.setInstance(this);

        // Setting up the default config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // MongoDB Connection Uri (Replace with your own!)
        String uri = "mongodb+srv://HyperBurger:pass123@cluster0.mdlmepm.mongodb.net/?retryWrites=true&w=majority";

        // MongoDB Server API settings
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        // MongoDB Client and Database initialization
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("spigot");

        // Initialize all our cool features!
        reportConfig = new ReportConfig(this);
        staffInventory = new StaffInventory(this);
        pageMenu = new PageMenu(this);
        profileManager = new ProfileManager();
        commandManager = new CommandManager(this);
        reportMenu = new ReportMenu(this);
        reportManager = new ReportManager(database);

        // Let's make sure everything's in order!
        init();
        startMessages();

        ReportController.init();
    }

    /**
     * 🌙 plugin disable, it's time to tidy up! 🌙
     */
    @Override
    public void onDisable() {
        // Closing the MongoDB client
        mongoClient.close();
    }

    /**
     * 🛠️ Initialize all commands and listeners here! 🛠️
     */
    public void init() {
        // Registering commands
        this.getCommand("staffmanager").setExecutor(new CommandManager(this));
        this.getCommand("staffchat").setExecutor(new StaffChat(this));
        this.getCommand("report").setExecutor(new ReportCommand(this));

        // Registering listeners
        final PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(new ReportInventoryListener(reportManager), this);
        manager.registerEvents(new StaffChatListener(this), this);
        manager.registerEvents(new ClickListener(this), this);
        manager.registerEvents(new StaffChatFeature(this), this);
    }

    /**
     * 📢 Shout out some start-up messages! 📢
     */

    private void startMessages(){
        final Logger logger = this.getLogger();

        for (Player player : Bukkit.getOnlinePlayers()) {
            logger.log(Level.INFO, "      -----------------  [Staff Manager]  -----------------");
            logger.severe("          WARNING! Looks like the plugin loaded");
            logger.severe("          While players are online! This might cause errors.");
            logger.log(Level.INFO, "      -----------------  ----------------  -----------------");
        }

        logger.log(Level.INFO, "  ");
        logger.log(Level.INFO, "      -----------------  [Staff Manager]  -----------------");
        logger.log(Level.INFO, "     |        Status: Successfully activated.");
        logger.log(Level.INFO, "     |        Server Information: " + this.getServer().getVersion());
        logger.log(Level.INFO, "     |   ");
        logger.log(Level.INFO, "     |        Developed by HyperBurger");
        logger.log(Level.INFO, "     |        Thanks for using my plugin ❤! ");
        logger.log(Level.INFO, "      -----------------  ----------------  -----------------");
        logger.log(Level.INFO, "  ");
    }
}
