package com.shepherdjerred.sttowns.commands.subcommands.plot;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.PlotNodeRegister;
import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.PlotLocation;

public class PlotInfo extends AbstractPlotCommand {

    public PlotInfo(PlotNodeRegister register) {
        super(register, new NodeInfo(
                "info",
                "stTowns.plot.info",
                "Show information about the plot you're in",
                "/plot info",
                0,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        PlotLocation plotLocation = new PlotLocation(sender.getPlayer().getLocation().getChunk());

        if (!plots.contains(plotLocation)) {
            sender.sendMessage("This plot isn't claimed");
            return;
        }

        Plot plot = plots.getPlot(plotLocation);

        sender.sendMessage(plot.getTown().getName());
        sender.sendMessage(plot.getPlotLocation().toString());

    }


}
