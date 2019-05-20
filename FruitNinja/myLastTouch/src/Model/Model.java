package Model;

import View.Controller;

public class Model {
    private static Model ourInstance = new Model();
    private Controller controller = Controller.getInstance();

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }
    private int score = 0;

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    private int highscore = controller.loadGame();
    private int lives = 4;
    private static int time = 60;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void decreaseTime() {
        time--;
    }



    public void increaseScore() {
        score++;
    }

    public void decreaseScore() {
        score--;
    }

    public void increaseLives() {
        lives++;
    }

    public void decreaseLives() {
        lives--;
    }


    public void increasescoreby100() {
        score = score + 100;
    }

    public void reset() {
        score = 0;
        lives = 4;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }
}
