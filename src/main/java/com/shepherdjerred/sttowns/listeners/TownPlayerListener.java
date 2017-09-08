package com.shepherdjerred.sttowns.listeners;

import com.shepherdjerred.sttowns.database.TownPlayerDAO;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TownPlayerListener implements Listener {

    private final TownPlayers townPlayers;
    private final TownPlayerDAO townPlayerDAO;
    private final Parser parser;

    public TownPlayerListener(Parser parser, TownPlayers townPlayers, TownPlayerDAO townPlayerDAO) {
        this.parser = parser;
        this.townPlayers = townPlayers;
        this.townPlayerDAO = townPlayerDAO;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        TownPlayer unloadedTownPlayer = new TownPlayer(event.getPlayer());

        try {
            townPlayers.add(unloadedTownPlayer);
        } catch (TrackerException e) {
            event.getPlayer().sendMessage(parser.colorString(true, "town.exception", "load your user data", e.getMessage()));
            e.printStackTrace();
        }

        try {
            TownPlayer townPlayer = townPlayerDAO.load(player);
            if (townPlayer == null) {
                townPlayer = new TownPlayer(player);
                townPlayerDAO.insert(townPlayer);
            }
            townPlayers.remove(player);
            townPlayers.add(townPlayer);
        } catch (TrackerException e) {
            event.getPlayer().sendMessage(parser.colorString(true, "town.exception", "load your user data", e.getMessage()));
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        townPlayers.remove(event.getPlayer().getUniqueId());
    }

}
