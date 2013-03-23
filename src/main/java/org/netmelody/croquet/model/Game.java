package org.netmelody.croquet.model;

import static org.netmelody.croquet.model.Position.at;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

import org.netmelody.croquet.rules.Rules;


public final class Game {

    public final Pitch pitch;
    public final Rules rules;
    public final List<Team> teams;
    public final Turn currentTurn;
    public final List<BallInPlay> ballPositions;

    public Game(Team team1, Team team2) {
        this(new Pitch(),
             new Rules(),
             unmodifiableList(asList(team1, team2)),
             unmodifiableList(asList(new BallInPlay(Ball.BLACK,  at(10.0f, 30.0f)),
                                     new BallInPlay(Ball.YELLOW, at(10.0f, 32.5f)),
                                     new BallInPlay(Ball.RED,    at(10.0f, 35.0f)),
                                     new BallInPlay(Ball.BLUE,   at(10.0f, 37.5f)))),
             new Turn(team1));
    }

    private Game(Pitch pitch, Rules rules, List<Team> teams, List<BallInPlay> newPositions, Turn turn) {
        this.pitch = pitch;
        this.rules = rules;
        this.teams = teams;
        this.ballPositions = newPositions;
        
        if (turn.finished()) {
            currentTurn = new Turn(teamAfter(teams, turn.team));
        }
        else {
            currentTurn = turn;
        }
    }

    private static Team teamAfter(List<Team> teams, Team team) {
        return teams.get((teams.indexOf(team) + 1) % teams.size());
    }

    public Team currentTeam() {
        return currentTurn.team;
    }

    public Game applyStroke(Ball ball, List<StrokeEvent> events, List<BallInPlay> newPositions) {
        return new Game(pitch, rules, teams, newPositions, rules.adjudicateStroke(currentTurn, ball, events));
    }
}
