package net.xenvision.xenmarket.listener;

import net.xenvision.xenmarket.gui.menus.MainMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getView().title().equals(MainMenu.MENU_TITLE)) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
            String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            if (displayName == null) return;

            String symbol = displayName.replaceAll("§.", "").replaceAll(".*\\((.*)\\).*", "$1").trim();

            if (event.isLeftClick()) {
                player.performCommand("xenmarket buy " + symbol + " 1");
            } else if (event.isRightClick()) {
                player.performCommand("xenmarket sell " + symbol + " 1");
            }
        }
    }
}
