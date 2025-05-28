package net.xenvision.xenmarket.currency;

import net.xenvision.xenmarket.XenMarket;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CurrencyManager {
    private final Map<String, Currency> currencies = new HashMap<>();

    private File currenciesFile;
    private FileConfiguration currenciesConfig;

    public void loadCurrencies() {
        XenMarket plugin = XenMarket.getInstance();

        // Создаём файл, если его нет
        currenciesFile = new File(plugin.getDataFolder(), "currencies.yml");
        if (!currenciesFile.exists()) {
            plugin.saveResource("currencies.yml", false);
        }
        currenciesConfig = YamlConfiguration.loadConfiguration(currenciesFile);

        currencies.clear();
        if (currenciesConfig.getKeys(false).isEmpty()) return;

        for (String id : currenciesConfig.getKeys(false)) {
            String name = currenciesConfig.getString(id + ".name", id);
            String symbol = currenciesConfig.getString(id + ".symbol", "");
            double minRate = currenciesConfig.getDouble(id + ".min_rate", 1);
            double maxRate = currenciesConfig.getDouble(id + ".max_rate", 1);
            double currentRate = currenciesConfig.getDouble(id + ".current_rate", 1);

            currencies.put(id, new Currency(id, name, symbol, minRate, maxRate, currentRate));
        }
    }

    public Currency getCurrency(String id) {
        return currencies.get(id);
    }

    public Collection<Currency> getAllCurrencies() {
        return currencies.values();
    }
}