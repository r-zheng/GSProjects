package genspark.projects.Project_2;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

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
                try {
                    guess = input.nextInt();
                } catch(InputMismatchException e) {
                    System.out.println("\nI am thinking of a number between 1 and 20.\nTake a guess.\n");
                    input.next();
                    continue;
                }

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

            String playAgain = input.nextLine();

            while(!playAgain.equals("y") && !playAgain.equals("n")) {
                System.out.println("\ny or n\n");
                playAgain = input.nextLine();
            }

            if(playAgain.equals("n"))
                playing = false;
        }
        input.close();
    }
}