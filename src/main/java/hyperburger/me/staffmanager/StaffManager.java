package hyperburger.me.staffmanager;

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
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * 🌟 Welcome to the StaffManager! The heart of our plugin! 🌟
 * Here we initialize, manage, and say goodbye to all our functionalities.
 */
public final class StaffManager extends JavaPlugin {

    // Here's where we keep our cool stuff!
    @Getter private StaffInventory staffInventory;
    @Getter private PageMenu pageMenu;
    @Getter private ProfileManager profileManager;
    @Getter private ReportMenu reportMenu;
    @Getter private CommandManager commandManager;
    @Getter private ReportConfig reportConfig;

    private ReportManager reportManager;
    private MongoDatabase database;
    private MongoClient mongoClient;

    /**
     * 🚀 plugin enable, let's get this party started! 🚀
     */
    @Override
    public void onEnable() {
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
        getCommand("staffmanager").setExecutor(new CommandManager(this));
        getCommand("staffchat").setExecutor(new StaffChat(this));
        getCommand("report").setExecutor(new ReportCommand(this));

        // Registering listeners
        getServer().getPluginManager().registerEvents(new ReportInventoryListener(reportManager), this);
        getServer().getPluginManager().registerEvents(new StaffChatListener(this), this);
        getServer().getPluginManager().registerEvents(new ClickListener(this), this);
        getServer().getPluginManager().registerEvents(new StaffChatFeature(this), this);
    }

    /**
     * 📢 Shout out some start-up messages! 📢
     */

    private void startMessages(){
        for (int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
            if (i > 0){
                this.getLogger().log(Level.INFO, "      -----------------  [Staff Manager]  -----------------");
                getLogger().severe("          WARNING! Looks like the plugin loaded");
                getLogger().severe("          While players are online! This might cause errors.");
                this.getLogger().log(Level.INFO, "      -----------------  ----------------  -----------------");
            }
        }

        this.getLogger().log(Level.INFO, "  ");
        this.getLogger().log(Level.INFO, "      -----------------  [Staff Manager]  -----------------");
        this.getLogger().log(Level.INFO, "     |        Status: Successfully activated.");
        this.getLogger().log(Level.INFO, "     |        Server Information: " + this.getServer().getVersion());
        this.getLogger().log(Level.INFO, "     |   ");
        this.getLogger().log(Level.INFO, "     |        Developed by HyperBurger");
        this.getLogger().log(Level.INFO, "     |        Thanks for using my plugin ❤! ");
        this.getLogger().log(Level.INFO, "      -----------------  ----------------  -----------------");
        this.getLogger().log(Level.INFO, "  ");

    }
}
