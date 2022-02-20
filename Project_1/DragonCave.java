package genspark.projects.Project_1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DragonCave {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("You are in a land full of dragons. In front of you,\nyou see two caves. In one cave, the dragon is friendly\nand will share his treasure with you. The other dragon\nis greedy and hungry and will eat you on sight.\nWhich cave will you go into? (1 or 2)\n");

        int choice = getIntegerFromInput(input);

        while(choice != 1 && choice != 2) {
            System.out.println("Choose 1 or 2:");
            choice = getIntegerFromInput(input);
        }

        if(choice == 1) {
            System.out.println("\nYou approach the cave...\nIt is dark and spooky...\nA large dragon jumps out in front of you! He opens his jaws and...\nGobbles you down in one bite!");
        }

        if(choice == 2) {
            System.out.println("\nYou approach the cave...\nIt is dark and spooky...\nA large dragon jumps out in front of you! He opens his jaws and...\nGives you gold!");
        }

        input.close();
    }

    public static int getIntegerFromInput(Scanner input) {
        while(true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not a valid choice");
                input.next();
            }
        }
    }
}