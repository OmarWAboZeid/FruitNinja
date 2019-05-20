package View;

public interface GameActions {
    /*
     *@return created game object
     */
    /*
    * update moving objects locations
    */
    public void updateObjectsLocations();
    /*
    * it is used to slice fruits located in your swiping region
    This method can take your swiping region as parameters (they
    depend on how you calculate it).
    */
    public void sliceObjects();
    /*
     *saves the current state of the game
     */
    public void saveGame();
    /*
     *loads the last saved state of the game
     */
    public int loadGame();
    /*
     *resets the game to its initial state
     */
    public void ResetGame();

    void saveGame(int score);
}