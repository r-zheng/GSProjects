package genspark.projects.Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    private int remainingWrongGuesses;
    private ArrayList<CharacterSlot> letters;
    private HashSet<Character> guessedLetters;
    private String[] man;
    private int remainingLetters;

    public HangmanGame(String dictionaryName) throws FileNotFoundException {
        String word = getRandomWord(dictionaryName);
        remainingWrongGuesses = 6;
        letters = new ArrayList<>();
        guessedLetters = new HashSet<>();
        remainingLetters = word.length();
        man = new String[]{" ______  ",
                " |    |  ",
                " |       ",
                " |       ",
                " |       ",
                " ------  "};
        for (int i = 0; i < word.length(); i++) {
            letters.add(new CharacterSlot(word.charAt(i)));
        }
    }

    private String getRandomWord(String filename) throws FileNotFoundException {
        ArrayList<String> words = new ArrayList<>();

        Scanner dictScanner = new Scanner(new File(filename));
        while (dictScanner.hasNext())
            words.add(dictScanner.nextLine());
        dictScanner.close();

        return words.get(new Random().nextInt(words.size()));
    }

    public int guessLetter(char guess) {
        int numberCorrect = 0;
        guessedLetters.add(guess);
        for (CharacterSlot c : letters) {
            boolean match = c.reveal(guess);
            if (match)
                numberCorrect++;
        }

        remainingLetters -= numberCorrect;

        if (numberCorrect == 0)
            wrongGuess();

        return numberCorrect;
    }

    private void wrongGuess() {
        remainingWrongGuesses--;
        switch (remainingWrongGuesses) {
            case 5:
                man[2] = " |    O  ";
                break;
            case 4:
                man[3] = " |    |  ";
                break;
            case 3:
                man[2] = " |  __O  ";
                break;
            case 2:
                man[2] = " |  __O__";
                break;
            case 1:
                man[4] = " |   /   ";
                break;
            case 0:
                man[4] = " |   / \\ ";
                break;
            default:
                break;
        }
    }

    private void printHangman() {
        for (String row : man)
            System.out.println(row);
    }

    private void printGuesses() {
        for (CharacterSlot c : letters) {
            if (c.isRevealed())
                System.out.print(c.getLetter());
            else
                System.out.print("_");
            System.out.print(" ");
        }
        System.out.printf("\nYou have %d wrong guesses remaining     You have guessed: %s%n", remainingWrongGuesses, guessedLetters.toString());
    }

    public int getRemainingWrongGuesses() {
        return remainingWrongGuesses;
    }

    public int getRemainingLetters() {
        return remainingLetters;
    }

    private class CharacterSlot {
        private final char letter;
        private boolean revealed;

        public CharacterSlot(char letter) {
            this.letter = letter;
            revealed = false;
        }

        public boolean reveal(char guess) {
            if (guess == letter) {
                revealed = true;
                return true;
            }
            return false;
        }

        public char getLetter() {
            return letter;
        }

        public boolean isRevealed() {
            return revealed;
        }
    }

    public static void main(String[] args) {
        String dictionaryName = "./Hangman/RandomWords.txt";
        HangmanGame hangmanGame;
        try {
            hangmanGame = new HangmanGame(dictionaryName);
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found. Unable to generate word. Exiting.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        hangmanGame.printHangman();
        hangmanGame.printGuesses();
        while (true) {
            String input = scanner.nextLine();
            if (input.length() != 1) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            hangmanGame.guessLetter(Character.toLowerCase(input.charAt(0)));
            hangmanGame.printHangman();
            hangmanGame.printGuesses();
            if (hangmanGame.getRemainingLetters() == 0) {
                System.out.println("Congratulations!!! You won!!!");
                break;
            } else if (hangmanGame.getRemainingWrongGuesses() == 0) {
                System.out.println("Out of guesses. You lost.");
                break;
            }
        }
    }
}
