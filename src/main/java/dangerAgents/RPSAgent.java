package dangerAgents;

import danger.DangerSquare;
import danger.World;

import java.util.ArrayList;
import java.util.Random;

public class RPSAgent extends Agent {

    ArrayList<Position> keySet;
    Random random = new Random();

    public RPSAgent(World world) {
        super(world);
        keySet = new ArrayList<>(unexploredPositions);
    }

    public RPSAgent(SPSAgent agent) {
        super(agent.exploredPositions, agent.unexploredPositions, agent.dangerGame);
        keySet = new ArrayList<>(unexploredPositions);
    }

    public void execute() {
        while (!hasFailed() && !hasSucceeded()) {
            Position randomPosition = keySet.get(random.nextInt(keySet.size()));
            dangerGame.uncoverSquare(randomPosition.getX(), randomPosition.getY());
            keySet.remove(randomPosition);
            super.addExploredPosition(randomPosition);

            checkSuccess();
        }
    }

    void checkSuccess() {
        if (dangerGame.gameOver()) {
            failed = true;
        } else if (keySet.size() == dangerGame.getRemainingDaggers()) {
            succeeded = true;
        }
    }
}
