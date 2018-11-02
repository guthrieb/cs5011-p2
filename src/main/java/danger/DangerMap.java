package danger;

import dangerAgents.Position;

import java.util.ArrayList;
import java.util.List;

public class DangerMap {
    private DangerSquare[][] dangerMap;
    private int daggers = 0;
    private List<DangerSquare> uncoveredSquares = new ArrayList<>();
    private List<DangerSquare> coveredSquares = new ArrayList<>();

    public DangerMap(World world) {
        String[][] map = world.map;
        this.dangerMap = new DangerSquare[map.length][map[0].length];

        for (int y = 0; y < map.length; y++) {
            for(int x = 0; x < map[y].length; x++) {
                DangerSquare dangerSquare;
                String dangerEnum = map[y][x];
                dangerSquare = getDangerSquare(x, y, dangerEnum);
                if(dangerEnum.equals("d")) {
                    daggers++;
                }
                dangerMap[x][y] = dangerSquare;
            }
        }
    }

    public void uncoverAll() {
        for(int i = 0; i < getSizeX(); i++) {
            for(int j = 0; j < getSizeY(); j++) {
                uncoverSquare(i, j);
            }
        }
    }

    private DangerSquare getDangerSquare(int x, int y, String dangerEnum) {
        DangerSquare dangerSquare;
        switch (dangerEnum) {
            case "g":
                dangerSquare = new DangerSquare(x, y, true, DangerSquare.Type.GOLD);
                break;
            case "d":
                dangerSquare = new DangerSquare(x, y, true, DangerSquare.Type.DAGGER);
                break;
            default:
                dangerSquare = new DangerSquare(x, y, true, Integer.parseInt(dangerEnum));
        }
        return dangerSquare;
    }

    public void uncoverSquare(int x, int y) {
        dangerMap[x][y].uncover();
    }

    public DangerSquare getSquareAndUncover(int x, int y) {
        DangerSquare dangerSquare = dangerMap[x][y];
        dangerSquare.uncover();
        return dangerSquare;
    }

    public DangerSquare getSquare(int x, int y) {
        return dangerMap[x][y];
    }

    public int getSizeX() {
        return dangerMap.length;
    }

    public int getSizeY() {
        return dangerMap[0].length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n  ");
        for(int i = 0; i < getSizeX(); i++) {
            builder.append("   ").append(i);
        }
        for(int y = 0; y < getSizeX(); y++) {
            builder.append("\n").append(y);
            builder.append(" ");
            for(int x = 0; x < getSizeX(); x++) {
                builder.append("   ");
                DangerSquare square = getSquare(x, y);
                if (square.isMarked()) {
                    builder.append("M");
                } else if(square.isCovered()) {
                    builder.append("#");
                } else {
                    builder.append(getSquare(x, y));
                }
            }
        }
        return builder.toString();
    }

    public int getTotalDaggers() {
        return daggers;
    }
}
