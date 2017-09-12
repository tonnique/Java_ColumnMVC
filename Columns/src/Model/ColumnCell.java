package Model;

public class ColumnCell {
    private int x;
    private int y;

    public ColumnCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ColumnCell)) return false;
        ColumnCell anotherCell = (ColumnCell) obj;
        if ((this.getX() == anotherCell.getX())
           && (this.getY() == anotherCell.getY())) return  true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash + this.getX() + this.getY();
    }
}
