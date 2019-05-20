package Fruit;

import javafx.geometry.Rectangle2D;

public class FruitFactory {
	  private Rectangle2D bounds;
    public Rectangle2D getBounds() {
        return bounds;

    }

    public void setBounds(Rectangle2D bounds) {
        this.bounds = bounds;
    }


    public FruitStrategy getFruit(String fruit,String mode)
    {
    	if(mode.equals("normal"))
    	{
        if(fruit.equals("apple"))
            return new Apple(bounds,"normal");
        else if(fruit.equals("grape"))
            return new Grape(bounds,"normal");
        else if(fruit.equals("strawberry"))
            return new Strawberry(bounds,"normal");
        else if(fruit.equals("heart"))
            return new Heart(bounds,"normal");
        else if(fruit.equals("starfruit"))
            return new Starfruit(bounds,"normal");
        else if(fruit.equals("bomb"))
            return new Bomb(bounds,"normal");
        else return new FatalBomb(bounds,"normal");
    	}

    	else if (mode.equals("faster"))
    	{
    		  if(fruit.equals("apple"))
    	            return new Apple(bounds,"faster");
    	        else if(fruit.equals("grape"))
    	            return new Grape(bounds,"faster");
    	        else if(fruit.equals("strawberry"))
    	            return new Strawberry(bounds,"faster");
    	        else if(fruit.equals("heart"))
    	            return new Heart(bounds,"faster");
    	        else if(fruit.equals("starfruit"))
    	            return new Starfruit(bounds,"faster");
    	        else if(fruit.equals("bomb"))
    	            return new Bomb(bounds,"faster");
    	        else return new FatalBomb(bounds,"faster");
    	}
    	else if (mode.equals("fastest"))
    	{
    		  if(fruit.equals("apple"))
    	            return new Apple(bounds,"fastest");
    	        else if(fruit.equals("grape"))
    	            return new Grape(bounds,"fastest");
    	        else if(fruit.equals("strawberry"))
    	            return new Strawberry(bounds,"fastest");
    	        else if(fruit.equals("heart"))
    	            return new Heart(bounds,"fastest");
    	        else if(fruit.equals("starfruit"))
    	            return new Starfruit(bounds,"fastest");
    	        else if(fruit.equals("bomb"))
    	            return new Bomb(bounds,"fastest");
    	        else return new FatalBomb(bounds,"fastest");
    	}
    	return null;
    }
}
