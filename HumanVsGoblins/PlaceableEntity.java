package genspark.projects.HumanVsGoblins;

public abstract class PlaceableEntity {
    public enum Direction {
            UP, DOWN, LEFT, RIGHT;
    }

    private int xPosition;
    private int yPosition;

    public PlaceableEntity(int xPosition, int yPosition) {
        setPosition(xPosition, yPosition);
    }

    protected boolean setPosition(int xPosition, int yPosition) {
        if(0 <= xPosition && xPosition < Game.BOARD_WIDTH && 0 <= yPosition && yPosition < Game.BOARD_HEIGHT) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            return true;
        } else {
            return false;
        }
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public static boolean areColliding(PlaceableEntity entity1, PlaceableEntity entity2) {
        return entity1.getXPosition() == entity2.getXPosition() && entity1.getYPosition() == entity2.getYPosition();
    }

    abstract public boolean handleCollision(PlaceableEntity other);

    abstract public String getIcon();

    abstract public String toString();
}
