package genspark.projects.Project_2;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Hello! What is your name?\n");
        String name = input.nextLine();

        boolean playing = true;
        while(playing) {
            System.out.println("\nWell " + name + ", I am thinking of a number between 1 and 20.\nTake a guess.\n");

            int randNum = new Random().nextInt(20) + 1;

            int guess = 0;
            int i = 0;
            while(i < 6) {
                guess = getGuess(input);
                if(guess == 0)
                    continue;

                if(guess == randNum) {
                    System.out.println("\nGood job " + name + "! You guessed my number in " + (i + 1) + " guesses!");
                    break;
                } else if(guess < randNum) {
                    System.out.println("\nYour guess is too low.");
                    if(i < 5) {
                        System.out.println("Take a guess.\n");
                    } else {
                        System.out.println("You lose. Number was " + randNum + ".");
                    }
                } else {
                    System.out.println("\nYour guess is too high.");
                    if(i < 5) {
                        System.out.println("Take a guess.\n");
                    } else {
                        System.out.println("You lose. Number was " + randNum + ".");
                    }
                }
                i++;
            }
            System.out.println("Would you like to play again? (y or n)\n");

            String playAgain = getPlayAgain(input);

            if(playAgain.equals("n"))
                playing = false;
        }
        input.close();
    }

    public static String getPlayAgain(Scanner input) {
        input.skip("\\R?");         // Scanner.nextInt doesn't consume endline characters so this goes to new line
        String playAgain = input.nextLine();
        if(!playAgain.equals("y") && !playAgain.equals("n")) {
            System.out.println("\ny or n\n");
            input.next();
            return getPlayAgain(input);
        }
        return playAgain;
    }

    public static int getGuess(Scanner input) {
        try {
            return input.nextInt();
        } catch(InputMismatchException e) {
            System.out.println("\nI am thinking of a number between 1 and 20.\nTake a guess.\n");
            input.next();
            return 0;
        }
    }
}