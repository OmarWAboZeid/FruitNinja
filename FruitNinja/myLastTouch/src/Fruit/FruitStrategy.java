package Fruit;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public interface FruitStrategy {
    public void stopMoving();
    public void resume();
    public int getEnd_x();
    public boolean getisSliced();
    public void setisSlicedF(boolean isSliced);
    public Circle Circle();
    public Image getImage();
    public void setImage();
    public Image getImage2();
    public String toString();
    public int getStart_x();
    public int getStart_y();
    public int getSpeed();
    public int getEnd_y();
}
