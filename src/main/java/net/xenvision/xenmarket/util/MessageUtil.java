package net.xenvision.xenmarket.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.stream.StreamSupport;

public class MessageUtil {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * Парсит строку с HEX и &-кодами, возвращает Adventure Component.
     */
    public static Component parse(String text) {
        if (text == null) return Component.empty();
        // Сначала заменяем &-коды на MiniMessage-теги, потом HEX
        String parsed = text
                .replace("&", "§")
                .replace("§§", "&"); // если надо экранировать &
        // MiniMessage поддерживает HEX: <#RRGGBB>
        // Паттерн: &#RRGGBB => <#RRGGBB>
        parsed = parsed.replaceAll("&#([A-Fa-f0-9]{6})", "<#$1>");
        return miniMessage.deserialize(parsed);
    }

    /**
     * Отправляет строку в чат с поддержкой HEX и форматирования.
     */
    public static void send(CommandSender to, String text) {
        to.sendMessage(parse(text));
    }

    /**
     * Конвертирует список строк в Component (для lore).
     */
    public static Component[] parseList(Iterable<String> lines) {
        return StreamSupport.stream(lines.spliterator(), false)
                .map(MessageUtil::parse)
                .toArray(Component[]::new);
    }
}