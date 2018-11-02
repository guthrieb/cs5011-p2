package dangerAgents;

import danger.Danger;
import danger.World;
import org.junit.Assert;
import org.junit.Test;

public class RPSAgentTest {
    @Test
    public void basicTest() {
        RPSAgent agent = new RPSAgent(World.TEST2);
        agent.execute();

        Assert.assertTrue(agent.hasSucceeded());
        Assert.assertFalse(agent.hasFailed());
    }

    @Test
    public void basicTest2() {
        RPSAgent agent = new RPSAgent(World.TEST3);
        agent.execute();


        Assert.assertTrue(agent.hasFailed());
        Assert.assertFalse(agent.hasSucceeded());
    }

    @Test
    public void basicTest3() {
        RPSAgent agent = new RPSAgent(World.TEST4);
        agent.execute();

        Assert.assertTrue(agent.hasSucceeded());
        Assert.assertFalse(agent.hasFailed());
    }
}
