package com.shepherdjerred.sttowns.objects.invites;

import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownInvite implements Invite<TownPlayer, Town> {

    private final TownPlayer sender;
    private final TownPlayer townPlayer;
    private final Town town;
    private boolean declined;

    public TownInvite(TownPlayer sender, TownPlayer townPlayer, Town town, boolean declined) {
        this.sender = sender;
        this.townPlayer = townPlayer;
        this.town = town;
        this.declined = declined;
    }

    public TownInvite(TownPlayer sender, TownPlayer townPlayer) {
        this.sender = sender;
        this.townPlayer = townPlayer;
        this.town = sender.getTown();
        this.declined = false;
    }

    @Override
    public TownPlayer getSender() {
        return sender;
    }

    @Override
    public TownPlayer getTarget() {
        return townPlayer;
    }

    @Override
    public Town getInvitedTo() {
        return town;
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
