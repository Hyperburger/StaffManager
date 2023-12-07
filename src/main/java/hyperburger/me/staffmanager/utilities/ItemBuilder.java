package hyperburger.me.staffmanager.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ItemBuilder class for creating custom ItemStacks with ease.
 */
public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    /**
     * Constructor to initialize ItemBuilder with a Material.
     *
     * @param material Material of the item.
     */
    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
    }

    /**
     * Sets the display name of the item with color support.
     *
     * @param name Display name.
     * @return ItemBuilder instance.
     */
    public ItemBuilder setName(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    /**
     * Adds multiple lore lines to the item with color support.
     *
     * @param lores List of lore lines.
     * @return ItemBuilder instance.
     */
    public ItemBuilder addLore(List<String> lores) {
        List<String> existingLores = meta.getLore();
        if (existingLores == null) {
            existingLores = new ArrayList<>();
        }
        for (String lore : lores) {
            existingLores.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        meta.setLore(existingLores);
        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment Enchantment type.
     * @param level       Enchantment level.
     * @return ItemBuilder instance.
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Adds an ItemFlag to the item.
     *
     * @param flag ItemFlag type.
     * @return ItemBuilder instance.
     */
    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    /**
     * Builds the ItemStack.
     *
     * @return Final ItemStack.
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
