package genspark.projects.Project_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class GuessTheNumberTest {

    @Test
    void getPlayAgain() {
        for(String s : new String[]{"y", "n"}) {
            String output = GuessTheNumber.getPlayAgain(new Scanner(new java.io.ByteArrayInputStream((s + "\n").getBytes())));

            Assertions.assertEquals(s, output);
        }
        {
            String output = GuessTheNumber.getPlayAgain(new Scanner(new java.io.ByteArrayInputStream(("erwdfw3\ndkfkdsk\ny\n").getBytes())));
            Assertions.assertEquals("y", output);
        }
    }

    @Test
    void getGuess() {
        for(int i = 1; i <= 20; i++) {              // check if input string is i, function should output i
            int output = GuessTheNumber.getGuess(new Scanner(new java.io.ByteArrayInputStream((i + "\n").getBytes())));

            Assertions.assertEquals(i, output);
        }
    }
}