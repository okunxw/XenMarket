package net.xenmarket.gui.menus;

import net.xenvision.xenmarket.XenMarket;
import net.xenvision.xenmarket.currency.Currency;
import net.xenvision.xenmarket.currency.CurrencyManager;
import net.xenvision.xenmarket.database.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MainMenu {

    public static String MENU_TITLE = "§bXenMarket — Валюты";

    public static void open(Player player) {
        CurrencyManager currencyManager = XenMarket.getInstance().getCurrencyManager();
        DatabaseManager databaseManager = XenMarket.getInstance().getDatabaseManager();

        List<Currency> currencies = currencyManager.getAllCurrencies();
        int size = ((currencies.size() - 1) / 9 + 1) * 9; // кратно 9
        Inventory inv = Bukkit.createInventory(null, size, MENU_TITLE);

        for (int i = 0; i < currencies.size(); i++) {
            Currency currency = currencies.get(i);

            // В качестве примера - изумруд для всех, можно добавить Material через currency.getIcon()
            ItemStack item = new ItemStack(Material.EMERALD);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e" + currency.getName() + " §7(" + currency.getSymbol() + ")");
            List<String> lore = List.of(
                    "§7Курс: §a" + currency.getCurrentRate(),
                    "§7Ваш баланс: §b" + databaseManager.getBalance(player.getUniqueId(), currency.getId()),
                    "",
                    "§aЛКМ: купить   §cПКМ: продать"
            );
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }

        player.openInventory(inv);
    }
}