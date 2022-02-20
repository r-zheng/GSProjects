package genspark.projects.Project_1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Scanner;

class DragonCaveTest {

    @Test
    void getIntegerFromInput() {
        for(int i = -5; i < 10; i++) {              // check if input string is i, function should output i
            int output = DragonCave.getIntegerFromInput(new Scanner(new java.io.ByteArrayInputStream((i + "\n").getBytes())));

            Assertions.assertEquals(i, output);
        }
    }
}