package com.shepherdjerred.sttowns.listeners;

import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.trackers.Plots;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlotListener implements Listener {

    private final Plots plots;
    private final TownPlayers townPlayers;

    public PlotListener(Plots plots, TownPlayers townPlayers) {
        this.plots = plots;
        this.townPlayers = townPlayers;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        TownPlayer townPlayer = townPlayers.get(player);

        Chunk chunk = player.getLocation().getChunk();

        if (plots.contains(chunk)) {

            Plot plot = plots.getPlot(chunk);

            // TODO Show plot name
            if (plot.getTown() != null) {
                if (townPlayer.getLastPlot() == null || !townPlayer.getLastPlot().getTown().equals(plot.getTown())) {
                    townPlayer.setLastPlotTown(plot);
                    player.sendMessage("Entering territory of " + plot.getTown());
                }
            }

        } else if (townPlayer.getLastPlot() != null) {
            townPlayer.setLastPlotTown(null);
            player.sendMessage("Entering the wilderness");
        }

    }

}
