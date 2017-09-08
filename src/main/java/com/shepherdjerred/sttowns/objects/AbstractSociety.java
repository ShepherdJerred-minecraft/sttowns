package com.shepherdjerred.sttowns.objects;

import com.shepherdjerred.sttowns.objects.ranks.GroupAction;

import java.util.UUID;

public abstract class AbstractSociety<T extends GroupAction> extends AbstractGroup<T> {

    protected String motd;
    protected boolean open;

    public AbstractSociety(UUID uuid, String name, String motd, boolean open) {
        super(uuid, name);
        this.motd = motd;
        this.open = open;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "AbstractSociety{" +
                "motd='" + motd + '\'' +
                ", open=" + open +
                "} " + super.toString();
    }
}
