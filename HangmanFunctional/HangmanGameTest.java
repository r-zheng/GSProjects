package genspark.projects.HangmanFunctional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

class HangmanGameTest {
    static Random random;

    @BeforeAll
    static void setup() {
        random = new Random();
    }

    @Test
    void testSpecific() throws IOException {
        HangmanGame game = new HangmanGame("tester", "./HangmanFunctional/TestScores.txt");
        game.newGame("./HangmanFunctional/TestWord.txt");
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

        Assertions.assertEquals(-1, game.guessLetter('l'));
        Assertions.assertEquals(1, game.getRemainingLetters());
        Assertions.assertEquals(5, game.getRemainingWrongGuesses());

        Assertions.assertEquals(-1, game.guessLetter('g'));
        Assertions.assertEquals(1, game.getRemainingLetters());
        Assertions.assertEquals(4, game.getRemainingWrongGuesses());

        Assertions.assertEquals(1, game.guessLetter('t'));
        Assertions.assertEquals(0, game.getRemainingLetters());
        Assertions.assertEquals(4, game.getRemainingWrongGuesses());
    }

    @Test
    void testRandom() throws IOException {
        HangmanGame game = new HangmanGame("tester", "./HangmanFunctional/TestScores.txt");
        for (int i = 0; i < 100; i++) {
            game.newGame("./HangmanFunctional/RandomWords.txt");
            int remainingLetters = game.getRemainingLetters();
            int remainingWrongGuesses = game.getRemainingWrongGuesses();
            while (remainingLetters > 0 && remainingWrongGuesses > 0) {
                char c = (char) (random.nextInt(26) + 'a');
                int correct = game.guessLetter(c);
                if(correct > 0)
                    remainingLetters -= correct;
                if (correct == -1)
                    remainingWrongGuesses--;
                Assertions.assertEquals(remainingLetters, game.getRemainingLetters());
                Assertions.assertEquals(remainingWrongGuesses, game.getRemainingWrongGuesses());
            }
        }
    }

    @Test
    void testScore() throws IOException {
        Score score1 = new Score("John", 18);
        score1.write("./HangmanFunctional/TestScores.txt");
        Score score2 = new Score("Jake", 15);
        score2.write("./HangmanFunctional/TestScores.txt");
        Score score3 = new Score("Jerry", 16);
        score3.write("./HangmanFunctional/TestScores.txt");
        Score highScore = Score.readScores("./HangmanFunctional/TestScores.txt").stream().max((scoreA, scoreB) -> scoreA.getScore() > scoreB.getScore() ? 1 : -1).get();
        Assertions.assertEquals(18, highScore.getScore());
        Assertions.assertEquals("John", highScore.getName());
    }
}