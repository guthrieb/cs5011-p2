package danger;

import org.junit.Assert;
import org.junit.Test;

public class TestDangerMap {
    @Test
    public void basicTest() throws CoveredSquareException {
        DangerMap map = new DangerMap(World.TEST1);
        map.uncoverAll();
        Assert.assertTrue(map.getSquare(0, 0).isClue());
        Assert.assertFalse(map.getSquare(0, 0).isDagger());
        Assert.assertFalse(map.getSquare(0, 0).isGold());
        Assert.assertEquals(0, map.getSquare(0, 0).getClue());

        Assert.assertTrue(map.getSquare(0, 1).isClue());
        Assert.assertFalse(map.getSquare(0, 1).isDagger());
        Assert.assertFalse(map.getSquare(0, 1).isGold());
        Assert.assertEquals(1, map.getSquare(0, 1).getClue());

        Assert.assertTrue(map.getSquare(1, 0).isGold());
        Assert.assertFalse(map.getSquare(1, 0).isDagger());
        Assert.assertFalse(map.getSquare(1, 0).isClue());

        Assert.assertTrue(map.getSquare(1, 1).isDagger());
        Assert.assertFalse(map.getSquare(1, 1).isGold());
        Assert.assertFalse(map.getSquare(1,1).isClue());
    }

    @Test (expected = CoveredSquareException.class)
    public void coveredSquareException() {
        DangerMap map = new DangerMap(World.TEST1);
        map.getSquare(0, 0);
    }

    @Test
    public void uncoverTest() throws CoveredSquareException {
        DangerMap map = new DangerMap(World.TEST1);
        Assert.assertEquals(0, map.getSquareAndUncover(0, 0).getClue());
    }
}
