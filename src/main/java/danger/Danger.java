package danger;

import dangerAgents.Position;

import java.util.ArrayList;
import java.util.List;

public class Danger {
    private DangerMap map;
    private int lives = 1;
    private int daggersLeft;

    public Danger(World world) {
        this.map = new DangerMap(world);
        daggersLeft = map.getTotalDaggers();
    }

    public DangerSquare uncoverSquare(int x, int y) {
        DangerSquare uncovered = map.getSquareAndUncover(x, y);
        try {
            if (uncovered.isDagger()) {
                lives -= 1;
                daggersLeft--;
            } else if (uncovered.isGold()) {
                lives += 1;
            }
        } catch (CoveredSquareException e) {
            e.printStackTrace();
        }
        return uncovered;
    }

    public DangerSquare beginGame() {
        return map.getSquareAndUncover(0, 0);
    }

    public boolean gameOver() {
        return lives <= 0;
    }

    public List<DangerSquare> getNeighbours(Position position) {
        List<DangerSquare> dangerSquares = new ArrayList<>();
        for (int x = position.getX() - 1; x <= position.getX() + 1 && x < getXdim(); x++) {
            for (int y = position.getY() - 1; y <= position.getY() + 1 && y < getYdim(); y++) {
                if ((!(x == position.getX() && y == position.getY())) && x >= 0 && y >= 0) {
                    dangerSquares.add(map.getSquare(x, y));
                }
            }
        }
        return dangerSquares;
    }

    public int getXdim() {
        return map.getSizeX();
    }

    public int getYdim() {
        return map.getSizeY();
    }

    public int getRemainingDaggers() {
        return daggersLeft;
    }

    public DangerSquare getSquare(Position position) {
        return map.getSquare(position.getX(), position.getY());
    }

    public DangerMap getMap() {
        return map;
    }

    public int getRemainingLives() {
        return lives;
    }
}
