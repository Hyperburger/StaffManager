package hyperburger.me.staffmanager.commands.subcommands;

import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.commands.SubCommand;
import hyperburger.me.staffmanager.inventory.PageMenu;
import hyperburger.me.staffmanager.utilities.Uti;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class PlayerList extends SubCommand {
    public PlayerList(StaffManager staffManager) {
        super(staffManager);
    }

    @Override
    public String getName() {
        return "playerlist";
    }

    @Override
    public String getDescription() {
        return "Shows all the players online";
    }

    @Override
    public String getSyntax() {
        return "/sm playerlist";
    }
    @Override
    public String permission(){
        return "staffmanager.playerlist  " +
                "  " +
                "" +
                "";
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        List<ItemStack> playerHeads = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();

            meta.setOwningPlayer(onlinePlayer);
            meta.setDisplayName(Uti.colorize(staffManager.getConfig().getString("General Settings.player_list_heads.name")
                    .replace("%player_name%", onlinePlayer.getDisplayName())));

            // Add player name to PersistentDataContainer
            NamespacedKey key = new NamespacedKey(staffManager, "targetPlayer");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, onlinePlayer.getName());

            // Don't forget to set the modified ItemMeta back to the ItemStack
            head.setItemMeta(meta);

            // Add the fully configured head to the list
            playerHeads.add(head);
        }

        PageMenu pageMenu = new PageMenu(staffManager);
        // Populate the PageMenu with player heads
        pageMenu.setItems(playerHeads);

        // Open the first page
        pageMenu.openPage(player, 0);
    }

}
