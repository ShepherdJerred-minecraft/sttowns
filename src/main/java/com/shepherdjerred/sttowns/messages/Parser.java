package com.shepherdjerred.sttowns.messages;

import com.shepherdjerred.riotbase.messages.AbstractParser;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ResourceBundle;

public class Parser extends AbstractParser {

    public Parser(ResourceBundle resourceBundle) {
        super(resourceBundle);
    }

    public static void broadcastMessage(List<Player> players, String message) {
        players.forEach(player -> player.sendMessage(message));
    }

}
