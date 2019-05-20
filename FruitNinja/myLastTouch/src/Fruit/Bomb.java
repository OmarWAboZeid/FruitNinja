package Fruit;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Bomb extends FruitState implements FruitStrategy{


    public Bomb(){

    }
    public Bomb(Rectangle2D bounds, String mode) {
    	image2 = new Image("Assets/kisspng-abdel-fattah-el-sisi-egyptian-presidential-electio-5b29d2252809d7.104900651529467429164.png");
        this.start_x = 50+random.nextInt((int) bounds.getMaxX());
        this.start_y = (int) bounds.getMaxY();
        this.end_x = this.start_x+200;
        this.bounds=bounds;
        this.end_y = 25;
        this.circle = new Circle(this.start_x,this.start_y,this.radius);
        if(mode.equals("normal"))
        {
        this.speed= 3500+ random.nextInt(1500);
        }
        else if (mode.equals("faster")){
        	this.speed=3500;
        }
        else if (mode.equals("fastest")){
        	this.speed=2800;
        }
        this.image = new Image("Assets/bomb.png");
        this.circle.setFill(new ImagePattern(this.image));

    }
    /**
     * @return the start_x
     */
    public Image getImage2()
    {
    	return image2;
    }
    public int getStart_x() {
        return start_x;
    }
    /**
     * @return the start_y
     */
    public int getStart_y() {
        return start_y;
    }
    /**
     * @return the end_x
     */
    public int getEnd_x() {
        return end_x;
    }
    /**
     * @return the end_y
     */
    public int getEnd_y() {
        return end_y;
    }
    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }
    /**
     * @param start_x the start_x to set
     */
    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }
    /**
     * @param start_y the start_y to set
     */
    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }
    /**
     * @param end_x the end_x to set
     */
    public void setEnd_x(int end_x) {
        this.end_x = end_x;
    }
    /**
     * @param end_y the end_y to set
     */
    public void setEnd_y(int end_y) {
        this.end_y = end_y;
    }
    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    /*
     * @param image the image to set
     */
    public void setImage() {
        this.image = image2;
        this.circle.setFill(new ImagePattern (this.image));
    }
    @Override
    public void resume()
    {
    	timeline.play();
    	timeline2.play();
    }
    @Override
    public void stopMoving()
    {

    	timeline.pause();
    	timeline2.pause();

    }
    public boolean getisSliced()
    {
    	return isSliced;
    }
    public void setisSlicedF(boolean isSliced)
    {
    	this.isSliced=isSliced;
    }
    public Circle Circle() {
		return this.circle;
	}
    @Override
	public String toString()
	{
		return "bomb";
	}
	@Override
	public int getSpeed() {
	return this.speed;
	}
}
