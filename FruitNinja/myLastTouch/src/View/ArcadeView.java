package View;

import Model.Model;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArcadeView {
    private static ArcadeView ourInstance = new ArcadeView();
    private Scene scene;
    private Group group = new Group();
    private Screen screen;
    private Model model = Model.getInstance();
    private Rectangle2D bounds;
    private Circle cursor;
    private Text scoreLabel;
    private Text scoreText;
    private Text highscoreLabel;
    private Text highscoreText;
    private Text timeLabel;
    private Text timeText;

    public Timeline getElapsedTime() {
        return elapsedTime;
    }

    private Timeline elapsedTime;
    private int score = 0;
    private Line l;
    private AudioClip heart;
    private AudioClip throwbomb;
    private AudioClip throwfruit;
    private Button pause;
    private Button resume;
    private Button restart;
    private List<Timeline> timelines = new ArrayList<>();
    private Controller controller = Controller.getInstance();
    private Image split = new Image("Assets/kisspng-abdel-fattah-el-sisi-egyptian-presidential-electio-5b29d2252809d7.104900651529467429164.png");


    private ArcadeView() {
    }

    public static ArcadeView getInstance() {
        return ourInstance;
    }

    public void start() {
        heart = new AudioClip(Paths.get("src/Assets/za3ama.mp3").toUri().toString());
        throwbomb = new AudioClip(Paths.get("src/Assets/Throw-bomb.wav").toUri().toString());
        throwfruit = new AudioClip(Paths.get("src/Assets/Throw-fruit.wav").toUri().toString());
        cursor = new Circle(10, 10, 10, Color.GREEN);
        screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        buttons();
        group.getChildren().addAll(pause, resume, restart);

        scene = new Scene(group, bounds.getWidth(), bounds.getHeight());
        Image image4 = new Image("Assets/background.png");
        scene.setFill(new ImagePattern(image4));
        Image imagecur = new Image("Assets/blade.png");
        scene.setCursor(new ImageCursor(imagecur));
        //getscore sets the labels and gets the score
        getScore();

        swipe();
    }

    public void buttons() {
        pause = new Button("Pause");
        resume = new Button("Resume");
        restart = new Button("Restart");
        resume.setLayoutX(50);
        restart.setLayoutX(150);

        pause.setOnAction(e -> {
            scene.setOnMouseDragged(null);
            controller.pauseGame("arcade");

        });

        resume.setOnAction(e -> {
            controller.resumeGame("arcade");
        });

        restart.setOnAction(e -> {
            controller.restartGameArcade();
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

        timeLabel = new Text("Time: ");
        timeLabel.setFill(Color.WHITE);
        timeLabel.setStyle("-fx-font: 24 arial;");
        timeLabel.setLayoutX(500);
        timeLabel.setLayoutY(100);

        timeText = new Text("" + model.getTime());
        timeText.setFill(Color.WHITE);
        timeText.setStyle("-fx-font: 24 arial;");
        timeText.setLayoutX(590);
        timeText.setLayoutY(100);

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
        //udpdateHighScore();

        group.getChildren().addAll(scoreText, scoreLabel, timeText, timeLabel, cursor, highscoreLabel, highscoreText);
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
                        controller.slicedArcade(Controller.myArray.get(i).toString(), i);
                    }
                }
            }
        });

    }

    public void elapseTime() {
        elapsedTime = new Timeline(new KeyFrame(Duration.ZERO, event -> {

            System.out.println(model.getTime());
            model.decreaseTime();
            timeText.setText(" " + model.getTime());
            if (model.getTime() < 0) {
                model.setTime(0);
                timeText.setText(" " + model.getTime());

            }
        }),
                new KeyFrame(Duration.seconds(1)));
        elapsedTime.setCycleCount(model.getTime());
        elapsedTime.play();

    }

    public Rectangle2D getBounds() {
        return this.bounds;
    }

    public Scene getScene() {
        return this.scene;
    }

}
