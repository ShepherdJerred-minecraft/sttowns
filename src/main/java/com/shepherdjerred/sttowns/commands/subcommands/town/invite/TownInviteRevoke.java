package com.shepherdjerred.sttowns.commands.subcommands.town.invite;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class TownInviteRevoke extends AbstractTownCommand {

    public TownInviteRevoke(TownNodeRegister register) {
        super(register, new NodeInfo(
                "revoke",
                "stTowns.town.invite.revoke",
                "Revoke an invite sent to a player",
                "/town invite revoke <player name>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer player = townPlayers.get(sender);

        if (!player.hasTown()) {
            sender.sendMessage(parser.colorString(true, "town.notInTown"));
            return;
        }

        if (!player.hasPermission(Town.TownAction.INVITE)) {
            sender.sendMessage(parser.colorString(true, "town.noTownPerms", "INVITE"));
            return;
        }

        // Now we do checks for the other player
        if (Bukkit.getOfflinePlayer(args[0]) == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerDoesntExist", args[0]));
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        TownPlayer targetPlayer;

        if (offlinePlayer.isOnline()) {
            targetPlayer = townPlayers.get(args[0]);
        } else {
            // TODO Load the offline TownPlayer from the database
            targetPlayer = new TownPlayer(UUID.randomUUID(), "SamplePlayer", null);
        }

        if (!targetPlayer.hasTownInvite(player.getTown())) {
            sender.sendMessage("Not invited");
            return;
        }

        try {
            townController.revokeInvite(targetPlayer, player.getTown());
        } catch (TownException e) {
            sender.sendMessage("Error revoking");
            e.printStackTrace();
        }

        sender.sendMessage("Invite for player removed");

        if (offlinePlayer.isOnline()) {
            Bukkit.getPlayer(args[0]).sendMessage("Invite for towns removed");
        }


    }

}
