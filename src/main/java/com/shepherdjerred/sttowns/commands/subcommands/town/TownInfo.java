package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.Bukkit;

import java.util.UUID;

public class TownInfo extends AbstractTownCommand {

    public TownInfo(TownNodeRegister register) {
        super(register, new NodeInfo(
                "info",
                "stTowns.town.info",
                "Show info about a town",
                "/town info [name]",
                0,
                true
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        Town town;

        if (args.length > 0) {
            town = towns.get(args[0]);
            if (town == null) {
                sender.sendMessage(parser.colorString(true, "town.townDoesntExist", args[0]));
                return;
            }
        } else {
            TownPlayer townPlayer = townPlayers.get(sender);
            if (townPlayer.hasTown()) {
                town = townPlayer.getTown();
            } else {
                sender.sendMessage(parser.colorString(true, "town.info.noTown"));
                return;
            }
        }

        String nation;
        if (town.getNation() == null) {
            nation = "None";
        } else {
            nation = town.getNation().getName();
        }

        // TODO Don't use the Bukkit API here
        String members = "";
        for (UUID uuid : town.getMembers()) {
            members = members.concat(Bukkit.getOfflinePlayer(uuid).getName() + ", ");
        }
        members = members.substring(0, members.length() - 2);

        sender.sendMessage(parser.colorString(false, "town.info", town.getName(), members, nation, town.getMotd(), town.isOpen(), town.getRemainingPlots(Plot.PlotType.CENTER), town.getRemainingPlots(Plot.PlotType.NORMAL)));

    }

}
