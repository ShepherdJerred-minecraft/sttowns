package com.shepherdjerred.sttowns.objects.invites;

import com.shepherdjerred.sttowns.objects.AbstractEntity;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public interface Invite<TargetType extends AbstractEntity, InvitedToType extends AbstractEntity> {

    public TownPlayer getSender();
    public TargetType getTarget();
    public InvitedToType getInvitedTo();
    public boolean isDeclined();
    public void setDeclined(boolean declined);

}
