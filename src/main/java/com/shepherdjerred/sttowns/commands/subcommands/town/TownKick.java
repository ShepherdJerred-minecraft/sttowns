package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class TownKick extends AbstractTownCommand {

    public TownKick(TownNodeRegister register) {
        super(register, new NodeInfo(
                "kick",
                "stTowns.town.kick",
                "Kick a player from your town",
                "/town kick <player name>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer kickingPlayer = townPlayers.get(sender);
        Town town = kickingPlayer.getTown();

        if (Bukkit.getOfflinePlayer(args[0]) == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerDoesntExist", args[0]));
            return;
        }

        OfflinePlayer offlinePlayerToBeKicked = Bukkit.getOfflinePlayer(args[0]);
        TownPlayer playerToBeKicked;

        if (offlinePlayerToBeKicked.isOnline()) {
            playerToBeKicked = townPlayers.get(args[0]);
        } else {
            // TODO load the player from the database instead
            sender.sendMessage("Player must be online");
            return;
        }

        try {
            townController.kickPlayer(kickingPlayer, playerToBeKicked);
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

        Parser.broadcastMessage(town.getOnlinePlayers(), parser.colorString(true, "town.kick.townBroadcast", playerToBeKicked.getName(), sender.getName()));

        if (offlinePlayerToBeKicked.isOnline()) {
            Bukkit.getPlayer(args[0]).sendMessage(parser.colorString(true, "town.kick.target", town.getName(), sender.getName()));
        }

    }


}
