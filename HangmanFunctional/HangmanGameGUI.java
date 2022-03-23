package genspark.projects.HangmanFunctional;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HangmanGameGUI {
    private JLabel enterNameLabel;
    private JTextField enterNameTextField;
    private JButton enterNameButton;

    private JLabel alreadyGuessed;
    private JLabel hiddenLetters;
    private JTextField letterEntry;
    private JButton guessButton;
    private JLabel man;

    private JFrame frame;
    private JPanel panel;

    private JLabel endText;
    private JButton playAgain;

    private JLabel scores;

    public HangmanGameGUI() {
        frame = new JFrame();
        frame.setSize(1500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel(new GridLayout());
        frame.add(panel);

        enterNameLabel = new JLabel("Enter your name");
        enterNameTextField = new JTextField();
        enterNameTextField.addActionListener(e -> createGame());
        enterNameButton = new JButton("Enter");
        enterNameButton.addActionListener(e -> createGame());
        BufferedImage manImage;
        try {
            manImage = ImageIO.read(new File("./HangmanFunctional/man.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        man = new JLabel(new ImageIcon(manImage));

        alreadyGuessed = new JLabel();
        hiddenLetters = new JLabel();
        hiddenLetters.setHorizontalAlignment(SwingConstants.CENTER);
        letterEntry = new JTextField();
        letterEntry.addActionListener(e -> evaluateGuess());
        guessButton = new JButton("Guess");
        guessButton.addActionListener(e -> evaluateGuess());

        endText = new JLabel();
        playAgain = new JButton("Play Again");
        playAgain.addActionListener(e -> {
            try {
                HangmanGame.game.newGame(HangmanGame.dictionaryName);
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
            switchToGameBoard();
        });

        scores = new JLabel(Score.readScores(HangmanGame.scoreFile).toString());

        panel.add(enterNameLabel);
        panel.add(enterNameTextField);
        panel.add(enterNameButton);

        frame.setVisible(true);
    }

    private void evaluateGuess() {
        char[] chars = letterEntry.getText().toLowerCase().toCharArray();
        for(char c : chars) {
            if(c >= 'a' && c <= 'z') {
                HangmanGame.game.guessLetter(c);
            }
            if(isGameEnd()) {
                return;
            }
        }
        updateGameBoard();
    }

    private void createGame() {
        try {
            HangmanGame.game = new HangmanGame(enterNameTextField.getText());
            HangmanGame.game.newGame(HangmanGame.dictionaryName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        switchToGameBoard();
    }

    private void switchToGameBoard() {
        panel.removeAll();
        panel.add(alreadyGuessed);
        panel.add(hiddenLetters);
        panel.add(letterEntry);
        panel.add(guessButton);
        panel.add(man);
        updateGameBoard();
        panel.revalidate();
        panel.repaint();
    }

    private boolean isGameEnd() {
        if(HangmanGame.game.getRemainingLetters() == 0) {
            switchToEndScreen(true);
            return true;
        } else if(HangmanGame.game.getRemainingWrongGuesses() == 0) {
            switchToEndScreen(false);
            return true;
        }
        return false;
    }

    private void updateGameBoard() {
        letterEntry.setText("");
        hiddenLetters.setText(HangmanGame.game.hiddenWord());
        alreadyGuessed.setText(HangmanGame.game.scoreKeeping());
    }

    private void switchToEndScreen(boolean victory) {
        if(victory) {
            endText.setText("You won, the word was " + HangmanGame.game.getWord());
            HangmanGame.game.setScore(HangmanGame.game.getScore() + 1);
        } else {
            endText.setText("You lost, the word was " + HangmanGame.game.getWord());
            HangmanGame.game.recordScore();
            HangmanGame.game.setScore(0);
        }
        scores.setText(Score.readScores(HangmanGame.scoreFile).toString());
        panel.removeAll();
        panel.add(endText);
        panel.add(playAgain);
        panel.add(scores);
        panel.revalidate();
        panel.repaint();
    }
}
