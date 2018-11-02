package analysis;

import danger.World;
import dangerAgents.RPSAgent;

public class Main {
    public static void main(String[] args) {
        RPSAgent agent = new RPSAgent(World.EASY1);
        agent.execute();
        if(agent.hasSucceeded()) {
            System.out.println("SUCCEEDED");
        }
        if(agent.hasFailed()) {
            System.out.println("FAILED");
        }
    }
}
