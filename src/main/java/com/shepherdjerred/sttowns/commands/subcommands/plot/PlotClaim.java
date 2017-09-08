package com.shepherdjerred.sttowns.commands.subcommands.plot;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.PlotNodeRegister;
import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.PlotLocation;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlotClaim extends AbstractPlotCommand {

    public PlotClaim(PlotNodeRegister register) {
        super(register, new NodeInfo(
                "claim",
                "stTowns.town.claim",
                "Claim a plot for your town",
                "/town claim [normal, center]",
                0,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        Plot.PlotType plotType;

        if (args.length > 0) {
            if (EnumUtils.isValidEnum(Plot.PlotType.class, args[0].toUpperCase())) {
                plotType = Plot.PlotType.valueOf(args[0].toUpperCase());
            } else {
                sender.sendMessage("Invalid plot type");
                return;
            }
        } else {
            plotType = Plot.PlotType.NORMAL;
        }

        TownPlayer claimingTownPlayer = townPlayers.get(sender.getPlayer());
        Player claimingPlayer = sender.getPlayer();
        Chunk chunkToBeClaimed = claimingPlayer.getLocation().getChunk();

        int chunkToBeClaimedX = chunkToBeClaimed.getX();
        int chunkToBeClaimedZ = chunkToBeClaimed.getZ();
        UUID chunkToBeClaimedWorld = chunkToBeClaimed.getWorld().getUID();

        PlotLocation plotLocation = new PlotLocation(chunkToBeClaimedX, chunkToBeClaimedZ, chunkToBeClaimedWorld);

        try {
            plotController.createPlot(claimingTownPlayer, plotLocation, plotType);
            sender.sendMessage("Plot claimed");
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
        }

    }

}
