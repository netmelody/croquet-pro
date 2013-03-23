package org.netmelody.croquet.model;

public final class Turn {

    public final Team team;
    private boolean live;

    public Turn(Team team) {
        this(team, true);
    }

    private Turn(Team team, boolean live) {
        this.team = team;
        this.live = live;
    }

    public Turn finish() {
        return new Turn(team, false);
    }

    public boolean finished() {
        return !live;
    }
}
