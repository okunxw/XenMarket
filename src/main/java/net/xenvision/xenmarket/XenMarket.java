package net.xenvision.xenmarket;

import net.xenvision.xenmarket.listener.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;
import net.xenvision.xenmarket.currency.CurrencyManager;
import net.xenvision.xenmarket.command.XenMarketCommand;
import net.xenvision.xenmarket.database.PlayerDataManager;
import net.xenvision.xenmarket.economy.EconomyManager;

public class XenMarket extends JavaPlugin {

    private static XenMarket instance;
    private CurrencyManager currencyManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        currencyManager = new CurrencyManager();
        currencyManager.loadCurrencies();

        playerDataManager = new PlayerDataManager(this);

        // Инициализация Vault
        if (!EconomyManager.setup()) {
            getLogger().severe("Vault или плагин экономики не найден! Плагин будет отключён.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getCommand("xenmarket").setExecutor(new XenMarketCommand());
        getLogger().info("XenMarket enabled!");
    }

    public CurrencyManager getCurrencyManager() {
        return currencyManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    @Override
    public void onDisable() {
        getLogger().info("XenMarket disabled!");
    }

    public static XenMarket getInstance() {
        return instance;
    }
}