package org.netmelody.croquet.model;

public final class Turn {

    public final Team team;
    public final Ball roquetBall;
    public final boolean finished;

    public Turn(Team team) {
        this(team, false, null);
    }

    private Turn(Team team, boolean finished, Ball roquetBall) {
        this.team = team;
        this.finished = finished;
        this.roquetBall = roquetBall;
    }

    public Turn finish() {
        return new Turn(team, true, null);
    }

    public Turn roquet(Ball ball) {
        return new Turn(team, false, ball);
    }

    public Turn continuation() {
        return this;
    }
}
