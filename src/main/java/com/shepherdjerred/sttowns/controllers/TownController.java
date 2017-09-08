package com.shepherdjerred.sttowns.controllers;

import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.database.TownDAO;
import com.shepherdjerred.sttowns.database.TownInviteDAO;
import com.shepherdjerred.sttowns.database.TownPlayerDAO;
import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import com.shepherdjerred.sttowns.objects.ranks.Rank;
import com.shepherdjerred.sttowns.objects.ranks.Ranks;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import org.bukkit.Bukkit;

import java.util.UUID;

public class TownController {

    private final Parser parser;
    private final Entities<Town> towns;
    private final TownPlayers townPlayers;
    private final Ranks ranks;
    private final TownDAO townDAO;
    private final TownPlayerDAO townPlayerDAO;
    private final TownInviteDAO townInviteDAO;

    public TownController(Parser parser, Entities<Town> towns, TownPlayers townPlayers, Ranks ranks, TownDAO townDAO, TownPlayerDAO townPlayerDAO, TownInviteDAO townInviteDAO) {
        this.parser = parser;
        this.towns = towns;
        this.townPlayers = townPlayers;
        this.ranks = ranks;
        this.townDAO = townDAO;
        this.townPlayerDAO = townPlayerDAO;
        this.townInviteDAO = townInviteDAO;
    }

    public void createTown(TownPlayer owner, String name) throws TrackerException, CommandException {

        if (owner.hasTown()) {
            throw new CommandException("This player is already in a town",
                    parser.colorString(true, "town.alreadyHaveTown"));
        }

        checkNameRequirements(name);

        // TODO Load default normal and center plots from JSON
        Town town = new Town(UUID.randomUUID(), name, parser.colorString(false, "town.defaultMotd"));
        town.setRemainingPlots(Plot.PlotType.CENTER, 1);
        town.setRemainingPlots(Plot.PlotType.NORMAL, 10);
        towns.add(town);

        System.out.println(towns.getAsList());

        town.setRank(owner.getUuid(), ranks.getTownRanks().get("owner"));
        owner.setTown(town);

        townDAO.insert(town);
        townPlayerDAO.insertTown(owner);
    }

    public void joinTown(TownPlayer player, Town town) throws CommandException {

        if (player.hasTown()) {
            throw new CommandException("This player is already in a town",
                    parser.colorString(true, "town.alreadyInTown"));
        }

        if (!town.isOpen()) {
            throw new CommandException("This town isn't open",
                    parser.colorString(true, "town.join.notOpen", town.getName()));
        }

        town.setRank(player.getUuid(), ranks.getTownRanks().get("members"));
        player.setTown(town);

        townPlayerDAO.insertTown(player);
    }

    public void destroyTown(TownPlayer player) throws CommandException {

        // TODO Do nation checks

        if (!player.hasTown()) {
            throw new CommandException("Player not in town",
                    parser.colorString(true, "town.notInTown"));
        }

        if (!player.hasPermission(Town.TownAction.DESTROY)) {
            throw new CommandException("Player doesn't have permission to destroy town",
                    parser.colorString(true, "town.noTownPerms", "DESTROY"));
        }

        Town townToRemove = player.getTown();

        towns.remove(townToRemove);

        // Iterate over all members, find who's online
        // Then remove their town
        townToRemove.getMembers().forEach(member -> {
            if (Bukkit.getOfflinePlayer(member).isOnline()) {
                townPlayers.get(member).setTown(null);
            }
        });

        townDAO.drop(townToRemove);

    }

    public void sendInvite(TownPlayer inviter, TownPlayer invitee) throws TownException {

        if (!inviter.hasTown()) {
            throw new TownException("Inviter has no town");
        }

        TownInvite townInvite = new TownInvite(inviter, invitee);
        invitee.addInvite(townInvite);

        townInviteDAO.insert(townInvite);

    }

    public void acceptInvite(TownPlayer townPlayer, Town town) throws TownException, CommandException {

        if (!townPlayer.hasTownInvite(town)) {
            throw new TownException("Player has no invite");
        }

        joinTown(townPlayer, town);
        townInviteDAO.drop(townPlayer.getTownInvite(town));

    }

    public void revokeInvite(TownPlayer townPlayer, Town town) throws TownException {

        if (!townPlayer.hasTownInvite(town)) {
            throw new TownException("No invite to that town");
        }

        // Do the database update BEFORE removing the invite from the player
        townInviteDAO.drop(townPlayer.getTownInvite(town));
        townPlayer.removeInvite(town);

    }

    public void declineInvite(TownPlayer townPlayer, Town town) throws TownException {

        if (!townPlayer.hasTownInvite(town)) {
            throw new TownException("No invite to that town");
        }

        townPlayer.getTownInvite(town).setDeclined(true);
        townInviteDAO.setDeclined(townPlayer.getTownInvite(town));

    }

