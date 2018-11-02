package dangerAgents;

import danger.CoveredSquareException;
import danger.DangerSquare;
import danger.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SPSAgent extends Agent {

    private RPSAgent agent;

    public SPSAgent(World world) {
        super(world);
    }

    boolean usingRps = false;

    public void execute() throws CoveredSquareException {
        boolean cycle = true;

        while (!hasSucceeded() && !hasFailed()) {
            cycle = false;

            for (int x = 0; x < dangerGame.getXdim(); x++) {
                for (int y = 0; y < dangerGame.getYdim(); y++) {
                    Position position = new Position(x, y);
                    if (exploredPositions.contains(position)) {

                        if (handlePosition(position)) {
                            cycle = true;
                        }
                    }
                }
            }

            if (!cycle) {
                uncoverRandomPosition();
            }
        }
        System.out.println(dangerGame.getMap());
        System.out.println(hasSucceeded());
    }

    private void uncoverRandomPosition() throws CoveredSquareException {
        HashSet<Position> checked = new HashSet<>();
        List<Position> keySet = new ArrayList<>(unexploredPositions);
        Position randomPosition = keySet.get(new Random().nextInt(keySet.size()));
        DangerSquare square = dangerGame.getSquare(randomPosition);

        while (square.isMarked() && checked.size() < keySet.size()) {
            randomPosition = keySet.get(new Random().nextInt(keySet.size()));
            square = dangerGame.getSquare(randomPosition);
            checked.add(randomPosition);
        }

        square = dangerGame.uncoverSquare(randomPosition.getX(), randomPosition.getY());
        if (square.isDagger()) {
            square.mark();
        }

        super.addExploredPosition(randomPosition);
    }

    @Override
    public boolean hasSucceeded() {
        if (!usingRps) {
            return super.hasSucceeded();
        } else {
            return agent.hasSucceeded();
        }
    }

    @Override
    public boolean hasFailed() {
        if (!usingRps) {
            return super.hasFailed();
        } else {
            return agent.hasFailed();
        }
    }


    private boolean handlePosition(Position position) throws CoveredSquareException {
        DangerSquare square = dangerGame.getSquare(position);
        List<DangerSquare> neighbours = dangerGame.getNeighbours(position);
        int markedNeighbours = 0;
        int coveredNeighbours = 0;
        for (DangerSquare neighbour : neighbours) {
            if (neighbour.isMarked()) {
                markedNeighbours++;
            }
            if (neighbour.isCovered()) {
                coveredNeighbours++;
            }
        }

        if (markedNeighbours == square.getClue()) {
            return uncoverNeighbours(neighbours);
        } else if (coveredNeighbours == square.getClue()) {
            return markNeighbours(neighbours);
        } else return false;
    }

    private boolean markNeighbours(List<DangerSquare> neighbours) {
        boolean update = false;
        for (DangerSquare neighbour : neighbours) {
            if (neighbour.isCovered() && !neighbour.isMarked()) {
                neighbour.mark();
                update = true;
            }
        }
        return update;
    }

    private boolean uncoverNeighbours(List<DangerSquare> neighbours) {
        boolean update = false;
        for (DangerSquare neighbour : neighbours) {
            if (!neighbour.isMarked() && neighbour.isCovered()) {
                update = true;
                dangerGame.uncoverSquare(neighbour.getX(), neighbour.getY());
                addExploredPosition(new Position(neighbour.getX(), neighbour.getY()));
            }
        }
        return update;
    }

    public static void main(String[] args) {
        SPSAgent agent = new SPSAgent(World.EASY2);
        try {
            agent.execute();
        } catch (CoveredSquareException e) {
            e.printStackTrace();
        }
    }
}
