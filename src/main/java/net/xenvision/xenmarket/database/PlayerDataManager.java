package net.xenvision.xenmarket.database;

import net.xenvision.xenmarket.XenMarket;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerDataManager {
    private final XenMarket plugin;
    private File dataFile;
    private FileConfiguration dataConfig;

    public PlayerDataManager(XenMarket plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        dataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public double getBalance(UUID uuid, String currencyId) {
        return dataConfig.getDouble(uuid + "." + currencyId, 0.0);
    }

    public void setBalance(UUID uuid, String currencyId, double amount) {
        dataConfig.set(uuid + "." + currencyId, amount);
        save();
    }

    public Map<String, Double> getAllBalances(UUID uuid) {
        Map<String, Double> balances = new HashMap<>();
        if (dataConfig.isConfigurationSection(uuid.toString())) {
            for (String cur : dataConfig.getConfigurationSection(uuid.toString()).getKeys(false)) {
                balances.put(cur, dataConfig.getDouble(uuid + "." + cur));
            }
        }
        return balances;
    }

    private void save() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}