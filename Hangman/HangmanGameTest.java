package genspark.projects.Hangman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Random;

class HangmanGameTest {
    static Random random;

    @BeforeAll
    static void setup() {
        random = new Random();
    }

    @Test
    void testSpecific() throws FileNotFoundException {
        HangmanGame game = new HangmanGame("./Hangman/TestWord.txt");

        Assertions.assertEquals(7, game.getRemainingLetters());
        Assertions.assertEquals(6, game.getRemainingWrongGuesses());

        Assertions.assertEquals(2, game.guessLetter('c'));
        Assertions.assertEquals(5, game.getRemainingLetters());
        Assertions.assertEquals(6, game.getRemainingWrongGuesses());

        Assertions.assertEquals(1, game.guessLetter('o'));
        Assertions.assertEquals(4, game.getRemainingLetters());
        Assertions.assertEquals(6, game.getRemainingWrongGuesses());

        Assertions.assertEquals(2, game.guessLetter('r'));
        Assertions.assertEquals(2, game.getRemainingLetters());
        Assertions.assertEquals(6, game.getRemainingWrongGuesses());

        Assertions.assertEquals(1, game.guessLetter('e'));
        Assertions.assertEquals(1, game.getRemainingLetters());
        Assertions.assertEquals(6, game.getRemainingWrongGuesses());

        Assertions.assertEquals(0, game.guessLetter('l'));
        Assertions.assertEquals(1, game.getRemainingLetters());
        Assertions.assertEquals(5, game.getRemainingWrongGuesses());

        Assertions.assertEquals(0, game.guessLetter('g'));
        Assertions.assertEquals(1, game.getRemainingLetters());
        Assertions.assertEquals(4, game.getRemainingWrongGuesses());

        Assertions.assertEquals(1, game.guessLetter('t'));
        Assertions.assertEquals(0, game.getRemainingLetters());
        Assertions.assertEquals(4, game.getRemainingWrongGuesses());
    }

    @Test
    void testRandom() throws FileNotFoundException {
        HangmanGame game;

        for (int i = 0; i < 100; i++) {
            game = new HangmanGame("./Hangman/RandomWords.txt");
            int remainingLetters = game.getRemainingLetters();
            int remainingWrongGuesses = game.getRemainingWrongGuesses();
            while (remainingLetters > 0 && remainingWrongGuesses > 0) {
                char c = (char) (random.nextInt(26) + 'a');
                int correct = game.guessLetter(c);
                remainingLetters -= correct;
                if (correct == 0)
                    remainingWrongGuesses--;
                Assertions.assertEquals(remainingLetters, game.getRemainingLetters());
                Assertions.assertEquals(remainingWrongGuesses, game.getRemainingWrongGuesses());
            }
        }
    }
}