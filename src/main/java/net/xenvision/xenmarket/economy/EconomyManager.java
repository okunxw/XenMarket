package net.xenvision.xenmarket.economy;

import net.milkbowl.vault.economy.Economy;
import net.xenvision.xenmarket.XenMarket;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    private static Economy economy = null;

    public static boolean setup() {
        if (economy != null) return true;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }
}