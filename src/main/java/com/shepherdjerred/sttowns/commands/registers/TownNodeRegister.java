package com.shepherdjerred.sttowns.commands.registers;

import com.shepherdjerred.riotbase.commands.NodeRegister;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.sttowns.controllers.TownController;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;

public class TownNodeRegister extends NodeRegister {

    private final Entities<Town> towns;
    private final TownPlayers townPlayers;
    private final TownController townController;

    public TownNodeRegister(AbstractParser parser, Entities<Town> towns, TownPlayers townPlayers, TownController townController) {
        super(parser);
        this.towns = towns;
        this.townPlayers = townPlayers;
        this.townController = townController;
    }

    public Entities<Town> getTowns() {
        return towns;
    }

    public TownPlayers getTownPlayers() {
        return townPlayers;
    }

    public TownController getTownController() {
        return townController;
    }

}