    public void clearInvites(TownPlayer townPlayer) {
        townPlayer.getTownInvites().clear();
        townInviteDAO.clearInvites(townPlayer);
    }

    public void leaveTown(TownPlayer playerToLeave) throws CommandException {

        if (!playerToLeave.hasTown()) {
            throw new CommandException("Player has no town",
                    parser.colorString(true, "town.notInTown"));
        }

        Town townToLeave = playerToLeave.getTown();

        if (playerToLeave.getTownRank().hasFlag(Rank.RankFlag.REQUIRED)) {
            boolean anotherRequiredMemberInTown = false;
            for (UUID member : townToLeave.getMembers()) {
                if (member != playerToLeave.getUuid() && playerToLeave.getTownRank() == townToLeave.getRank(member) && townToLeave.getRank(member).hasFlag(Rank.RankFlag.REQUIRED)) {
                    anotherRequiredMemberInTown = true;
                }
            }
            if (!anotherRequiredMemberInTown) {
                throw new CommandException("There must be at least one member of the players rank in the town at all times",
                        parser.colorString(true, "town.leave", townToLeave.getName()));
            }
        }

        playerToLeave.setTown(null);
        townToLeave.remove(playerToLeave.getUuid());

        townPlayerDAO.dropTown(playerToLeave);

    }

    public void kickPlayer(TownPlayer kickingPlayer, TownPlayer playerToBeKicked) throws CommandException {

        if (!kickingPlayer.hasTown()) {
            throw new CommandException("Player doesn't have a town",
                    parser.colorString(true, "town.notInTown"));
        }

        if (!kickingPlayer.hasPermission(Town.TownAction.KICK)) {
            throw new CommandException("Player doesn't have permission to kick",
                    "You don't have permission to kick");
        }

        if (kickingPlayer == playerToBeKicked) {
            throw new CommandException("Player can't kick self",
                    "You can't kick yourself");
        }

        if (!playerToBeKicked.getTown().equals(kickingPlayer.getTown())) {
            throw new CommandException("Player can't kick someone outside of their town",
                    parser.colorString(true, "town.targetNotInTown", playerToBeKicked.getName()));
        }

        if (playerToBeKicked.hasFlag(Rank.RankFlag.UNKICKABLE) && !playerToBeKicked.hasFlag(Rank.RankFlag.OWNER)) {
            throw new CommandException("Player can't kick rank with UNKICKABLE flag unless player has OWNER flag",
                    "You can't kick that player");
        }

        leaveTown(playerToBeKicked);
    }

    public void setMotd(TownPlayer townPlayer, String motd) throws CommandException {

        // TODO Enforce a length requirement

        if (!townPlayer.hasTown()) {
            throw new CommandException("Player doesn't have a town",
                    parser.colorString(true, "town.notInTown"));
        }

        if (!townPlayer.hasPermission(Town.TownAction.SET_MOTD)) {
            throw new CommandException("Player doesn't have permission to set motd",
                    parser.colorString(true, "town.noTownPerms", "SET_MOTD"));
        }

        Town town = townPlayer.getTown();

        town.setMotd(motd);
        townDAO.setMotd(town);
    }

    public void setOpen(TownPlayer townPlayer, boolean value) throws CommandException {

        if (!townPlayer.hasTown()) {
            throw new CommandException("Player doesn't have a town",
                    parser.colorString(true, "town.notInTown"));
        }

        if (!townPlayer.hasPermission(Town.TownAction.SET_OPEN)) {
            throw new CommandException("Player doesn't have permission to set name",
                    parser.colorString(true, "town.noTownPerms", "SET_OPEN"));
        }

        Town town = townPlayer.getTown();

        town.setOpen(value);
        townDAO.setOpen(town);
    }

    public void setName(TownPlayer townPlayer, String name) throws CommandException {

        if (!townPlayer.hasTown()) {
            throw new CommandException("Player doesn't have a town",
                    parser.colorString(true, "town.notInTown"));
        }

        if (!townPlayer.hasPermission(Town.TownAction.SET_NAME)) {
            throw new CommandException("Player doesn't have permission to set name",
                    parser.colorString(true, "town.noTownPerms", "SET_NAME"));
        }

        checkNameRequirements(name);

        Town town = townPlayer.getTown();

        town.setName(name);
        townDAO.setName(town);

    }

    private void checkNameRequirements(String name) throws CommandException {
        if (towns.contains(name)) {
            throw new CommandException("That town name is already taken",
                    parser.colorString(true, "town.create.nameTaken", towns.get(name).getName()));
        }

        if (name.length() < 3 || name.length() > 16) {
            throw new CommandException("Town name must be between 3-16 characters",
                    parser.colorString(true, "town.nameWrongLength", name.length()));
        }
    }

}
