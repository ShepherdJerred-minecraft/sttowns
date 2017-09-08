package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.Bukkit;

public class TownJoin extends AbstractTownCommand {

    public TownJoin(TownNodeRegister register) {
        super(register, new NodeInfo(
                "join",
                "stTowns.town.join",
                "Join an open town on the server",
                "/town join <towns name>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer player = townPlayers.get(sender);

        if (!towns.contains(args[0])) {
            sender.sendMessage(parser.colorString(true, "town.townDoesntExist", args[0]));
            return;
        }

        Town town = towns.get(args[0]);

        try {
            townController.joinTown(player, town);
            town.getMembers().forEach(member -> {
                if (Bukkit.getOfflinePlayer(member).isOnline()) {
                    Bukkit.getPlayer(member).sendMessage(parser.colorString(true, "town.join", sender.getName(), town.getName()));
                }
            });
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

    }

}
