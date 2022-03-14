package genspark.projects.HangmanFunctional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class HangmanGame {
    private int remainingWrongGuesses;
    private ArrayList<CharacterSlot> letters;
    private HashSet<Character> guessedLetters;
    private int remainingLetters;
    private ArrayList<String> hangmenImage;

    private int score;
    private String name;
    private String scoreFile;

    public HangmanGame(String name, String scoreFile) throws IOException {
        this.scoreFile = scoreFile;
        score = 0;
        this.name = name;

        hangmenImage = new ArrayList<String>();
        try (Stream<String> stream = Files.lines(Paths.get("./HangmanFunctional/HangmanPic.txt"))){
            hangmenImage.addAll(Arrays.asList(stream.reduce("", (str1, str2) -> str1 + "\n" + str2).split("\\.")));
        } catch (Exception e) {
            System.out.println("Couldn't find hangman art. Exiting.");
            throw e;
        }
    }

    public void newGame(String dictionaryName) throws IOException {
        String word;
        try {
            word = getRandomWord(dictionaryName);
        } catch (IOException e) {
            System.out.println("Dictionary file couldn't be opened. Unable to generate word. Exiting.");
            throw e;
        }

        remainingWrongGuesses = 6;
        letters = new ArrayList<>();
        guessedLetters = new HashSet<>();
        remainingLetters = word.length();
        Arrays.stream(word.split("")).forEach(letter -> letters.add(new CharacterSlot(letter.charAt(0))));
    }

    private String getRandomWord(String filename) throws IOException {
        ArrayList<String> words = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filename))){
            stream.forEach(words::add);
        }

        return words.get(new Random().nextInt(words.size()));
    }

    // return number correct if correct, return -1 if incorrect, return 0 if character already been guessed
    public int guessLetter(char guess) {
        final int[] numberCorrect = {0};        // wrapper so that this can be used in stream later
        if(guessedLetters.contains(guess)) {
            return 0;
        }

        guessedLetters.add(guess);

        letters.stream().forEach(letter -> {
            if (letter.reveal(guess))
                numberCorrect[0]++;
        });

        remainingLetters -= numberCorrect[0];

        if (numberCorrect[0] == 0) {
            remainingWrongGuesses--;
            return -1;
        }

        return numberCorrect[0];
    }

    private void printHangman() {
        System.out.println(hangmenImage.get(remainingWrongGuesses));
    }

    private void printGuesses() {
        letters.stream().forEach(letter -> {
            if (letter.isRevealed())
                System.out.print(letter.getLetter());
            else
                System.out.print("_");
            System.out.print(" ");
        });

        System.out.printf("\nYou have %d wrong guesses remaining     You have guessed: %s%nYour score: %d\n", remainingWrongGuesses, guessedLetters.toString(), score);
    }

    public int getRemainingWrongGuesses() {
        return remainingWrongGuesses;
    }

    public int getRemainingLetters() {
        return remainingLetters;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void recordScore() {
        Score scoreObj = new Score(name, score);
        scoreObj.write(scoreFile);
    }

    public void getHighScore() {
        // TODO
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
        String dictionaryName = "./HangmanFunctional/RandomWords.txt";
        String scoreFile = "./HangmanFunctional/Scores.txt";

        System.out.println(Score.readScores(scoreFile));

        boolean playing = true;
        HangmanGame hangmanGame;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name:");

        try {
            hangmanGame = new HangmanGame(scanner.nextLine(), scoreFile);
            hangmanGame.newGame(dictionaryName);
        } catch (IOException e) {
            return;
        }

        while(playing) {
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
                    hangmanGame.setScore(hangmanGame.getScore() + 1);
                    try {
                        hangmanGame.newGame(dictionaryName);
                    } catch (IOException e) {
                        return;
                    }
                    break;
                } else if (hangmanGame.getRemainingWrongGuesses() == 0) {
                    System.out.println("Out of guesses. You lost.");
                    hangmanGame.recordScore();
                    playing = false;
                    break;
                }
            }
        }
        System.out.printf("\nCurrent High Score: %s", Score.readScores(scoreFile).stream().max((score1, score2) -> score1.getScore() > score2.getScore() ? 1 : -1).get());
    }
}
