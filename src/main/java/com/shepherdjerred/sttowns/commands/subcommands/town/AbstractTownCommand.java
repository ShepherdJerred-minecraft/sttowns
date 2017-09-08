package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.CommandNode;
import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.sttowns.controllers.TownController;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;

public abstract class AbstractTownCommand extends CommandNode {

    protected final Entities<Town> towns;
    protected final TownPlayers townPlayers;
    protected final TownController townController;

    public AbstractTownCommand(TownNodeRegister register, NodeInfo NodeInfo, AbstractTownCommand... children) {
        super(register, NodeInfo, children);
        this.towns = register.getTowns();
        this.townPlayers = register.getTownPlayers();
        this.townController = register.getTownController();
    }

}
