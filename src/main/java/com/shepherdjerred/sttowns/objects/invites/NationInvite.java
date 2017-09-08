package com.shepherdjerred.sttowns.objects.invites;

import com.shepherdjerred.sttowns.objects.Nation;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class NationInvite implements Invite<Town, Nation> {

    private final TownPlayer sender;
    private final Town town;
    private final Nation nation;
    private boolean declined;

    public NationInvite(TownPlayer sender, Town town, Nation nation, boolean declined) {
        this.sender = sender;
        this.town = town;
        this.nation = nation;
        this.declined = declined;
    }

    @Override
    public TownPlayer getSender() {
        return sender;
    }

    @Override
    public Town getTarget() {
        return town;
    }

    @Override
    public Nation getInvitedTo() {
        return nation;
    }

    @Override
    public boolean isDeclined() {
        return declined;
    }

    @Override
    public void setDeclined(boolean declined) {
        this.declined = declined;
    }

}
