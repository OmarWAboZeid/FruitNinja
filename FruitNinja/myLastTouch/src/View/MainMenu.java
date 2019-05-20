package View;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.security.Key;

import static javafx.util.Duration.seconds;

public class MainMenu {
    private static MainMenu ourInstance = new MainMenu();
    private ArcadeView arcadeView;
    private Timeline timelines= new Timeline(); //GLOBAL VARIABLE
    private Text classic;
    private Text arcade;
    private Text difficulty;
    private Text easy;
    private Text medium;
    private Text hard;


    public static MainMenu getInstance() {
        return ourInstance;
    }

    private MainMenu() {
    }

    private Scene mainmenuscene;
    private Controller controller;
    private Stage stage;
    private boolean circle = false;
    public Scene getMainmenuscene() {
        return mainmenuscene;
    }

    public void setMainmenuscene(Scene mainmenuscene) {
        this.mainmenuscene = mainmenuscene;
    }


    public void PrepareMainmenuscene()
    {
        Screen screen = Screen.getPrimary();
        Rectangle2D screenbounds = screen.getBounds();
        Group root = new Group();
        mainmenuscene = new Scene(root, screenbounds.getWidth(), screenbounds.getHeight());
        mainmenuscene.setFill(new ImagePattern(new Image("Assets/lel.png")));

        classic = new Text("Classic");
        classic.setFill(Color.DARKGREEN);
        classic.setStyle("-fx-font: 35 arial;");
        classic.setLayoutX(250);
        classic.setLayoutY(270);

        arcade = new Text("Arcade");
        arcade.setFill(Color.YELLOW);
        arcade.setStyle("-fx-font: 35 arial;");
        arcade.setLayoutX(700);
        arcade.setLayoutY(270);

        difficulty = new Text("Choose the difficlty");
        difficulty.setFill(Color.ORANGE);
        difficulty.setStyle("-fx-font: 35 arial;");
        difficulty.setLayoutX(250);
        difficulty.setLayoutY(270);

        easy = new Text("Easy");
        easy.setFill(Color.ORANGE);
        easy.setStyle("-fx-font: 29 arial;");
        easy.setLayoutX(270);
        easy.setLayoutY(500);

        medium = new Text("Medium");
        medium.setFill(Color.ORANGE);
        medium.setStyle("-fx-font: 29 arial;");
        medium.setLayoutX(350);
        medium.setLayoutY(500);

        hard = new Text("Hard");
        hard.setFill(Color.ORANGE);
        hard.setStyle("-fx-font: 29 arial;");
        hard.setLayoutX(490);
        hard.setLayoutY(500);


        root.getChildren().addAll(classic, arcade);

        Circle watermeloncircle = new Circle();
        watermeloncircle.setRadius(100);
        watermeloncircle.setFill(new ImagePattern(new Image("Assets/watermelon.png")));
        watermeloncircle.setLayoutX(400);
        watermeloncircle.setLayoutY(400);

        Circle bananacircle = new Circle();
        bananacircle.setRadius(100);
        bananacircle.setFill(new ImagePattern(new Image("Assets/banana.png")));
        bananacircle.setLayoutX(800);
        bananacircle.setLayoutY(400);


        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(10);
        dropShadow.setOffsetY(10);
        dropShadow.setColor(Color.BLACK);

        bananacircle.setEffect(dropShadow);
        watermeloncircle.setEffect(dropShadow);

        Circle cursor = new Circle(10, 10, 10, Color.GREEN);
        Image imagecur = new Image("Assets/blade.png");
        mainmenuscene.setCursor(new ImageCursor(imagecur));

        //banana rotate trans
        RotateTransition rotateTransition = new RotateTransition(seconds(3), root);
        rotateTransition.setFromAngle(360);
        rotateTransition.setToAngle(0);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setCycleCount(10000000);
        rotateTransition.setNode(bananacircle);
        rotateTransition.play();

        RotateTransition rotateTransition2 = new RotateTransition(seconds(3), root);
        rotateTransition2.setFromAngle(0);
        rotateTransition2.setToAngle(360);
        rotateTransition2.setAutoReverse(false);
        rotateTransition2.setCycleCount(10000000);
        rotateTransition2.setNode(watermeloncircle);
        rotateTransition2.play();

        //easy orange ***************************************************************************************
        Circle easyorange = new Circle();
        easyorange.setRadius(30);
        easyorange.setFill(new ImagePattern(new Image("Assets/easy.png")));
        easyorange.setLayoutX(300);
        easyorange.setLayoutY(400);
        RotateTransition rotateTransition3 = new RotateTransition(seconds(3), root);
        rotateTransition3.setFromAngle(360);
        rotateTransition3.setToAngle(0);
        rotateTransition3.setAutoReverse(false);
        rotateTransition3.setCycleCount(10000000);
        rotateTransition3.setNode(easyorange);
        rotateTransition3.play();

        //medium orange
        Circle mediumorange = new Circle();
        mediumorange.setRadius(30);
        mediumorange.setFill(new ImagePattern(new Image("Assets/medium.png")));
        mediumorange.setLayoutX(390);
        mediumorange.setLayoutY(400);
        RotateTransition rotateTransition4 = new RotateTransition(seconds(3), root);
        rotateTransition4.setFromAngle(360);
        rotateTransition4.setToAngle(0);
        rotateTransition4.setAutoReverse(false);
        rotateTransition4.setCycleCount(10000000);
        rotateTransition4.setNode(mediumorange);
        rotateTransition4.play();

        //hardorange
        Circle hardorange = new Circle();
        hardorange.setRadius(30);
        hardorange.setFill(new ImagePattern(new Image("Assets/hard.png")));
        hardorange.setLayoutX(490);
        hardorange.setLayoutY(400);
        RotateTransition rotateTransition5 = new RotateTransition(seconds(3), root);
        rotateTransition5.setFromAngle(360);
        rotateTransition5.setToAngle(0);
        rotateTransition5.setAutoReverse(false);
        rotateTransition5.setCycleCount(10000000);
        rotateTransition5.setNode(hardorange);
        rotateTransition5.play();

        easyorange.setEffect(dropShadow);
        mediumorange.setEffect(dropShadow);
        hardorange.setEffect(dropShadow);
        //**************************************************************************************************************

        //easy orange ***************************************************************************************

        root.getChildren().add(watermeloncircle);
        root.getChildren().add(bananacircle);


        mainmenuscene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cursor.setCenterX(event.getSceneX());
                cursor.setCenterY(event.getSceneY());
                watermeloncircle.setFill(new ImagePattern(new Image("Assets/watermelonsliced.png")));
                if (cursor.getBoundsInParent().intersects(watermeloncircle.getBoundsInParent()) && circle == false) {

                    circle = true;




                    KeyValue xKV = new KeyValue(watermeloncircle.centerXProperty(), 500); //500 is where the X ends
                    KeyValue yKV = new KeyValue(watermeloncircle.centerYProperty(), 500, new Interpolator() { //500 is where the Y ends
                        @Override
                        protected double curve(double t) {
                            return 2 * (t);

                        }
                    });
                    KeyFrame xKF = new KeyFrame(Duration.millis(5000), xKV); //How long it takes to move the watermellon
                    KeyFrame yKF = new KeyFrame(Duration.millis(5000), yKV); //How long it takes to move the watermellon
                    timelines.getKeyFrames().addAll(xKF, yKF);
                    timelines.play();


                    Timeline     timelinez = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                        root.getChildren().remove(watermeloncircle);
                        root.getChildren().remove(classic);
                        root.getChildren().addAll(difficulty, easy, medium, hard);
                        root.getChildren().add(easyorange);
                        root.getChildren().add(mediumorange);
                        root.getChildren().add(hardorange);
                    }),
                            new KeyFrame(Duration.seconds(1)) //Adds them within 1 second
                    );
                    timelinez.setDelay(Duration.millis(3000)); /// THIS HAS TO BE MORE THAN THAT OF THE FIRST TIMELINE
                    timelinez.play();
                }
                else if (cursor.getBoundsInParent().intersects(bananacircle.getBoundsInParent())) {
                    arcadeView.elapseTime();
                    controller.startArcade();

                }
                else if (cursor.getBoundsInParent().intersects(easyorange.getBoundsInParent())&&timelines.getStatus().toString().equals("STOPPED")) {
                    System.out.println("easy");
                    root.getChildren().remove(easyorange);
                    controller.startClassic("easy");
                }
                else if (cursor.getBoundsInParent().intersects(mediumorange.getBoundsInParent())&&timelines.getStatus().toString().equals("STOPPED")) {
                    System.out.println("medium");
                    root.getChildren().remove(mediumorange);
                    controller.startClassic("medium");
                }
                else if (cursor.getBoundsInParent().intersects(hardorange.getBoundsInParent())&&timelines.getStatus().toString().equals("STOPPED")) {
                    System.out.println("hard");
                    root.getChildren().remove(hardorange);
                    controller.startClassic("hard");
                }
            }

        });
/*        mainmenuscene.setOnMouseDragOver(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cursor.setCenterX(event.getSceneX());
                cursor.setCenterY(event.getSceneY());
                if (cursor.getBoundsInParent().intersects(easyorange.getBoundsInParent())) {
                    root.getChildren().remove(easyorange);
                    controller.startClassic("easy");
                }
                else if (cursor.getBoundsInParent().intersects(mediumorange.getBoundsInParent())) {
                    root.getChildren().remove(mediumorange);
                    controller.startClassic("easy");
                }
                else if (cursor.getBoundsInParent().intersects(hardorange.getBoundsInParent())) {
                    root.getChildren().remove(hardorange);
                    controller.startClassic("easy");
                }
            }
        });*/
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setArcade(ArcadeView arcadeView) {
        this.arcadeView = arcadeView;
    }
}
