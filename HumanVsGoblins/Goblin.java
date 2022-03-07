package genspark.projects.HumanVsGoblins;

public class Goblin extends GameCharacter {
    private String icon;

    public Goblin(int xPosition, int yPosition, int health, int attack) {
        super(xPosition, yPosition, health, attack);
        icon = Game.GOBLIN_ICON;
    }

    public boolean handleCollision(PlaceableEntity other) {
        if(other instanceof Human) {
            return Game.game.damageEvent(this, (Human)other);
        }
        return true;
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return "Goblin";
    }
}
