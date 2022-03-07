package genspark.projects.HumanVsGoblins;

public abstract class GameCharacter extends PlaceableEntity {
    protected int health;
    protected int attack;

    public GameCharacter(int xPosition, int yPosition, int health, int attack) {
        super(xPosition, yPosition);
        this.health = health;
        this.attack = attack;
    }

    // TODO: if I add modifiers
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int generateRandomizedAttack() {
        return (int) Math.round(attack * ((Math.random() - 0.5) * 0.2 + 1));    // +/- 10% damage range TODO: test this
    }
}
