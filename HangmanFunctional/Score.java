package genspark.projects.HangmanFunctional;

import java.io.*;
import java.util.ArrayList;

class Score implements Serializable {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String toString() {
        return name + ": " + score;
    }

    public boolean write(String filename) {
        ArrayList<Score> currentScores = readScores(filename);
        if(currentScores == null) {
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            currentScores.add(this);
            objectOutputStream.writeObject(currentScores);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Problem writing out high score.");
            return false;
        }
        return true;
    }

    public static ArrayList<Score> readScores(String filename) {
        ArrayList<Score> scores;
        try{
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            scores = (ArrayList<Score>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }catch (Exception e) {
            System.out.println("Problem reading high scores. If first time, possible that score file is blank.");
            e.printStackTrace();
            scores = new ArrayList<>();
        }
        return scores;
    }
}
