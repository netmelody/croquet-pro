package org.netmelody.croquet.model;

public final class Turn {

    public final Team team;
    public final Ball roquetBall;
    private boolean live;

    public Turn(Team team) {
        this(team, true, null);
    }

    private Turn(Team team, boolean live, Ball roquetBall) {
        this.team = team;
        this.live = live;
        this.roquetBall = roquetBall;
    }

    public Turn finish() {
        return new Turn(team, false, null);
    }

    public boolean finished() {
        return !live;
    }

    public Turn roquet(Ball ball) {
        return new Turn(team, true, ball);
    }
}
