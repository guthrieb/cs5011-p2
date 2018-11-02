package danger;

public class DangerSquare {
    private int x;
    private int y;
    private boolean covered;
    private int value;
    private boolean marked = false;
    private Type type;
    DangerSquare(int x, int y, boolean covered, int value) {
        this.x = x;
        this.y = y;
        this.covered = covered;
        this.value = value;
        this.type = Type.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    DangerSquare(int x, int y, boolean covered, Type type) {
        this.x = x;
        this.y = y;
        this.covered = covered;
        this.type = type;
        this.value = 0;
    }

    public boolean isDagger() throws CoveredSquareException {
        if(covered) {
            throw new CoveredSquareException("This square is covered");
        }
        return type == Type.DAGGER;
    }

    boolean isGold() throws CoveredSquareException {
        if(covered) {
            throw new CoveredSquareException("This square is covered");
        }
        return type == Type.GOLD;
    }

    boolean isClue() throws CoveredSquareException {
        if(covered) {
            throw new CoveredSquareException("This square is covered");
        }
        return type == Type.EMPTY;
    }

    public int getClue() throws CoveredSquareException {
        if(covered) {
            throw new CoveredSquareException("This square is covered");
        }
        return value;
    }

    void uncover() {
        covered = false;
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        marked = true;
    }

    public String toString() {
        if(type == Type.DAGGER) {
            return "d";
        } else if (type == Type.GOLD) {
            return "g";
        } else {
            return String.valueOf(value);
        }
    }

    public boolean isCovered() {
        return covered;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != DangerSquare.class) {
            return false;
        }

        DangerSquare dangerSquare = (DangerSquare) obj;

        return dangerSquare.x == this.x && dangerSquare.y == this.y;
    }

    enum Type {
        DAGGER, GOLD, EMPTY
    }
}


