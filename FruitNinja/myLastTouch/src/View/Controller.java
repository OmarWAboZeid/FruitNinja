package View;

import Fruit.FruitFactory;
import Fruit.FruitStrategy;
import Model.Model;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller implements View.GameActions {
    protected static List<FruitStrategy> myArray = new ArrayList<>();
    private static Controller obj = new Controller();
    int randomnumber;
    private Model Model;
    private View.ClassicView ClassicView;
    private View.ArcadeView arcadeView;
    private Stage stage;
    private FruitFactory fruitFactory = new FruitFactory();
    private Timeline timeline;
    private Timeline timeline2;
    private int highscore;
    private int bombs;
    private String speed;

    private Controller() {
    }

    public static Controller getInstance() {
        if (obj == null)
            obj = new Controller();
        return obj;
    }

    public ClassicView getClassicView() {
        return ClassicView;
    }

    public void setClassicView(View.ClassicView classicView) {
        ClassicView = classicView;
    }

    public Model getModel() {
        return Model;
    }

    public void setModel(Model model) {
        Model = model;
    }

    public void startClassic(String mode) {
        ClassicView.start();

        if(mode.equals("easy"))
        {
            bombs = 2;
            speed="normal";
        } else if(mode.equals("medium")){
            bombs = 4;
            speed="faster";
        } else{
            bombs = 6;
            speed="fastest";
        }
        ClassicView.setScore(Model.getScore());
        ClassicView.setLives(Model.getLives());
        fruitFactory.setBounds(ClassicView.getBounds());
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> { //randomnumber= random.nextInt((max - min) + 1) + min; // spare method if you want to include numbers within a certain range.
            Random random = new Random();
            int max = 10;
            randomnumber = random.nextInt(5); // Generates a random number between 0 and 5 inclusive
            ClassicView.clearScene();
            myArray.clear();
            ClassicView.clearTimelines();
            for (int i = 0; i < randomnumber; i++) {
                myArray.add(fruitFactory.getFruit("strawberry", speed));
            }

            int lastSize = myArray.size();///This procedure is used we add new elements to the end of the array list,(where we stopped), so that the objects in the array list won't get overridden
            for (int i = myArray.size(); i < lastSize + random.nextInt(5); i++) {
                myArray.add(fruitFactory.getFruit("grape", speed));
            }
            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(5); i++) {
                myArray.add(fruitFactory.getFruit("apple", speed));

            }
            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(bombs); i++) {
                myArray.add(fruitFactory.getFruit("FatalBomb", speed));
            }

            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(2); i++) {
                myArray.add(fruitFactory.getFruit("heart", speed));


            }
            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(2); i++) {
                myArray.add(fruitFactory.getFruit("starfruit", speed));
            }

            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(bombs); i++) {
                myArray.add(fruitFactory.getFruit("bomb", speed));
            }
            ClassicView.fillScene();
            ClassicView.move();

        }),
                new KeyFrame(Duration.seconds(5))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        timeline2 = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            for (int i = 0; i < myArray.size(); i++) {
                if (((myArray.get(i).getEnd_x() - (int) myArray.get(i).Circle().getCenterX()) == 400 || myArray.get(i).getEnd_x() == (int) myArray.get(i).Circle().getCenterX())) {

                    if (myArray.get(i).toString().equals("FatalBomb")) //If the fallen item is a FatalBomb, just remove it from the array, and the group
                    {
                        ClassicView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).toString().equals("starfruit")) //If the fallen item is a starfruit, just remove it from the array, and the group
                    {
                        ClassicView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).toString().equals("bomb")) //If the fallen item is a bomb, just remove it from the array, and the group
                    {
                        ClassicView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).toString().equals("heart")) //If the fallen item is a bomb, just remove it from the array, and the group
                    {
                        ClassicView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).getImage() == myArray.get(i).getImage2()) //If the fallen item is a sliced fruit, just remove it from the array, and the group
                    {

                        ClassicView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else { //If the item is not a special fruit
                        ClassicView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                        Model.decreaseLives();
                        ClassicView.setLives(Model.getLives());
                    }
                }
            }

            if (Model.getScore() > Model.getHighscore()) {
                Model.setHighscore(Model.getScore());
                saveGame(Model.getHighscore());
                getClassicView().updatehighscoretext(Model.getScore());
            }

            if (Model.getLives() == 0) {
                timeline.stop();
                timeline2.stop();
                ClassicView.stopMoving();
                ClassicView.showGameOver();
            }
        }),
                new KeyFrame(Duration.millis(10)));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
        this.stage.setScene(getClassicView().getScene());

    }

    public void sliced(String fruit, int i) {
        if (fruit.equals("FatalBomb")) //If the sliced fruit is a fatal bomb, stop the game.
        {
            ClassicView.setScore(Model.getScore());
            saveGame(Model.getScore());
            timeline2.stop();
            timeline.stop();
            ClassicView.stopMoving();
            ClassicView.showGameOver();
        } else if (fruit.equals("heart") && myArray.get(i).getisSliced() == false) //If the sliced fruit is a heart, add one more live.
        {
            Model.increaseLives();
            ClassicView.setLives(Model.getLives());
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);

        } else if (fruit.equals("starfruit") && myArray.get(i).getisSliced() == false) //If the sliced fruit is a starfruit, add 100 points to the score.
        {
            Model.increasescoreby100();
            ClassicView.setScore(Model.getScore());
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);
        } else if (fruit.equals("bomb") && myArray.get(i).getisSliced() == false) //If the sliced fruit is a bomb, decrease the lives by 1.
        {

            Model.decreaseLives();
            ClassicView.setLives(Model.getLives());
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);
        } else if (myArray.get(i).getisSliced() == false) //If the sliced fruit is just a normal fruit, increase the score once.
        {
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);
            Model.increaseScore();
            ClassicView.setScore(Model.getScore());
        }
    }

    public void slicedArcade(String fruit, int i) {
        if (fruit.equals("FatalBomb")) //If the sliced fruit is a fatal bomb, stop the game.
        {
            stop();
            arcadeView.stopMoving();
        } else if (fruit.equals("heart") && myArray.get(i).getisSliced() == false) //If the sliced fruit is a heart, add one more live.
        {
            getModel().setTime(getModel().getTime() + 10);
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);

        } else if (fruit.equals("starfruit") && myArray.get(i).getisSliced() == false) //If the sliced fruit is a starfruit, add 100 points to the score.
        {
            Model.increasescoreby100();
            arcadeView.setScore(Model.getScore());
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);
        } else if (fruit.equals("bomb") && myArray.get(i).getisSliced() == false) //If the sliced fruit is a bomb, decrease the lives by 1.
        {

            getModel().setTime(getModel().getTime() - 10);
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);
        } else if (myArray.get(i).getisSliced() == false) //If the sliced fruit is just a normal fruit, increase the score once.
        {
            myArray.get(i).setImage();
            myArray.get(i).setisSlicedF(true);
            Model.increaseScore();
            arcadeView.setScore(Model.getScore());
        }
        if (getModel().getHighscore() < getModel().getScore()) {
            getModel().setHighscore(getModel().getScore());
        }
    }

    public void pauseGame(String mode) {
        timeline2.pause();
        timeline.pause();
        if (mode.equals("arcade")) {
            arcadeView.pauseMoving();
            arcadeView.getElapsedTime().stop();
        } else {
            ClassicView.pauseMoving();
        }
    }

    public void resumeGame(String mode) {
        if (timeline.getStatus().toString().equals("PAUSED")) {
            if (mode.equals("arcade")) {
                arcadeView.swipe();
                arcadeView.resumeMoving();
                arcadeView.getElapsedTime().play();
            } else {
                ClassicView.swipe();
                ClassicView.resumeMoving();
            }
            timeline.play();
            timeline2.play();
        }

    }

    public void restartGame() {
        timeline2.stop();
        timeline.stop();
        ClassicView.clearScene();
        myArray.clear();
        Model.reset();
        ClassicView.setLives(Model.getLives());
        ClassicView.setScore(Model.getScore());
        timeline.setDelay(Duration.millis(2000));
        timeline.play();
        timeline2.play();
        ClassicView.move();
        ClassicView.updatehighscoretext(Model.getHighscore());

    }

    public void restartGameArcade() {
        timeline2.stop();
        getModel().setTime(60);
        timeline.stop();
        arcadeView.clearScene();
        myArray.clear();
        Model.reset();
        arcadeView.setScore(Model.getScore());
        timeline.setDelay(Duration.millis(2000));
        timeline.play();
        timeline2.play();
        arcadeView.move();
        arcadeView.elapseTime();
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    @Override
    public void updateObjectsLocations() {
        // TODO Auto-generated method stub

    }

    @Override
    public void sliceObjects() {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveGame() {

    }


    @Override
    public int loadGame() {
        int x1=0;
        try {

            File xmlFiler = new File("data.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFiler);


                NodeList list = document.getElementsByTagName("Score");


                    Node node = list.item(0);
                    Element elemented = (Element) node;

                    x1 = Integer.parseInt(elemented.getFirstChild().getTextContent());

        } catch (Exception e) {
            System.out.println(e);
        }

        return x1;
    }

    @Override
    public void ResetGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveGame(int score) {

            try {

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element scoreE = document.createElement("Score");
                Text scoreText = document.createTextNode(""+score);
                scoreE.appendChild(scoreText);

                document.appendChild(scoreE);


                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("data.xml"));
                transformer.transform(source, streamResult);


            } catch (Exception e) {
                System.out.println(e);
            }
        }


    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public void startArcade() {
        arcadeView.start();
        arcadeView.setScore(Model.getScore());
        fruitFactory.setBounds(arcadeView.getBounds());
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> { //randomnumber= random.nextInt((max - min) + 1) + min; // spare method if you want to include numbers within a certain range.
            Random random = new Random();
            int max = 10;
            randomnumber = random.nextInt(5); // Generates a random number between 0 and 5 inclusive
            arcadeView.clearScene();
            myArray.clear();
            arcadeView.clearTimelines();
            for (int i = 0; i < randomnumber; i++) {
                myArray.add(fruitFactory.getFruit("strawberry", "normal"));
            }

            int lastSize = myArray.size();///This procedure is used we add new elements to the end of the array list,(where we stopped), so that the objects in the array list won't get overridden
            for (int i = myArray.size(); i < lastSize + random.nextInt(5); i++) {
                myArray.add(fruitFactory.getFruit("grape", "normal"));
            }
            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(5); i++) {
                myArray.add(fruitFactory.getFruit("apple", "normal"));

            }
            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(2); i++) {
                myArray.add(fruitFactory.getFruit("FatalBomb", "normal"));
            }

            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(2); i++) {
                myArray.add(fruitFactory.getFruit("heart", "normal"));


            }
            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(2); i++) {
                myArray.add(fruitFactory.getFruit("starfruit", "normal"));
            }

            lastSize = myArray.size();
            for (int i = myArray.size(); i < lastSize + random.nextInt(2); i++) {
                myArray.add(fruitFactory.getFruit("bomb", "normal"));
            }
            arcadeView.fillScene();
            arcadeView.move();

        }),
                new KeyFrame(Duration.seconds(5))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //Timeline for checking falling objects
        timeline2 = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            for (int i = 0; i < myArray.size(); i++) {
                if (((myArray.get(i).getEnd_x() - (int) myArray.get(i).Circle().getCenterX()) == 400 || myArray.get(i).getEnd_x() == (int) myArray.get(i).Circle().getCenterX())) {

                    if (myArray.get(i).toString().equals("FatalBomb")) //If the fallen item is a FatalBomb, just remove it from the array, and the group
                    {
                        arcadeView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).toString().equals("starfruit")) //If the fallen item is a starfruit, just remove it from the array, and the group
                    {
                        arcadeView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).toString().equals("bomb")) //If the fallen item is a bomb, just remove it from the array, and the group
                    {
                        arcadeView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else if (myArray.get(i).getImage() == myArray.get(i).getImage2()) //If the fallen item is a sliced fruit, just remove it from the array, and the group
                    {

                        arcadeView.removeFruit(i);
                        myArray.remove(myArray.get(i));
                    } else { //If the item is not a special fruit
                        arcadeView.removeFruit(i);
                        myArray.remove(myArray.get(i));

                    }
                }
            }
            if (getModel().getTime() == 0) {
                // if (arcadeView.getScore(); > loadGame())
                //saveGame(score);
                // scene.setOnMouseDragged(null);
                timeline.stop();
                arcadeView.showGameOver();
                arcadeView.stopMoving();
                timeline2.stop();
                arcadeView.clearScene();
                //stop();

            }
        }),
                new KeyFrame(Duration.millis(10)));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
        this.stage.setScene(arcadeView.getScene());

    }

    //Method to stop objects from moving
    public void stop() {
        timeline.stop();
        timeline2.stop();
        arcadeView.getElapsedTime().stop();

        for (int j = 0; j < myArray.size(); j++) {
            myArray.get(j).stopMoving();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Note");
        alert.setHeaderText("YOU LOSE");
        alert.setContentText("Lost");
        arcadeView.getScene().setOnMouseDragged(null);
        alert.show();
    }

    public void setArcadeView(ArcadeView arcadeView) {
        this.arcadeView = arcadeView;
    }

   /* public void udpdateHighScore(int score) {
        Timeline highScore = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            if (score > loadGame()) {
                saveGame(score);
                getModel().setHighscore(score);
            }
        }),
                new KeyFrame(Duration.millis(10)));
        highScore.setCycleCount(Animation.INDEFINITE);
        highScore.play();
    }*/
}
