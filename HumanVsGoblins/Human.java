package genspark.projects.HumanVsGoblins;

public class Human extends GameCharacter {
    private Inventory inventory;
    private String icon;

    public Human(int xPosition, int yPosition, int health, int attack, Integer inventorySize) {
        super(xPosition, yPosition, health, attack);
        inventory = new Inventory(inventorySize);
        icon = Game.HUMAN_ICON;
    }

    // returns false if move is not valid(out of bounds)
    public boolean move(Direction direction) {
        if(direction == Direction.UP) {
            return super.setPosition(getXPosition(), getYPosition() - 1);
        } else if(direction == Direction.DOWN) {
            return super.setPosition(getXPosition(), getYPosition() + 1);
        } else if(direction == Direction.LEFT) {
            return super.setPosition(getXPosition() - 1, getYPosition());
        } else if(direction == Direction.RIGHT) {
            return super.setPosition(getXPosition() + 1, getYPosition());
        }
        return false;
    }

    public boolean handleCollision(PlaceableEntity other) {
        if(other instanceof Goblin) {
            return Game.game.damageEvent(this, (Goblin)other);
        }
        return true;
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return "Human";
    }
}
