package com.shepherdjerred.sttowns.database;

import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import org.codejargon.fluentjdbc.api.FluentJdbc;

public class TownInviteDAO {

    private final FluentJdbc fluentJdbc;

    public TownInviteDAO(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    public void drop(TownInvite townInvite) {

    }

    public void setDeclined(TownInvite townInvite) {

    }

    public void insert(TownInvite townInvite) {

    }

    public void clearInvites(TownPlayer townPlayer) {

    }

}
