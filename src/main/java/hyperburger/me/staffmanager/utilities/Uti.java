package hyperburger.me.staffmanager.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Uti {

    /**
     * Sends a colored message to a player.
     *
     * @param player  The player to send the message to.
     * @param message The message to send.
     */
    public static void sendColoredMessage(Player player, String message) {
        player.sendMessage(translateHexColorCodes(message));
    }

    /**
     * Broadcasts a colored message to all online players.
     *
     * @param message The message to broadcast.
     */
    public static void broadcastColoredMessage(String message) {
        Bukkit.broadcastMessage(translateHexColorCodes(message));
    }

    /**
     * Converts a string with color codes to a colored string.
     *
     * @param message The message to colorize.
     * @return The colorized message.
     */
    public static String colorize(String message) {
        return translateHexColorCodes(message);
    }

    /**
     * Translates Hex color codes in a string.
     *
     * @param message The message to translate.
     * @return The translated message.
     */
    private static String translateHexColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replaceAll("&#([A-Fa-f0-9]{6})", "§x§$1".replaceAll("(.)", "§$1"));
    }

    // Add more utility methods as needed.
}

