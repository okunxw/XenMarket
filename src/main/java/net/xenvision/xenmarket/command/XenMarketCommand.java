package net.xenvision.xenmarket.command;

import net.xenvision.xenmarket.XenMarket;
import net.xenvision.xenmarket.database.PlayerDataManager;
import net.xenvision.xenmarket.currency.Currency;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XenMarketCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("currencies")) {
            sender.sendMessage("Список валют:");
            for (Currency currency : XenMarket.getInstance().getCurrencyManager().getAllCurrencies()) {
                sender.sendMessage(" - " + currency.getName() + " (" + currency.getSymbol() + "): " + currency.getCurrentRate());
            }
            return true;
        }

        if (args.length > 2 && args[0].equalsIgnoreCase("buy")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Только игрок может покупать валюту!");
                return true;
            }
            Player player = (Player) sender;
            String currencyId = args[1].toLowerCase();
            double amount;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage("Введите корректное количество!");
                return true;
            }
            Currency currency = XenMarket.getInstance().getCurrencyManager().getCurrency(currencyId);
            if (currency == null) {
                player.sendMessage("Валюта не найдена!");
                return true;
            }
            PlayerDataManager pdm = XenMarket.getInstance().getPlayerDataManager();
            double current = pdm.getBalance(player.getUniqueId(), currencyId);
            pdm.setBalance(player.getUniqueId(), currencyId, current + amount);
            player.sendMessage("Вы купили " + amount + " " + currency.getName() + " (" + currency.getSymbol() + ")");
            return true;
        }

        if (args.length > 2 && args[0].equalsIgnoreCase("sell")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Только игрок может продавать валюту!");
                return true;
            }
            Player player = (Player) sender;
            String currencyId = args[1].toLowerCase();
            double amount;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage("Введите корректное количество!");
                return true;
            }
            Currency currency = XenMarket.getInstance().getCurrencyManager().getCurrency(currencyId);
            if (currency == null) {
                player.sendMessage("Валюта не найдена!");
                return true;
            }
            PlayerDataManager pdm = XenMarket.getInstance().getPlayerDataManager();
            double current = pdm.getBalance(player.getUniqueId(), currencyId);
            if (current < amount) {
                player.sendMessage("Недостаточно валюты для продажи!");
                return true;
            }
            pdm.setBalance(player.getUniqueId(), currencyId, current - amount);
            player.sendMessage("Вы продали " + amount + " " + currency.getName() + " (" + currency.getSymbol() + ")");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("balance")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Только игрок может смотреть баланс!");
                return true;
            }
            Player player = (Player) sender;
            PlayerDataManager pdm = XenMarket.getInstance().getPlayerDataManager();
            sender.sendMessage("Ваш баланс по всем валютам:");
            for (Currency currency : XenMarket.getInstance().getCurrencyManager().getAllCurrencies()) {
                double bal = pdm.getBalance(player.getUniqueId(), currency.getId());
                sender.sendMessage(" - " + currency.getName() + " (" + currency.getSymbol() + "): " + bal);
            }
            return true;
        }

        sender.sendMessage("§bXenMarket §7— работает! (доступные подкоманды: currencies, buy, sell, balance)");
        return true;
    }
}