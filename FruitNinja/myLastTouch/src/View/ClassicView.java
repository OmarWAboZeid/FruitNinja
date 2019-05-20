package View;

import Model.Model;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ClassicView {
    private static ClassicView ourInstance = new ClassicView();

    public static ClassicView getInstance() {
        return ourInstance;
    }
    private Model model = Model.getInstance();

    private ClassicView() {
    }
    private Controller control = Controller.getInstance();

    private Scene scene;
    private Group group = new Group();
    private Screen screen;
    private Rectangle2D bounds;
    private Circle cursor;
    private Text scoreLabel;
    private Text scoreText;
    private Text highscoreLabel;
    private Text highscoreText;
    private Text livesLabel;
    private Text livesText;
    private Button pause;
    private Button resume;
    private Button restart;
    private List<Timeline> timelines = new ArrayList<>();

    public Rectangle2D getBounds() {
        return bounds;
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        cursor = new Circle(10, 10, 10, Color.GREEN);
        screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        scene = new Scene(group, bounds.getWidth(), bounds.getHeight());
        Image image4 = new Image("Assets/background.png");
        scene.setFill(new ImagePattern(image4));
        buttons();
        group.getChildren().addAll(pause, resume, restart);
        getScore();
       // control.udpdateHighScore(model.getScore());
        updatehighscoretext(model.getHighscore());
        swipe();
    }

    public void updatehighscoretext(int highscore)
    {
        highscoreText.setText("" + highscore);
    }


    public void buttons() {
        pause = new Button("Pause");
        resume = new Button("Resume");
        restart = new Button("Restart");
        resume.setLayoutX(50);
        restart.setLayoutX(150);

        pause.setOnAction(e -> {
            scene.setOnMouseDragged(null);
            control.pauseGame("classic");
        });

        resume.setOnAction(e -> {
            control.resumeGame("classic");
        });

        restart.setOnAction(e -> {
            control.restartGame();
            swipe();
        });
    }

    public void fillScene() {
        for (int i = 0; i < Controller.myArray.size(); i++) {
            group.getChildren().add(Controller.myArray.get(i).Circle());
        }


    }

    public void setScore(int score) {
        scoreText.setText("" + score);
    }

    public void setLives(int lives) {
        livesText.setText("" + lives);
    }

    public void removeFruit(int i) {

        group.getChildren().remove(Controller.myArray.get(i).Circle());
    }

    public void showGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Note");
        alert.setHeaderText("YOU LOSE");
        alert.setContentText("Bye");
        scene.setOnMouseDragged(null);
        alert.show();
    }

    public void stopMoving() {
        for (int i = 0; i < timelines.size(); i++) {
            timelines.get(i).stop();
        }
    }

    public void pauseMoving() {
        for (int i = 0; i < timelines.size(); i++) {
            timelines.get(i).pause();
        }
    }

    public void resumeMoving() {
        control.saveGame(model.getScore());
        for (int i = 0; i < timelines.size(); i++) {
            timelines.get(i).play();
        }
    }

    public void clearTimelines() {
        timelines.clear();
    }

    public void getScore() {

        scoreLabel = new Text("Score: ");
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setStyle("-fx-font: 24 arial;");
        scoreLabel.setLayoutX(100);
        scoreLabel.setLayoutY(100);

        scoreText = new Text("");
        scoreText.setFill(Color.WHITE);
        scoreText.setStyle("-fx-font: 24 arial;");
        scoreText.setLayoutX(190);
        scoreText.setLayoutY(100);

        livesLabel = new Text("Lives: ");
        livesLabel.setFill(Color.WHITE);
        livesLabel.setStyle("-fx-font: 24 arial;");
        livesLabel.setLayoutX(500);
        livesLabel.setLayoutY(100);

        livesText = new Text("");
        livesText.setFill(Color.WHITE);
        livesText.setStyle("-fx-font: 24 arial;");
        livesText.setLayoutX(590);
        livesText.setLayoutY(100);

        highscoreLabel = new Text("High Score: ");
        highscoreLabel.setFill(Color.WHITE);
        highscoreLabel.setStyle("-fx-font: 24 arial;");
        highscoreLabel.setLayoutX(100);
        highscoreLabel.setLayoutY(300);

        highscoreText = new Text("");
        highscoreText.setFill(Color.WHITE);
        highscoreText.setStyle("-fx-font: 24 arial;");
        highscoreText.setLayoutX(250);
        highscoreText.setLayoutY(300);

        group.getChildren().addAll(scoreText, scoreLabel, livesText, livesLabel, cursor, highscoreLabel, highscoreText);
    }

    public void clearScene() {
        for (int i = 0; i < Controller.myArray.size(); i++)
            group.getChildren().remove(Controller.myArray.get(i).Circle());

    }

    public void move() {

        for (int i = 0; i < Controller.myArray.size(); i++) {
            timelines.add(new Timeline());
        }
        for (int i = 0; i < Controller.myArray.size(); i++) {

            if (Controller.myArray.get(i).getStart_x() < bounds.getMaxX() / 2) {

                DropShadow dropShadow = new DropShadow();
                dropShadow.setOffsetX(10);
                dropShadow.setOffsetY(10);
                dropShadow.setColor(Color.BLACK);
                Controller.myArray.get(i).Circle().setEffect(dropShadow);
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3));
                rotateTransition.setFromAngle(360);
                rotateTransition.setToAngle(0);
                rotateTransition.setAutoReverse(false);
                rotateTransition.setCycleCount(10000000);
                rotateTransition.setNode(Controller.myArray.get(i).Circle());
                rotateTransition.play();

                timelines.get(i).setAutoReverse(false);
                KeyValue xKV = new KeyValue(Controller.myArray.get(i).Circle().centerXProperty(), Controller.myArray.get(i).getEnd_x());
                KeyValue yKV = new KeyValue(Controller.myArray.get(i).Circle().centerYProperty(), Controller.myArray.get(i).getEnd_y(), new Interpolator() {
                    @Override
                    protected double curve(double t) {
                        return -4 * (t - .5) * (t - .5) + 1;

                    }
                });

                KeyFrame xKF = new KeyFrame(Duration.millis(Controller.myArray.get(i).getSpeed()), xKV);
                KeyFrame yKF = new KeyFrame(Duration.millis(Controller.myArray.get(i).getSpeed()), yKV);
                timelines.get(i).getKeyFrames().addAll(xKF, yKF);
                timelines.get(i).play();

            } else {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setOffsetX(10);
                dropShadow.setOffsetY(10);
                dropShadow.setColor(Color.BLACK);
                Controller.myArray.get(i).Circle().setEffect(dropShadow);
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3));
                rotateTransition.setFromAngle(360);
                rotateTransition.setToAngle(0);
                rotateTransition.setAutoReverse(false);
                rotateTransition.setCycleCount(10000000);
                rotateTransition.setNode(Controller.myArray.get(i).Circle());
                rotateTransition.play();
                timelines.get(i).setAutoReverse(false);
                KeyValue xKV = new KeyValue(Controller.myArray.get(i).Circle().centerXProperty(), Controller.myArray.get(i).getEnd_x() - 400);
                KeyValue yKV = new KeyValue(Controller.myArray.get(i).Circle().centerYProperty(), Controller.myArray.get(i).getEnd_y(), new Interpolator() {
                    @Override
                    protected double curve(double t) {
                        return -4 * (t - .5) * (t - .5) + 1;

                    }
                });

                KeyFrame xKF = new KeyFrame(Duration.millis(Controller.myArray.get(i).getSpeed()), xKV);
                KeyFrame yKF = new KeyFrame(Duration.millis(Controller.myArray.get(i).getSpeed()), yKV);
                timelines.get(i).getKeyFrames().addAll(xKF, yKF);
                timelines.get(i).play();

            }
        }
    }


    public void swipe() {

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cursor.setCenterX(event.getSceneX());
                cursor.setCenterY(event.getSceneY());
                for (int i = 0; i < Controller.myArray.size(); i++) {
                    if (cursor.getBoundsInParent().intersects(Controller.myArray.get(i).Circle().getBoundsInParent())) {
                        control.sliced(Controller.myArray.get(i).toString(), i);
                    }
                }
            }
        });
    }
}
