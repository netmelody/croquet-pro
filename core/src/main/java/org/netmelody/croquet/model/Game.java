package org.netmelody.croquet.model;

import static org.netmelody.croquet.model.Position.at;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

import org.netmelody.croquet.rules.Rules;


public final class Game {

    public final Court court;
    public final Rules rules;
    public final List<Team> teams;
    public final Turn currentTurn;
    public final List<BallInPlay> ballPositions;

    public Game(Team team1, Team team2) {
        this(new Court(),
             new Rules(),
             unmodifiableList(asList(team1, team2)));
    }

    private Game(Court court, Rules rules, List<Team> teams) {
        this(court, rules, teams,
             unmodifiableList(asList(new BallInPlay(Ball.BLACK,  at(court.yardLineInset, 20.0f)),
                                     new BallInPlay(Ball.YELLOW, at(court.yardLineInset, 22.5f)),
                                     new BallInPlay(Ball.RED,    at(court.yardLineInset, 25.0f)),
                                     new BallInPlay(Ball.BLUE,   at(court.yardLineInset, 27.5f)))),
             new Turn(teams.get(0)));
    }

    private Game(Court pitch, Rules rules, List<Team> teams, List<BallInPlay> newPositions, Turn turn) {
        this.court = pitch;
        this.rules = rules;
        this.teams = teams;
        this.ballPositions = newPositions;
        
        if (turn.finished) {
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

    public Game applyStroke(Ball ball, List<StrokeEvent<?>> events, List<BallInPlay> newPositions) {
        final Turn progressedTurn = rules.adjudicateStroke(currentTurn, ball, court.hoops.get(0), events);
        return new Game(court, rules, teams, newPositions, progressedTurn);
    }
}
