package Fruit;

import java.util.Random;

import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class FruitState {
	protected int start_x;
    protected int start_y;
    protected int end_x;
    protected int end_y;
    protected double radius=30;
    protected int speed;
    protected Image image;
    protected Image image2;
    protected Random random = new Random();
    protected Circle circle;
    boolean isSliced= false;
    protected final Timeline timeline = new Timeline();
    protected  final Timeline timeline2 = new Timeline();
    Rectangle2D bounds;


}
