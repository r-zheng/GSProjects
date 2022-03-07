package genspark.projects.HumanVsGoblins;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static Game game;

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    public static final String HUMAN_ICON = "\uD83D\uDE0A"; //"\uD83D\uDCB0";
    public static final String GOBLIN_ICON = "\uD83D\uDE08";

    private ArrayList<PlaceableEntity> entities;

    private Human player;

    public Game() {
        game = this;
        entities = new ArrayList<>();
        player = new Human(0, 0, 2000, 400, null);
        entities.add(player);

        // TODO enemy random generation maybe???
        entities.add(new Goblin(2, 6, 500, 200));
        entities.add(new Goblin(4, 4, 500, 200));
        entities.add(new Goblin(6, 7, 500, 200));
        entities.add(new Goblin(5, 0, 500, 200));
    }

    private void printBoard() {
        System.out.println(createBoard());
    }

    private String createBoard() {
        String[][] board = new String[BOARD_WIDTH][BOARD_HEIGHT];
        String boardString = "";
        for(int i = 0; i < BOARD_HEIGHT; i++)
            for(int j = 0; j < BOARD_WIDTH; j++)
                board[i][j] = "\uD83C\uDF32";
        for(PlaceableEntity entity : entities) {
            if(!board[entity.getYPosition()][entity.getXPosition()].equals(Game.HUMAN_ICON))              // This is to prevent make sure player character is always displayed
                board[entity.getYPosition()][entity.getXPosition()] = entity.getIcon();
        }
        for(String[] row : board) {
            for (String location : row) {
                boardString += location + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

    private void handleCollisions() {
        ArrayList<PlaceableEntity> toBeDestroyed = new ArrayList<>();
        for(int i = 0; i < entities.size() - 1; i++) {
            for(int j = i + 1; j < entities.size(); j++) {
                if (PlaceableEntity.areColliding(entities.get(i), entities.get(j))) {    // If colliding, handle collisions and store to be removed from entities later
                    while(true) {
                        if (!entities.get(i).handleCollision(entities.get(j))) {
                            toBeDestroyed.add(entities.get(i));
                            break;
                        }
                        if (!entities.get(j).handleCollision(entities.get(i))) {
                            toBeDestroyed.add(entities.get(j));
                            break;
                        }
                    }
                }
            }
        }
        entities.removeAll(toBeDestroyed);
    }

    private void printPlayerInformation() {
        System.out.format("Player Health: %d \nPlayer Attack: %d\n", player.getHealth(), player.getAttack());
    }

    public boolean damageEvent(GameCharacter attacked, GameCharacter attacker) {
        int damage = attacker.generateRandomizedAttack();
        attacked.setHealth(attacked.getHealth() - damage);
        System.out.format("%s attacked %s for %d damage\n", attacker, attacked, damage);
        if(attacked.getHealth() <= 0) {
            System.out.format("%s died\n", attacked);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Game game = new Game();
        boolean playing = true;
        Scanner scanner = new Scanner(System.in);
        while(playing) {
            game.printBoard();
            game.printPlayerInformation();
            switch (scanner.nextLine()) {
                case "w", "up" -> game.player.move(PlaceableEntity.Direction.UP);
                case "s", "down" -> game.player.move(PlaceableEntity.Direction.DOWN);
                case "a", "left" -> game.player.move(PlaceableEntity.Direction.LEFT);
                case "d", "right" -> game.player.move(PlaceableEntity.Direction.RIGHT);
                default -> System.out.println("Enter a direction: \"up/w\", \"down/s\", \"left/a\", or \"right/d\"");
            }
            game.handleCollisions();
            if(!game.player.isAlive()) {
                System.out.println("You died, Game Over!!!");
                playing = false;
            }
        }
    }
}
