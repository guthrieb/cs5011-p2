package dangerAgents;

import danger.Danger;
import danger.DangerMap;
import danger.DangerSquare;
import danger.World;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Agent {
    Set<Position> unexploredPositions = new HashSet<>();
    Set<Position> exploredPositions = new HashSet<>();
    Danger dangerGame;

    boolean failed = false;
    boolean succeeded = false;

    public Agent(Set<Position> unexploredPositions, Set<Position> exploredPositions, Danger dangerGame) {
        this.unexploredPositions = unexploredPositions;
        this.exploredPositions = exploredPositions;
        this.dangerGame = dangerGame;
    }

    public Agent(World world) {
        this.dangerGame = new Danger(world);
        for(int i = 0; i < dangerGame.getXdim(); i++) {
            for(int j = 0; j < dangerGame.getYdim(); j++) {
                unexploredPositions.add(new Position(i, j));
            }
        }
        DangerSquare dangerSquare = dangerGame.beginGame();
        Position position = new Position(dangerSquare.getX(), dangerSquare.getY());
        addExploredPosition(position);
    }

    public boolean hasFailed() {
        return dangerGame.gameOver();
    }

    void addExploredPosition(Position explored) {
        unexploredPositions.remove(explored);
        exploredPositions.add(explored);
    }

    public boolean hasSucceeded() {
        return dangerGame.getRemainingDaggers() == unexploredPositions.size();
    }


}
